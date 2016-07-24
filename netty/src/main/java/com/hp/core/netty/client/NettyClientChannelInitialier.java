/**
 * 
 */
package com.hp.core.netty.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.core.netty.bean.Response;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author huangping
 * 2016年7月24日 下午1:51:49
 */
@Component
public class NettyClientChannelInitialier extends ChannelInitializer<SocketChannel> {

	static Logger log = LoggerFactory.getLogger(NettyClientChannelInitialier.class);
	
	@Resource
	NettyClientDispatchHandler nettyClientDispatchHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("initChannel with ");
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

}
