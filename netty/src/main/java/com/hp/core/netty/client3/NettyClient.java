/**
 * 
 */
package com.hp.core.netty.client3;

import java.util.concurrent.ArrayBlockingQueue;

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
	private Channel channel;
	private int workerGroupThreads = 2;
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

	/**
	 * @param host
	 * @param port
	 * @param serializationTypeEnum
	 */
	public NettyClient(String host, int port, SerializationTypeEnum serializationTypeEnum) {
		this.host = host;
		this.port = port;
		this.serializationTypeEnum = serializationTypeEnum;
	}
	
	/**
	 * @param host
	 * @param port
	 * @param serializationTypeEnum
	 */
	public NettyClient(String host, int port, String serializationTypeEnumName) {
		this.host = host;
		this.port = port;
		this.serializationTypeEnum = SerializationTypeEnum.getEnumByName(serializationTypeEnumName);
	}

	@Override
	public void init() throws Exception {
		log.info("init client start with host={}, port={}", host, port);
		initBootstrap();
		getChannel();
		log.info("init client success with host={}, port={}", host, port);
	}
	
	/**
	 * 初始化Bootstrap
	 */
	private void initBootstrap() {
		log.info("initBootstrap client start. with host={}, port={}", host, port);
		workerGroup = new NioEventLoopGroup(workerGroupThreads);
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
		log.info("initBootstrap client end. with host={}, port={}", host, port);
	}
	
	/**
	 * 获取channel
	 * @throws Exception
	 */
	public void getChannel() throws Exception {
		log.info("getChannel client start. with host={}, port={}", host, port);
		if (bootstrap == null) {
			initBootstrap();
		}
		channel = bootstrap.connect(host, port).sync().channel();
		log.info("getChannel client end. with host={}, port={}", host, port);
	}

	@Override
	public NettyResponse send(NettyRequest request) throws Exception {
		log.debug("send message with request={}", request);
		NettyConstants.responseMap.put(request.getMessageId(), new ArrayBlockingQueue<NettyResponse>(1));
//		byte[] bytes = (request.toString() + System.getProperty("line.separator")).getBytes();
//		ByteBuf message = Unpooled.buffer(bytes.length);
//		message.writeBytes(bytes);
		channel.writeAndFlush(request);
		return getResponse(request.getMessageId());
	}
	
	/**
	 * 获取服务端返回的值
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public NettyResponse getResponse(String messageId) throws Exception {
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
		}
		return response;
	}

	@Override
	public void close() {
		if (channel == null) {
			return;
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
		if (channel != null) {
			channel.closeFuture().syncUninterruptibly();	
		}
		workerGroup = null;
		channel = null;
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

}
