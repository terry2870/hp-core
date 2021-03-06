/**
 * 
 */
package com.hp.core.redis;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.hp.core.redis.utils.RedisUtil;


/**
 * @author ping.huang
 * 2017年5月12日
 */
public class HPValueOperations {

	static Logger log = LoggerFactory.getLogger(HPValueOperations.class);
	private static final int MIN_TIME = 5;
	private static final int MAX_TIME = 21;
	
	private HPRedisTemplate hpRedisTemplate;
	
	private ValueOperations<String, String> valueOperations;

	private ValueOperations<String, String> valueOperationsReadOnly;

	
	// 读写----------------------------开始
	
	/**
	 * 删除key
	 * 
	 * @param key
	 * @param value
	 */
	public Boolean delete(String key) {
		return hpRedisTemplate.delete(key);
	}
	
	/**
	 * 批量删除
	 */
	public Long delete(Collection<String> keys) {
		return hpRedisTemplate.delete(keys);
	}
	
	/**
	 * 设置超时
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void setTimeout(String key, long timeout, TimeUnit unit) {
		hpRedisTemplate.setTimeout(key, timeout, unit);
	}
	
	public void set(String key, Object value) {
		// TODO Auto-generated method stub
	}

	/**
	 * 设置，随机时间
	 * @param key
	 * @param value
	 */
	public void setWithRandomMinute(String key, Object value) {
		//随机获取一个时间
		long timeout = RandomUtils.nextLong(MIN_TIME, MAX_TIME);
		set(key, value, timeout, TimeUnit.MINUTES);
	}

	
	/**
	 * 设置，并且设置超时
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 */
	public void set(String key, Object value, long timeout, TimeUnit unit) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPValueOperations set redis error. with key={}, value={}", key, value);
			return;
		}
        try {
        	valueOperations.set(key, v, timeout, unit);
        } catch (Exception e) {
        	log.error("HPValueOperations set redis error. with key={}, value={}", key, value, e);
        }
	}
	
	/**
	 * 设置
	 * @param key
	 * @param value
	 * @param offset
	 * @param timeout
	 * @param unit
	 */
	public void set(String key, Object value, long offset, long timeout, TimeUnit unit) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPValueOperations set redis error. with key={}, value={}, offset={}", key, value, offset);
			return;
		}
        try {
        	valueOperations.set(key, v, offset);
        	setTimeout(key, timeout, unit);
        } catch (Exception e) {
        	log.error("HPValueOperations set redis error. with key={}, value={}", key, value, e);
        	//删除key
        	delete(key);
        }
	}
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPValueOperations setIfAbsent redis error with key={}, value={}", key, value);
			return Boolean.FALSE;
		}
		Boolean result = Boolean.FALSE;
        try {
        	//设置
        	result = valueOperations.setIfAbsent(key, v, timeout, unit);
        } catch (Exception e) {
        	log.error("HPValueOperations setIfAbsent redis error. with key={}, value={}", key, value, e);
        	//删除key
        	delete(key);
        }
        return result;
	}
	
	/**
	 * 批量设置，并且设置超时
	 * @param map
	 * @param timeout
	 * @param unit
	 */
	public void multiSet(Map<String, Object> map, long timeout, TimeUnit unit) {
		if (MapUtils.isEmpty(map)) {
			log.warn("HPValueOperations multiSet redis error with map is empty.");
			return;
		}
		Map<String, String> cacheMap = new HashMap<String, String>(map.size());
		for (Entry<? extends String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			String key = entry.getKey();
			if (value instanceof String) {
				cacheMap.put(key, (String) value);
			} else if (value instanceof Integer) {
				cacheMap.put(key, String.valueOf(value));
			} else {
				cacheMap.put(key, JSON.toJSONString(value));
			}
		}
		try {
			hpRedisTemplate.executePipelined(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.openPipeline();
					for (Entry<String, String> item : cacheMap.entrySet()) {
						connection.setEx(item.getKey().getBytes(StandardCharsets.UTF_8), timeout, null == item.getValue() ? null : item.getValue().getBytes(StandardCharsets.UTF_8));
					}
					// 无需返回是否缓存成功
					return null;
				}
			});
		} catch (Exception e) {
			log.error("HPValueOperations multiSet redis error. with key.size={}", map.size(), e);
			delete(map.keySet());
		}
	}
	
	/**
	 * 设置或者获取
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @param clazz
	 * @return
	 */
	public <V> V getAndSet(String key, Object value, long timeout, TimeUnit unit, Class<V> clazz) {
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("HPValueOperations getAndSet redis error with key={}, value={}", key, value);
			return null;
		}
		String result = null;
        try {
        	result = valueOperations.getAndSet(key, v);
        	//设置超时
        	setTimeout(key, timeout, unit);
        	return RedisUtil.string2Value(result, clazz);
        } catch (Exception e) {
        	log.error("HPValueOperations getAndSet redis error. with key={}, value={}", key, value, e);
        	//删除key
        	delete(key);
        }
        return null;
	}
	
	/**
	 * 自增
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long increment(String key, long delta) {
		return valueOperations.increment(key, delta);
	}
	
	/**
	 * 自增
	 * @param key
	 * @param delta
	 * @return
	 */
	public Double increment(String key, double delta) {
		return valueOperations.increment(key, delta);
	}

	/**
	 * 追加
	 * @param key
	 * @param value
	 * @return
	 */
	public Integer append(String key, String value) {
		return valueOperations.append(key, value);
	}
	
	/**
	 * setBit
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Boolean setBit(String key, long offset, boolean value) {
		return valueOperations.setBit(key, offset, value);
	}
	
	// 读写----------------------------结束
	
	
	
	// 只读----------------------------开始
	
	/**
	 * 获取值
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V get(Object key, Class<V> clazz) {
		String v = null;
		try {
			v = valueOperationsReadOnly.get(key);
			if (v == null) {
				log.warn("HPValueOperations get redis error key={}", key);
				return null;
			}
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("HPValueOperations get error. with key={}", key, e);
		}
		return null;
	}
	
	/**
	 * 获取list格式
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> clazz) {
		String v = null;
		try {
			v = valueOperationsReadOnly.get(key);
			if (v == null) {
				log.warn("RedisValueHelper getList redis error key={}", key);
				return null;
			}
			return JSON.parseArray(v, clazz);
		} catch (Exception e) {
			log.error("RedisValueHelper getList error. with key={}, e={}", key, e.toString());
		}
		return null;
	}
	
	/**
	 * 部分获取
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String get(String key, long start, long end) {
		return valueOperationsReadOnly.get(key, start, end);
	}
	
	/**
	 * 批量获取
	 * @param keys
	 * @param clazz
	 * @return
	 */
	public <V> List<V> multiGet(Collection<String> keys, Class<V> clazz) {
		if (CollectionUtils.isEmpty(keys)) {
			log.warn("HPValueOperations multiGet error. keys={}", keys);
			return null;
		}
		List<String> list = null;
		try {
			list = valueOperationsReadOnly.multiGet(keys);
			if (CollectionUtils.isEmpty(list)) {
				log.warn("HPValueOperations multiGet error. keys={}", keys);
				return null;
			}
			List<V> l = new ArrayList<V>(list.size());
			for (String str : list) {
				l.add(RedisUtil.string2Value(str, clazz));
			}
			return l;
		} catch (Exception e) {
			log.error("HPValueOperations multiGet error. ", e);
		}
		return null;
	}
	
	/**
	 * 批量获取
	 * 
	 * @param keys
	 * @param clazz
	 * @return
	 */
	public <T> List<List<T>> multiGetList(Collection<String> keys, Class<T> clazz) {
		if (CollectionUtils.isEmpty(keys)) {
			log.warn("RedisValueHelper multiGetList error. keys={}", keys);
			return null;
		}
		List<String> list = null;
		try {
			list = valueOperationsReadOnly.multiGet(keys);
			if (CollectionUtils.isEmpty(list)) {
				log.warn("RedisValueHelper multiGetList error. keys={}", keys);
				return null;
			}
			List<List<T>> l = new ArrayList<>();
			for (String str : list) {
				l.add(JSON.parseArray(str, clazz));
			}
			return l;
		} catch (Exception e) {
			log.error("RedisValueHelper multiGetList error. ", e);
		}
		return null;
	}
	
	/**
	 * 批量获取，返回map
	 * @param keys
	 * @param clazz
	 * @return
	 */
	public <T> Map<String, T> multiGetForMap(Collection<String> keys, Class<T> clazz) {
		List<T> list = multiGet(keys, clazz);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		Map<String, T> map = new HashMap<>();
		int i = 0;
		for (String key : keys) {
			map.put(key, list.get(i));
			i++;
		}
		return map;
	}
	
	/**
	 * 大小
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		return valueOperationsReadOnly.size(key);
	}
	
	/**
	 * getBit
	 * @param key
	 * @param offset
	 * @return
	 */
	public Boolean getBit(String key, long offset) {
		return valueOperationsReadOnly.getBit(key, offset);
	}
	
	
	// 只读----------------------------


	public ValueOperations<String, String> getValueOperations() {
		return valueOperations;
	}

	public void setValueOperations(ValueOperations<String, String> valueOperations) {
		this.valueOperations = valueOperations;
	}

	public ValueOperations<String, String> getValueOperationsReadOnly() {
		return valueOperationsReadOnly;
	}

	public void setValueOperationsReadOnly(ValueOperations<String, String> valueOperationsReadOnly) {
		this.valueOperationsReadOnly = valueOperationsReadOnly;
	}

	public void setHpRedisTemplate(HPRedisTemplate hpRedisTemplate) {
		this.hpRedisTemplate = hpRedisTemplate;
	}
}
