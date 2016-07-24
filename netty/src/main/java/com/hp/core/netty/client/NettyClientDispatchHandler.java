/**
 * 
 */
package com.hp.core.netty.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.core.netty.bean.Request;
import com.hp.core.netty.bean.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huangping
 * 2016年7月24日 下午2:09:16
 */
@Component
public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<Response> {

	static Logger log = LoggerFactory.getLogger(NettyClientDispatchHandler.class);
	
	/**
	 * 用来存放服务端返回的数据
	 */
	private ConcurrentHashMap<String, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<String, BlockingQueue<Response>>();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		log.info("channelRead0 with response={}", response);
		
	}
	
	
	
	/**
	 * 获取服务端返回的值
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public Response getResponse(String messageId) throws Exception {
		Response result = null;
		responseMap.putIfAbsent(messageId, new ArrayBlockingQueue<Response>(1));
		try {
			result = responseMap.get(messageId).take();
		} catch (InterruptedException e) {
			throw e;
		} finally {
			responseMap.remove(messageId);
		}
		return result;
	}
	
	/**
	 * 添加一个调用
	 * @param request
	 */
	public void addRequest(Request request) {
		responseMap.put(request.getMessageId(), new ArrayBlockingQueue<>(1));
	}

}
