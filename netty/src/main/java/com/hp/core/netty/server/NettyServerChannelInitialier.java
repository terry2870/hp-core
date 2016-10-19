/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.enums.SerializationTypeEnum;
import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.serialize.protostuff.ProtostuffDecoder;
import com.hp.core.netty.serialize.protostuff.ProtostuffEncoder;

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
public class NettyServerChannelInitialier extends ChannelInitializer<SocketChannel> {

	private static Logger log = LoggerFactory.getLogger(NettyServerChannelInitialier.class);
	
	private SerializationTypeEnum serializationTypeEnum;
	
	/**
	 * @param serializationTypeEnum
	 */
	public NettyServerChannelInitialier(SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("初始化开始");
		ChannelPipeline pipeline = ch.pipeline();
		switch (serializationTypeEnum) {
		case PROTOSTUFF:
			pipeline.addLast(new ProtostuffDecoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffEncoder(NettyResponse.class));
			break;
		case LINEBASED:
			pipeline.addLast(new LineBasedFrameDecoder(1024));
			pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
			break;
		default:
			pipeline.addLast(new ProtostuffDecoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffEncoder(NettyResponse.class));
			break;
		}
		
		pipeline.addLast(new NettyServerDispatchHandler());
	}
	
	public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<NettyRequest> {


		@Override
		protected void channelRead0(ChannelHandlerContext ctx, NettyRequest request) throws Exception {
			log.info("服务端收到消息。 request={}", request);
			NettyResponse response = new NettyResponse(request.getMessageId(), "收到请求："+ request.getData());
			ByteBuf resp = Unpooled.copiedBuffer((response.toString() + System.getProperty("line.separator")).getBytes());
			ctx.writeAndFlush(resp);
		}
		

	}

	public SerializationTypeEnum getSerializationTypeEnum() {
		return serializationTypeEnum;
	}

	public void setSerializationTypeEnum(SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
	}

}
