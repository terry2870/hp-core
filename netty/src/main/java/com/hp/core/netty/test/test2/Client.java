/**
 * 
 */
package com.hp.core.netty.test.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author huangping 2016年8月14日 上午12:47:38
 */
public class Client {

	private EventLoopGroup workerGroup;
	private int workerGroupThreads;

	public void connect(String host, int port) throws Exception {
		workerGroup = new NioEventLoopGroup(workerGroupThreads);
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerGroup).channel(NioSocketChannel.class)
				// .option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						// 以下两行代码为了解决半包读问题
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						ch.pipeline().addLast(new StringDecoder());

						ch.pipeline().addLast(new TimeClientHandler());
					}
				});
		bootstrap.connect(host, port).sync();
	}

	public static class TimeClientHandler extends SimpleChannelInboundHandler<String> {

		private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

		private int counter;

		private byte[] req;

		public TimeClientHandler() {
			req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			logger.info("channelActive");
			ByteBuf message = null;
			for (int i = 0; i < 10; i++) {
				message = Unpooled.buffer(req.length);
				message.writeBytes(req);
				ctx.writeAndFlush(message);
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			logger.info("Unexcepted exception from downstream:" + cause.getMessage());
			ctx.close();
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
			String body = (String) msg;
			logger.info("Now is:" + body + "; the counter is:" + (++counter));
		}

	}

	public static void main(String[] args) {
		try {
			new Client().connect("127.0.0.1", 9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
