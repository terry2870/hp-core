/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author huangping 2016年7月24日 下午10:07:12
 */
@Component
public class NettyServer implements Server {

	static Logger log = LoggerFactory.getLogger(NettyServer.class);

	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	@Override
	public void start(int port) throws InterruptedException {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
//			.option(ChannelOption.SO_BACKLOG, 1024)
//			.childOption(ChannelOption.SO_KEEPALIVE, true)
//			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(new NettyServerChannelInitialier());
		serverBootstrap.bind(port).sync();
	}

	@Override
	public void stop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		bossGroup = null;
		workerGroup = null;
		channel = null;
	}

}
