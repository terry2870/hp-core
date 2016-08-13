/**
 * 
 */
package com.hp.core.netty.server;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hp.core.netty.bean.Request;
import com.hp.core.netty.bean.Response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping
 * 2016年7月24日 下午1:51:49
 */
@Component
public class NettyServerChannelInitialier extends ChannelInitializer<SocketChannel> {

	static Logger log = LoggerFactory.getLogger(NettyServerChannelInitialier.class);

	@Resource
	NettyServerDispatchHandler nettyServerDispatchHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("已经启动监听");
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LineBasedFrameDecoder(1024));
		pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast(nettyServerDispatchHandler);
	}
	
	@Component
	public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<String> {

		int counter = 0;

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String requestMsg) throws Exception {
			log.info("服务端收到消息。 requestMsg={}", requestMsg);
			Request request = JSON.parseObject(requestMsg, Request.class);
			Response response = new Response(request.getMessageId(), "收到请求："+ request.getData());
			ByteBuf resp = Unpooled.copiedBuffer((response.toString() + System.getProperty("line.separator")).getBytes());
			ctx.writeAndFlush(resp);
		}
		

	}

}
