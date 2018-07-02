/**
 * 
 */
package com.hp.core.netty.serialize.protostuff;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author huangping 2016年8月21日 上午1:01:39
 */
public class ProtostuffDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	public ProtostuffDecoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int i = in.readableBytes();
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			ctx.close();
		}
		i = in.readableBytes();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);

		Object obj = SerializationUtil.deserialize(data, genericClass);
		out.add(obj);
	}
}
