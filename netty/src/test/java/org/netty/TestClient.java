/**
 * 
 */
package org.netty;

import java.util.logging.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ping.huang 2016年7月23日
 */
public class TestClient {

	TimeClientHandler handler = new TimeClientHandler();

	public Channel connect(int port, String host) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = null;
		Channel c = null;
		try {
			b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					System.out.println("client initChannel..");
					arg0.pipeline().addLast(new HelloClientHandler());
				}
			});
			// 发起异步连接操作
			c = b.connect(host, port).sync().channel();
			// 等待客户端链路关闭
			c.closeFuture().sync();
		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
		return c;
	}

	public static void main(String[] args) throws Exception {
		int port = 9000;
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		Channel c = new TestClient().connect(port, "127.0.0.1");
		for (int i = 0; i < 5; i++) {
			ChannelFuture f = c.writeAndFlush("啊哈哈啊" + i);
			Object o = f.get();
			System.out.println(o);
		}
		
	}

	/**
	 * 实现1
	 * 
	 * @author ping.huang 2016年7月23日
	 */
	public static class HelloClientHandler extends SimpleChannelInboundHandler<String> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

			System.out.println("Server say : " + msg);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("Client active ");
			// super.channelActive(ctx);
			//ctx.writeAndFlush("暗黑哈哈哈啊 " + System.currentTimeMillis());
			super.channelActive(ctx);
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("Client close ");
			super.channelInactive(ctx);
		}
	}

	/**
	 * 实现2
	 * 
	 * @author ping.huang 2016年7月23日
	 */
	public static class TimeClientHandler extends ChannelInboundHandlerAdapter {

		private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

		private final ByteBuf firstMessage;

		public TimeClientHandler() {
			byte[] req = "QUERY TIME ORDER".getBytes();
			firstMessage = Unpooled.buffer(req.length);
			firstMessage.writeBytes(req);
		}

		public void sendMsg(ChannelHandlerContext ctx) {
			// ctx.writeAndFlush(ctx.);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			// 与服务端建立连接后
			System.out.println("client channelActive..");
			// sendMsg(ctx);
			ctx.writeAndFlush(firstMessage);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("client channelRead..");
			// 服务端返回消息后
			ByteBuf buf = (ByteBuf) msg;
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body = new String(req, "UTF-8");
			System.out.println("Now is :" + body);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			System.out.println("client exceptionCaught..");
			// 释放资源
			logger.warning("Unexpected exception from downstream:" + cause.getMessage());
			ctx.close();
		}

	}
}
