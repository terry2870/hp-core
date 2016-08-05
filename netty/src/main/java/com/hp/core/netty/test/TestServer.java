/**
 * 
 */
package com.hp.core.netty.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping 2016年8月3日 下午10:52:36
 */
public class TestServer {

	private static final Logger logger = LoggerFactory.getLogger(TestServer.class);
	private static final String IP = "127.0.0.1";
	private static final int PORT = 9999;
	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; // 默认
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;
	/*
	 * NioEventLoopGroup实际上就是个线程池,
	 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
	 * 每一个NioEventLoop负责处理m个Channel,
	 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
	 */
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	protected static void run() throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				logger.info("启动监听端口：" + PORT);
				ChannelPipeline pipeline = ch.pipeline();
				/*pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));*/
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast(new TcpServerHandler());
			}
		});

		b.bind(IP, PORT).sync();
		logger.info("TCP服务器已启动");
	}

	protected static void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) throws Exception {
		logger.info("开始启动TCP服务器...");
		TestServer.run();
		// TcpServer.shutdown();
	}
	
	public static class TcpServerHandler extends SimpleChannelInboundHandler<Object> {  
		  
	    private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);  
	  
	    @Override  
	    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  
	            throws Exception {  
	        logger.info("SERVER接收到消息:"+msg);  
	        ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);  
	    }  
	      
	    @Override  
	    public void exceptionCaught(ChannelHandlerContext ctx,  
	            Throwable cause) throws Exception {  
	        logger.warn("Unexpected exception from downstream.", cause);  
	        ctx.close();  
	    }  
	  
	}
}
