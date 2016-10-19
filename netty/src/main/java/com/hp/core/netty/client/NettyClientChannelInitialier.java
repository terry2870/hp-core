/**
 * 
 */
package com.hp.core.netty.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.constants.NettyConstants;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author huangping
 * 2016年7月24日 下午1:51:49
 */
public class NettyClientChannelInitialier extends ChannelInitializer<SocketChannel> {

	private static Logger log = LoggerFactory.getLogger(NettyClientChannelInitialier.class);

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("initChannel with ");
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LineBasedFrameDecoder(1024));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new NettyClientDispatchHandler());
	}
	
	@Component
	public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<String> {
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String responseMsg) throws Exception {
			log.info("客户端收到返回消息。 responseMsg={}", responseMsg);
			NettyResponse resp = JSON.parseObject(responseMsg, NettyResponse.class);
			NettyConstants.responseMap.get(resp.getMessageId()).put(resp);
		}

	}

}
