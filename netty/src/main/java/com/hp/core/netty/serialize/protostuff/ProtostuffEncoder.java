/**
 * 
 */
package com.hp.core.netty.serialize.protostuff;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author huangping 2016年8月21日 上午1:03:20
 */
public class ProtostuffEncoder extends MessageToByteEncoder<Object> {

	private Class<?> genericClass;

	public ProtostuffEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
		if (genericClass.isInstance(in)) {
			byte[] data = SerializationUtil.serialize(in);
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}
}
