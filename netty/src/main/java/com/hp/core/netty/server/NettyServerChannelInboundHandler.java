/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ping.huang
 * 2016年10月24日
 */
public class NettyServerChannelInboundHandler extends SimpleChannelInboundHandler<NettyRequest> {

	// 服务端处理的具体方法
	private NettyProcess nettyProcess;
	
	
	/**
	 * @param nettyProcess
	 */
	public NettyServerChannelInboundHandler(NettyProcess nettyProcess) {
		super();
		this.nettyProcess = nettyProcess;
	}

	static Logger log = LoggerFactory.getLogger(NettyServerChannelInboundHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyRequest request) throws Exception {
		log.debug("服务端收到消息。 request={}", request);
		NettyResponse response = new NettyResponse();
		response.setMessageId(request.getMessageId());
		try {
			Object data = nettyProcess.process(request);
			response.setData(data);
			response.setClassName(data.getClass());
		} catch (Exception e) {
			log.error("channelRead0 error. with messageId={}", request.getMessageId(), e);
			response.setException(e);
		}
		
		//ByteBuf resp = Unpooled.copiedBuffer((response.toString() + System.getProperty("line.separator")).getBytes());
		ctx.writeAndFlush(response);
	}
	
	
	/**
	 * 处理方法
	 * @author ping.huang
	 * 2016年10月24日
	 */
	public interface NettyProcess {
		
		/**
		 * 服务端处理具体的逻辑
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public Object process(NettyRequest request) throws Exception;
	}

	public NettyProcess getNettyProcess() {
		return nettyProcess;
	}

	public void setNettyProcess(NettyProcess nettyProcess) {
		this.nettyProcess = nettyProcess;
	}

}
