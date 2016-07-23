/**
 * 
 */
package org.netty;

import java.net.InetAddress;
import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ping.huang 2016年7月23日
 */
public class TestServer {

	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 配置服务器的NIO线程租
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());

			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();

			System.out.println("端口【" + port + "】监听完成");

			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();

		} finally {
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			System.out.println("server initChannel..");
			arg0.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 9000;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		new TestServer().bind(port);
	}

	public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
			// 收到消息直接打印输出
			System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

			// 返回客户端消息 - 我已经接收到了你的消息
			//ctx.writeAndFlush("Received your message !===" + msg);
		}

		/*
		 * 
		 * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
		 * 
		 * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {

			System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

			ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!");

			//super.channelActive(ctx);
		}
	}

	public static class TimeServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("server channelRead..");
			ByteBuf buf = (ByteBuf) msg;
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body = new String(req, "UTF-8");
			System.out.println("The time server receive order:" + body);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			ctx.write(resp);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			System.out.println("server channelReadComplete..");
			ctx.flush();// 刷新后才将数据发出到SocketChannel
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			System.out.println("server exceptionCaught..");
			ctx.close();
		}

	}
}
