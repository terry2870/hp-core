/**
 * 
 */
package com.hp.core.netty.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping 2016年8月3日 下午11:02:13
 */
public class TestClient {

	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);
	public static String HOST = "127.0.0.1";
	public static int PORT = 9999;

	public static Bootstrap bootstrap = getBootstrap();
	public static Channel channel = getChannel(HOST, PORT);

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	public static final Bootstrap getBootstrap() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				/*pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));*/
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast("handler", new TcpClientHandler());
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		return b;
	}

	public static final Channel getChannel(String host, int port) {
		Channel channel = null;
		try {
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", host, port), e);
			return null;
		}
		return channel;
	}

	public static void sendMsg(String msg) throws Exception {
		if (channel != null) {
			channel.writeAndFlush(msg).sync();
		} else {
			logger.warn("消息发送失败,连接尚未建立!");
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			long t0 = System.currentTimeMillis();
			for (int i = 0; i < 2; i++) {
				TestClient.sendMsg(i + "你好1");
			}
			long t1 = System.currentTimeMillis();
			logger.info(String.valueOf((t1 - t0) / 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class TcpClientHandler extends SimpleChannelInboundHandler<Object> {

		private static final Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
			// messageReceived方法,名称很别扭，像是一个内部方法.
			logger.info("client接收到服务器返回的消息:" + msg);

		}

	}
}
