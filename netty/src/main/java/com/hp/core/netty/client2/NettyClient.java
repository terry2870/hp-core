/**
 * 
 */
package com.hp.core.netty.client2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.enums.SerializationTypeEnum;
import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.constants.NettyConstants;
import com.hp.core.netty.serialize.protostuff.ProtostuffDecoder;
import com.hp.core.netty.serialize.protostuff.ProtostuffEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping
 * 2016年7月24日 上午1:01:28
 */
public class NettyClient implements Client {
	
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);
	

	private EventLoopGroup workerGroup;
	private Bootstrap bootstrap;
	private BlockingQueue<Channel> channelQueue = null;
	
	private String host;
	private int port;
	private int queueSize = Runtime.getRuntime().availableProcessors() * 2; // 连接服务端，使用最大channel个数。默认为cpu核数 * 2
	
	private SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.PROTOSTUFF; // 序列化方法
	
	public NettyClient() {
	}

	/**
	 * @param host
	 * @param port
	 */
	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public NettyClient(String host, int port, int queueSize) {
		this.host = host;
		this.port = port;
		this.queueSize = queueSize;
	}

	@Override
	public void init() throws Exception {
		log.info("init client start with host={}, port={}", host, port);
		channelQueue = new ArrayBlockingQueue<>(queueSize);
		initBootstrap();
		log.info("init client success with host={}, port={}", host, port);
	}
	
	/**
	 * 初始化Bootstrap
	 */
	private void initBootstrap() throws Exception {
		log.info("initBootstrap client start. with host={}, port={}", host, port);
		workerGroup = new NioEventLoopGroup(queueSize);
		bootstrap = new Bootstrap();
		bootstrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					log.info("客户端初始化开始");
					ChannelPipeline pipeline = ch.pipeline();
					switch (serializationTypeEnum) {
					case PROTOSTUFF:
						pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
						pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
						break;
					case LINEBASED:
						pipeline.addLast(new LineBasedFrameDecoder(1024));
						pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
						break;
					default:
						pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
						pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
						break;
					}
					pipeline.addLast(new NettyClientChannelInboundHandler());
				}
			});
		for (int i = 0; i < queueSize; i++) {
			Channel channel = bootstrap.connect(host, port).sync().channel();
			//channel.closeFuture().sync();
			channelQueue.add(channel);
		}
		//close();
		log.info("initBootstrap client end. with host={}, port={}", host, port);
	}
	
	/**
	 * 获取channel
	 * @throws Exception
	 */
	public Channel getChannel() throws Exception {
		log.info("getChannel client start. with host={}, port={}", host, port);
		if (bootstrap == null) {
			initBootstrap();
		}
		return channelQueue.take();
	}

	@Override
	public NettyResponse send(NettyRequest request) throws Exception {
		log.debug("send message with request={}", request);
		NettyConstants.responseMap.put(request.getMessageId(), new ArrayBlockingQueue<NettyResponse>(1));
//		byte[] bytes = (request.toString() + System.getProperty("line.separator")).getBytes();
//		ByteBuf message = Unpooled.buffer(bytes.length);
//		message.writeBytes(bytes);
		Channel channel = getChannel();
		channel.writeAndFlush(request);
		return getResponse(request.getMessageId(), channel);
	}
	
	/**
	 * 获取服务端返回的值
	 * @param messageId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	private NettyResponse getResponse(String messageId, Channel channel) throws Exception {
		NettyResponse response = null;
		try {
			response = NettyConstants.responseMap.get(messageId).take();
			if (response.getException() != null) {
				log.error("getResponse error. with messageId={}", messageId, response.getException());
				throw new Exception(response.getException());
			}
		} catch (Exception e) {
			log.error("getResponse error. with messageId={}", messageId, e);
			throw e;
		} finally {
			NettyConstants.responseMap.remove(messageId);
			channelQueue.add(channel);
		}
		return response;
	}

	@Override
	public void close() throws Exception {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
			workerGroup = null;
		}
		if (CollectionUtils.isNotEmpty(channelQueue)) {
			int i = 0;
			while (i < queueSize) {
				channelQueue.take().closeFuture().syncUninterruptibly();
				i++;
			}
		}
		channelQueue.clear();
		channelQueue = null;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SerializationTypeEnum getSerializationTypeEnum() {
		return serializationTypeEnum;
	}

	public void setSerializationTypeEnum(SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

}
