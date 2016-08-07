/**
 * 
 */
package com.hp.core.netty.client;

import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.core.netty.bean.Request;
import com.hp.core.netty.bean.Response;
import com.hp.core.netty.constants.NettyConstants;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author huangping
 * 2016年7月24日 上午1:01:28
 */
@Component
public class NettyClient implements Client {
	
	static Logger log = LoggerFactory.getLogger(NettyClient.class);
	
	@Resource
	NettyClientChannelInitialier nettyClientChannelInitialier;

	private EventLoopGroup workerGroup;
	private Channel channel;
	private int workerGroupThreads;
	
	@Override
	public void connect(String host, int port) throws Exception {
		workerGroup = new NioEventLoopGroup(workerGroupThreads);
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			//.option(ChannelOption.TCP_NODELAY, true)
			.handler(nettyClientChannelInitialier);
		channel = bootstrap.connect(host, port).sync().channel();
	}

	@Override
	public Response send(Request request) throws Exception {
		log.info("send:" + request);
		NettyConstants.responseMap.put(request.getMessageId(), new ArrayBlockingQueue<Response>(1));
		channel.writeAndFlush(request.toString());
		return nettyClientChannelInitialier.getResponse(request.getMessageId());
	}

	@Override
	public void close() {
		if (null == channel) {
			return;
		}
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		workerGroup = null;
		channel = null;
	}

}
