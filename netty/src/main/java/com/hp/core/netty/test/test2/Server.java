/**
 * 
 */
package com.hp.core.netty.test.test2;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author huangping 2016年8月14日 上午12:47:45
 */
public class Server {

	static Logger log = LoggerFactory.getLogger(Server.class);

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public void start(int port) throws InterruptedException {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(10);
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
				// .childOption(ChannelOption.SO_KEEPALIVE, true)
				// .childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChildChannelHandler());
		serverBootstrap.bind(port).sync();
	}

	public static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// 以下两行代码为了解决半包读问题
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new StringDecoder());

			ch.pipeline().addLast(new TimeServerHandler());
		}

	}

	public static class TimeServerHandler extends SimpleChannelInboundHandler<String> {

		private int counter;

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			ctx.close();
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
			log.info("The time server receive order:" + msg + ";the counter is:" + (++counter));
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(msg) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
			currentTime = currentTime + System.getProperty("line.separator");
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			ctx.write(resp);
		}
	}

	public static void main(String[] args) {
		try {
			new Server().start(9999);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
