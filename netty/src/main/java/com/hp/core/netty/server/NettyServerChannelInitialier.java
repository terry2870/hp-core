/**
 * 
 */
package com.hp.core.netty.server;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

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
		ch.pipeline().addLast(nettyServerDispatchHandler);
	}

}
