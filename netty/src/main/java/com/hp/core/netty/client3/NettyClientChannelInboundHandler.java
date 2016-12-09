/**
 * 
 */
package com.hp.core.netty.client3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.constants.NettyConstants;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ping.huang
 * 2016年10月24日
 */
public class NettyClientChannelInboundHandler extends SimpleChannelInboundHandler<NettyResponse> {

	static Logger log = LoggerFactory.getLogger(NettyClientChannelInboundHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyResponse response) throws Exception {
		log.debug("客户端收到返回消息。 response={}", response);
		NettyConstants.responseMap.get(response.getMessageId()).put(response);
	}
}
