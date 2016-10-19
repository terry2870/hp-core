/**
 * 
 */
package com.hp.core.netty.serialize.protostuff;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @author huangping 2016年8月21日 上午1:03:50
 */
public class SerializationUtil {

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();
	private static Logger log = LoggerFactory.getLogger(SerializationUtil.class);

	private SerializationUtil() {
	}

	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> cls) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(cls);
			if (schema != null) {
				cachedSchema.put(cls, schema);
			}
		}
		return schema;
	}

	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj) {
		Class<T> cls = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<T> schema = getSchema(cls);
			return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} catch (Exception e) {
			log.error("serialize error", e);
			return null;
		} finally {
			if (buffer != null) {
				buffer.clear();
			}
		}
	}

	public static <T> T deserialize(byte[] data, Class<T> cls) {
		try {
			T message = (T) cls.newInstance();
			Schema<T> schema = getSchema(cls);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			log.error("deserialize error", e);
			return null;
		}
	}
}
