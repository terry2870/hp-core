/**
 * 
 */
package com.hp.core.test.nettyTest.test2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ping.huang 2016年11月9日
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);
	ExecutorService exe = Executors.newFixedThreadPool(10);
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		
		exe.execute(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					logger.info("SERVER接收到消息:" + msg);
					Thread.sleep(10000);
					ctx.channel().writeAndFlush("yes, server is accepted you ,nice !" + msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warn("Unexpected exception from downstream.", cause);
		ctx.close();
	}
}
