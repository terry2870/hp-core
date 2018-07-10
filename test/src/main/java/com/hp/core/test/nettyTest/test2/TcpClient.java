/**
 * 
 */
package com.hp.core.test.nettyTest.test2;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyResponse;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author ping.huang 2016年11月9日
 */
public class TcpClient {

	private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);
	public static String HOST = "127.0.0.1";
	public static int PORT = 9999;

	public static Bootstrap bootstrap = getBootstrap();
	
	public static Map<String, BlockingQueue<String>> responseMap = new ConcurrentHashMap<String, BlockingQueue<String>>();
	
	//private static Map<String, V>

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
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast("handler", new TcpClientHandler());
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		return b;
	}

	public static final Channel getChannel() {
		Channel channel = null;
		try {
			channel = bootstrap.connect(HOST, PORT).sync().channel();
		} catch (Exception e) {
			logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", HOST, PORT), e);
			return null;
		}
		return channel;
	}

	public static void sendMsg(String msg) throws Exception {
		Channel channel = getChannel();
		logger.info("send msg= {}" + msg);
		if (channel != null) {
			channel.writeAndFlush(msg).sync();
		} else {
			logger.warn("消息发送失败,连接尚未建立!");
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			long t0 = System.nanoTime();
			ExecutorService exe = Executors.newFixedThreadPool(10);
			
			
			for (int i = 0; i < 5; i++) {
				exe.execute(new Run(i));
				
			}
			long t1 = System.nanoTime();
			System.out.println((t1 - t0) / 1000000.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class Run implements Runnable {

		private int i;
		
		public Run(int i) {
			this.i = i;
		}
		
		@Override
		public void run() {
			try {
				TcpClient.sendMsg("你好1" + i);
				//logger.info("send msg" + i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
