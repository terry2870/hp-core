/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author huangping
 * 2018年7月2日
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		log.info("channelRead msg={}", msg);
	}
}
