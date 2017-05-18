/**
 * 
 */
package com.hp.core.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;

import com.hp.core.redis.utils.RedisUtil;


/**
 * @author ping.huang
 * 2017年5月16日
 */
public class HPListOperations {

	
	static Logger log = LoggerFactory.getLogger(HPListOperations.class);
	
	private ListOperations<String, String> listOperations;

	private ListOperations<String, String> listOperationsReadOnly;
	
	/**
	 * 获取
	 * @param key
	 * @param start
	 * @param end
	 * @param clazz
	 * @return
	 */
	public <V> List<V> range(String key, long start, long end, Class<V> clazz) {
		try {
			List<String> list = listOperationsReadOnly.range(key, start, end);
			if (CollectionUtils.isEmpty(list)) {
				return null;
			}
			List<V> l = new ArrayList<>(list.size());
			for (String str : list) {
				l.add(RedisUtil.string2Value(str, clazz));
			}
			return l;
		} catch (Exception e) {
			log.error("HPListOperations range error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 截取
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, long end) {
		listOperations.trim(key, start, end);
	}
	
	/**
	 * 获取长度
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		return listOperationsReadOnly.size(key);
	}
	
	/**
	 * 左进
	 * @param key
	 * @param value
	 * @return
	 */
	public Long leftPush(String key, Object value) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPListOperations leftPush error with key={}, value={}", key, value);
			return 0L;
		}
		try {
			return listOperations.leftPush(key, v);
		} catch (Exception e) {
			log.error("HPListOperations leftPush error. with key={}, value={}", key, value, e);
		}
		return 0L;
	}
	
	public Long leftPush(String key, Object pivot, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 全部左进
	 * @param key
	 * @param values
	 * @return
	 */
	public Long leftPushAll(String key, Object... values) {
		if (ArrayUtils.isEmpty(values) || values[0] == null) {
			log.warn("HPListOperations leftPushAll error with key={}, values={}", key, values);
			return 0L;
		}
		String[] arr = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			arr[i] = RedisUtil.value2String(values[i]);
		}
		try {
			return listOperations.leftPushAll(key, arr);
		} catch (Exception e) {
			log.error("HPListOperations leftPushAll error. with key={}", key, e);
		}
		return 0L;
	}
	
	/**
	 * 全部左进
	 * @param key
	 * @param values
	 * @return
	 */
	public Long leftPushAll(String key, Collection<Object> values) {
		if (CollectionUtils.isEmpty(values)) {
			log.warn("HPListOperations leftPushAll error with key={}, values={}", key, values);
			return 0L;
		}
		List<String> list = new ArrayList<String>(values.size());
		for (Object t : values) {
			list.add(RedisUtil.value2String(t));
		}
		try {
			return listOperations.leftPushAll(key, list);
		} catch (Exception e) {
			log.error("HPListOperations leftPushAll error. with key={}", key, e);
		}
		return 0L;
	}
	
	/**
	 * 左进
	 * @param key
	 * @param value
	 * @return
	 */
	public Long leftPushIfPresent(String key, Object value) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPListOperations leftPushIfPresent error with key={}, value={}", key, value);
			return 0L;
		}
		try {
			return listOperations.leftPushIfPresent(key, v);
		} catch (Exception e) {
			log.error("HPListOperations leftPushIfPresent error. with key={}, value={}", key, value, e);
		}
		return 0L;
	}
	
	/**
	 * 右进
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rightPush(String key, String value) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPListOperations rightPush error with key={}, value={}", key, value);
			return 0L;
		}
		try {
			return listOperations.rightPush(key, v);
		} catch (Exception e) {
			log.error("HPListOperations rightPush error. with key={}, value={}", key, value, e);
		}
		return 0L;
	}
	
	/**
	 * 全部右进
	 * @param key
	 * @param values
	 * @return
	 */
	public Long rightPushAll(String key, String... values) {
		if (ArrayUtils.isEmpty(values) || values[0] == null) {
			log.warn("HPListOperations rightPushAll error with key={}, values={}", key, values);
			return 0L;
		}
		String[] arr = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			arr[i] = RedisUtil.value2String(values[i]);
		}
		try {
			return listOperations.rightPushAll(key, arr);
		} catch (Exception e) {
			log.error("HPListOperations rightPushAll error. with key={}", key, e);
		}
		return 0L;
	}
	
	/**
	 * 全部右进
	 * @param key
	 * @param values
	 * @return
	 */
	public Long rightPushAll(String key, Collection<String> values) {
		if (CollectionUtils.isEmpty(values)) {
			log.warn("HPListOperations rightPushAll error with key={}, values={}", key, values);
			return 0L;
		}
		List<String> list = new ArrayList<String>(values.size());
		for (Object t : values) {
			list.add(RedisUtil.value2String(t));
		}
		try {
			return listOperations.rightPushAll(key, list);
		} catch (Exception e) {
			log.error("HPListOperations rightPushAll error. with key={}", key, e);
		}
		return 0L;
	}
	
	/**
	 * 右进
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rightPushIfPresent(String key, String value) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPListOperations rightPushIfPresent error with key={}, value={}", key, value);
			return 0L;
		}
		try {
			return listOperations.rightPushIfPresent(key, v);
		} catch (Exception e) {
			log.error("HPListOperations rightPushIfPresent error. with key={}, value={}", key, value, e);
		}
		return 0L;
	}
	
	public Long rightPush(String key, Object pivot, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 设置
	 * @param key
	 * @param index
	 * @param value
	 */
	public void set(String key, long index, Object value) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPListOperations set error with key={}, values={}, index={}", key, value, index);
			return;
		}
		try {
			listOperations.set(key, index, v);
		} catch (Exception e) {
			log.error("HPListOperations set error. with key={}, index={}, value={}", key, index, value, e);
		}
	}
	
	public Long remove(String key, long count, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * index
	 * @param key
	 * @param index
	 * @param clazz
	 * @return
	 */
	public <V> V index(String key, long index, Class<V> clazz) {
		try {
			String v = listOperationsReadOnly.index(key, index);
			if (v == null) {
				log.warn("HPListOperations index error with key={}, index={}", key, index);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations index error. with key={}, index={}", key, index, e);
		}
		return null;
	}
	
	/**
	 * 左出
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V leftPop(String key, Class<V> clazz) {
		try {
			String v = listOperations.leftPop(key);
			if (v == null) {
				log.warn("HPListOperations leftPop error with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations leftPop error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 左出
	 * @param key
	 * @param clazz
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public <V> V leftPop(String key, Class<V> clazz, long timeout, TimeUnit unit) {
		try {
			String v = listOperations.leftPop(key, timeout, unit);
			if (v == null) {
				log.warn("HPListOperations leftPop error with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations leftPop error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 右出
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V rightPop(String key, Class<V> clazz) {
		try {
			String v = listOperations.rightPop(key);
			if (v == null) {
				log.warn("HPListOperations rightPop error with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations rightPop error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 右出
	 * @param key
	 * @param clazz
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public <V> V rightPop(String key, Class<V> clazz, long timeout, TimeUnit unit) {
		try {
			String v = listOperations.rightPop(key, timeout, unit);
			if (v == null) {
				log.warn("HPListOperations rightPop error with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations rightPop error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 右出，左进
	 * @param sourceKey
	 * @param destinationKey
	 * @param clazz
	 * @return
	 */
	public <V> V rightPopAndLeftPush(String sourceKey, String destinationKey, Class<V> clazz) {
		try {
			String v = listOperations.rightPopAndLeftPush(sourceKey, destinationKey);
			if (v == null) {
				log.warn("HPListOperations rightPopAndLeftPush error with sourceKey={}, destinationKey={}", sourceKey, destinationKey);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations rightPopAndLeftPush error. with sourceKey={}, destinationKey={}", sourceKey, destinationKey, e);
		}
		return null;
	}
	
	/**
	 * 右出，左进
	 * @param sourceKey
	 * @param destinationKey
	 * @param clazz
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public <V> V rightPopAndLeftPush(String sourceKey, String destinationKey, Class<V> clazz, long timeout, TimeUnit unit) {
		try {
			String v = listOperations.rightPopAndLeftPush(sourceKey, destinationKey,timeout, unit);
			if (v == null) {
				log.warn("HPListOperations rightPopAndLeftPush error with sourceKey={}, destinationKey={}", sourceKey, destinationKey);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPListOperations rightPopAndLeftPush error. with sourceKey={}, destinationKey={}", sourceKey, destinationKey, e);
		}
		return null;
	}

	public ListOperations<String, String> getListOperations() {
		return listOperations;
	}

	public void setListOperations(ListOperations<String, String> listOperations) {
		this.listOperations = listOperations;
	}

	public ListOperations<String, String> getListOperationsReadOnly() {
		return listOperationsReadOnly;
	}

	public void setListOperationsReadOnly(ListOperations<String, String> listOperationsReadOnly) {
		this.listOperationsReadOnly = listOperationsReadOnly;
	}
}
