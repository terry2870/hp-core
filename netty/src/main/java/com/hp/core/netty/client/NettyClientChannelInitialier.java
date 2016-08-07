/**
 * 
 */
package com.hp.core.netty.client;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hp.core.netty.bean.Response;
import com.hp.core.netty.constants.NettyConstants;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping
 * 2016年7月24日 下午1:51:49
 */
@Component
public class NettyClientChannelInitialier extends ChannelInitializer<SocketChannel> {

	static Logger log = LoggerFactory.getLogger(NettyClientChannelInitialier.class);
	
	NettyClientDispatchHandler nettyClientDispatchHandler = null;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("initChannel with ");
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		nettyClientDispatchHandler = this.new NettyClientDispatchHandler();
		pipeline.addLast("handler", nettyClientDispatchHandler);
	}
	
	/**
	 * 获取服务端返回的值
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public Response getResponse(String messageId) throws Exception {
		return nettyClientDispatchHandler.getResponse(messageId);
	}
	
	public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<String> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String responseMsg) throws Exception {
			log.info("客户端收到返回消息。 responseMsg={}", responseMsg);
			Response resp = JSON.parseObject(responseMsg, Response.class);
			NettyConstants.responseMap.get(resp.getMessageId()).put(resp);
		}
		
		
		
		/**
		 * 获取服务端返回的值
		 * @param messageId
		 * @return
		 * @throws Exception
		 */
		public Response getResponse(String messageId) throws Exception {
			Response result = null;
			try {
				result = NettyConstants.responseMap.get(messageId).take();
			} catch (InterruptedException e) {
				throw e;
			} finally {
				NettyConstants.responseMap.remove(messageId);
			}
			return result;
		}

	}

}
