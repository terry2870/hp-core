/**
 * 
 */
package com.hp.core.netty.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.hp.core.netty.bean.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huangping
 * 2016年7月24日 下午2:09:16
 */
public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<Response> {

	
	private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<Long, BlockingQueue<Response>>();

	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
