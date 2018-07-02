/**
 * 
 */
package com.hp.core.netty.client;

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
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
	
	private String host;
	private int port;
	
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

	@Override
	public Client init() throws Exception {
		log.info("init client start with host={}, port={}", host, port);
		initBootstrap();
		log.info("init client success with host={}, port={}", host, port);
		return this;
	}
	
	/**
	 * 初始化Bootstrap
	 */
	private void initBootstrap() throws Exception {
		log.info("initBootstrap client start. with host={}, port={}", host, port);
		workerGroup = new NioEventLoopGroup();
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
		ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
		//channel.addListener(ChannelFutureListener.CLOSE);
		log.info("initBootstrap client end. with host={}, port={}", host, port);
	}
	
	/**
	 * 获取channel
	 * @throws Exception
	 */
	public Channel getChannel() throws Exception {
		log.debug("getChannel client start. with host={}, port={}", host, port);
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
			//清空处理响应的队列
			NettyConstants.responseMap.remove(messageId);
			//把管道还回到队列中
			channelQueue.add(channel);
		}
		return response;
	}

	@Override
	public Client close() throws Exception {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
			workerGroup = null;
		}
		if (CollectionUtils.isNotEmpty(channelQueue)) {
			int i = 0;
			while (i < chanelSize) {
				channelQueue.take().closeFuture().syncUninterruptibly();
				i++;
			}
		}
		channelQueue.clear();
		channelQueue = null;
		return this;
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

	public int getChanelSize() {
		return chanelSize;
	}

	public void setChanelSize(int chanelSize) {
		this.chanelSize = chanelSize;
	}

}
