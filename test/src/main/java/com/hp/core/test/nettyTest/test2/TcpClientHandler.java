/**
 * 
 */
package com.hp.core.test.nettyTest.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ping.huang 2016年11月9日
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// messageReceived方法,名称很别扭，像是一个内部方法.
		logger.info("client接收到服务器返回的消息:" + msg);

	}
}
