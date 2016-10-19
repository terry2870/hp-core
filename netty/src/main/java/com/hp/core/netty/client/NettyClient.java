/**
 * 
 */
package com.hp.core.netty.client;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.constants.NettyConstants;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author huangping
 * 2016年7月24日 上午1:01:28
 */
public class NettyClient implements Client {
	
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);
	

	private EventLoopGroup workerGroup;
	private Channel channel;
	private int workerGroupThreads;
	
	private String host;
	private int port;
	
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

	public void connect() throws Exception {
		connect(host, port);
	}
	
	@Override
	public void connect(String host, int port) throws Exception {
		log.info("connect start server host={}, port={}", host, port);
		workerGroup = new NioEventLoopGroup(workerGroupThreads);
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new NettyClientChannelInitialier());
		channel = bootstrap.connect(host, port).sync().channel();
		log.info("connect server success host={}, port={}", host, port);
	}

	@Override
	public NettyResponse send(NettyRequest request) throws Exception {
		log.debug("send message with request={}", request);
		NettyConstants.responseMap.put(request.getMessageId(), new ArrayBlockingQueue<NettyResponse>(1));
		byte[] bytes = (request.toString() + System.getProperty("line.separator")).getBytes();
		ByteBuf message = Unpooled.buffer(bytes.length);
		message.writeBytes(bytes);
		channel.writeAndFlush(message);
		return getResponse(request.getMessageId());
	}
	
	/**
	 * 获取服务端返回的值
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public NettyResponse getResponse(String messageId) throws Exception {
		NettyResponse result = null;
		try {
			result = NettyConstants.responseMap.get(messageId).take();
		} catch (InterruptedException e) {
			log.error("", e);
			throw e;
		} finally {
			NettyConstants.responseMap.remove(messageId);
		}
		return result;
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

}
