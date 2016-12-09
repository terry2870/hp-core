/**
 * 
 */
package com.hp.core.netty.serialize;

import com.hp.core.common.enums.SerializationTypeEnum;
import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.serialize.protostuff.ProtostuffDecoder;
import com.hp.core.netty.serialize.protostuff.ProtostuffEncoder;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author ping.huang
 * 2016年12月5日
 */
public class SerializeSelector {

	
	/**
	 * 选择序列化方法
	 * @param pipeline
	 * @param serializationTypeEnum
	 */
	public static void selectSerialize(ChannelPipeline pipeline, SerializationTypeEnum serializationTypeEnum) {
		switch (serializationTypeEnum) {
		case PROTOSTUFF:
			pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
			break;
		case LINEBASED:
			pipeline.addLast(new LineBasedFrameDecoder(1024));
			pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
			break;
		default:
			pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
			break;
		}
	}
	
	/**
	 * 选择序列化方法
	 * @param pipeline
	 * @param serializationTypeEnum
	 */
	public static void selectSerialize(ChannelPipeline pipeline, String serializationType) {
		switch (serializationType) {
		case "Protostuff":
			pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
			break;
		case "LineBased":
			pipeline.addLast(new LineBasedFrameDecoder(1024));
			pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
			break;
		default:
			pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
			pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
			break;
		}
	}
}
