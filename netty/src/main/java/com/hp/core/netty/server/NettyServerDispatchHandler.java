/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.core.netty.bean.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huangping
 * 2016年7月24日 下午2:09:16
 */
@Component
public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<Response> {

	static Logger log = LoggerFactory.getLogger(NettyServerDispatchHandler.class);


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		log.info("channelRead0 with response={}", response);
		
	}
	

}
