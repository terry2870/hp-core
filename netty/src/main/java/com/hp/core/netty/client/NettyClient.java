/**
 * 
 */
package com.hp.core.netty.client;

import org.springframework.stereotype.Component;

import com.hp.core.netty.bean.Request;
import com.hp.core.netty.bean.Response;
import com.miracle.framework.remote.netty.client.ClientCloseException;

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

	private EventLoopGroup workerGroup;
	private Channel channel;
	private int workerGroupThreads;
	
	@Override
	public void connect(String host, int port) {
		workerGroup = new NioEventLoopGroup(workerGroupThreads);
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(applicationContext.getBean(serializeType.getClientChannelInitializer()));
		channel = bootstrap.connect(host, port).syncUninterruptibly().channel();
	}

	@Override
	public Response<?> send(Request<?> request) {
		channel.writeAndFlush(request);
		return applicationContext.getBean(serializeType.getClientChannelInitializer()).getResponse(request.getMessageId());
	}

	@Override
	public void close() {
		if (null == channel) {
			throw new ClientCloseException();
		}
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		workerGroup = null;
		channel = null;
	}

}
