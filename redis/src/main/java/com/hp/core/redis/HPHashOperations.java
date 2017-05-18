/**
 * 
 */
package com.hp.core.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;

import com.hp.core.redis.utils.RedisUtil;


/**
 * @author ping.huang
 * 2017年5月17日
 */
public class HPHashOperations {

	static Logger log = LoggerFactory.getLogger(HPHashOperations.class);
	
	private HPRedisTemplate hpRedisTemplate;
	
	private HashOperations<String, String, String> hashOperations;

	private HashOperations<String, String, String> hashOperationsReadOnly;
	
	/**
	 * 删除
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public Long delete(String key, Object... hashKeys) {
		Long result = null;
		try {
			result = hashOperations.delete(key, hashKeys);
		} catch (Exception e) {
			log.error("HPHashOperations delete key error. with key={}", key, e);
			//删除失败，重试一次
			try {
				result = hashOperations.delete(key, hashKeys);
			} catch (Exception e2) {
				log.error("HPHashOperations delete key again error. with key={}", key, e);
				throw e2;
			}
		}
		return result;
	}

	/**
	 * 是否存在
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Boolean hasKey(String key, Object hashKey) {
		try {
			return hashOperationsReadOnly.hasKey(key, hashKey);
		} catch (Exception e) {
			log.error("hasKey key error. with key={}, hashKey={}", key, hashKey, e);
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取
	 * @param key
	 * @param hashKey
	 * @param clazz
	 * @return
	 */
	public <V> V get(String key, Object hashKey, Class<V> clazz) {
		try {
			String v = hashOperationsReadOnly.get(key, hashKey);
			return RedisUtil.string2Value(v, clazz);
		} catch (Exception e) {
			log.error("get value error. with key={}, hashKey={}", key, hashKey, e);
		}
		return null;
	}

	/**
	 * 批量获取
	 * @param key
	 * @param hashKeys
	 * @param clazz
	 * @return
	 */
	public <V> List<V> multiGet(String key, Collection<String> hashKeys, Class<V> clazz) {
		try {
			List<String> list = hashOperationsReadOnly.multiGet(key, hashKeys);
			if (CollectionUtils.isEmpty(list)) {
				log.warn("multiGet error. with result is empty. with key={}, hashKeys={}", key, hashKeys);
				return null;
			}
			List<V> l = new ArrayList<>(list.size());
			for (String str : list) {
				l.add(RedisUtil.string2Value(str, clazz));
			}
			return l;
		} catch (Exception e) {
			log.error("multiGet error. with key={}, hashKeys={}", key, hashKeys, e);
		}
		return null;
	}

	/**
	 * 自增
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public Long increment(String key, String hashKey, long delta) {
		try {
			return hashOperations.increment(key, hashKey, delta);
		} catch (Exception e) {
			log.error("increment error. with key={}, hashKey={}, delta={}", key, hashKey, delta, e);
		}
		return 0L;
	}

	/**
	 * 自增
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public Double increment(String key, String hashKey, double delta) {
		try {
			return hashOperations.increment(key, hashKey, delta);
		} catch (Exception e) {
			log.error("increment error. with key={}, hashKey={}, delta={}", key, hashKey, delta, e);
		}
		return 0D;
	}

	/**
	 * 获取键
	 * @param key
	 * @return
	 */
	public Set<String> keys(String key) {
		try {
			Set<String> set = hashOperationsReadOnly.keys(key);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("get keys error. with result is empty. with key={}", key);
				return null;
			}
			return set;
		} catch (Exception e) {
			log.error("get keys error. with key={}", key, e);
		}
		return null;
	}

	/**
	 * 获取大小
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		try {
			return hashOperationsReadOnly.size(key);
		} catch (Exception e) {
			log.error("get size error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * 全部增加
	 * @param key
	 * @param m
	 * @param timeout
	 * @param unit
	 */
	public <V> void putAll(String key, Map<String, V> m, long timeout, TimeUnit unit) {
		try {
			if (MapUtils.isEmpty(m)) {
				log.warn("putAll error. with map is empty. with key={}", key);
				return;
			}
			Map<String, String> map = new HashMap<>(m.size());
			for (Entry<String, V> entry : m.entrySet()) {
				map.put(entry.getKey(), RedisUtil.value2String(entry.getValue()));
			}
			hashOperations.putAll(key, map);
			hpRedisTemplate.expire(key, timeout, unit);
		} catch (Exception e) {
			log.error("putAll error. with key={}", key, e);
			//设置失败，删除key
			//delete(key);
		}
	}

	/**
	 * put
	 * @param key
	 * @param hashKey
	 * @param value
	 * @param timeout
	 * @param unit
	 */
	public <V> void put(String key, String hashKey, V value, long timeout, TimeUnit unit) {
		try {
			String v = RedisUtil.value2String(value);
			hashOperations.put(key, hashKey, v);
			hpRedisTemplate.expire(key, timeout, unit);
		} catch (Exception e) {
			log.error("put value error. with key={}, hashKey={}, value={}", key, hashKey, value, e);
			//设置失败，删除key
			delete(key, hashKey);
		}
	}

	/**
	 * putIfAbsent
	 * @param key
	 * @param hashKey
	 * @param value
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Boolean putIfAbsent(String key, String hashKey, Object value, long timeout, TimeUnit unit) {
		try {
			String v = RedisUtil.value2String(value);
			Boolean b = hashOperations.putIfAbsent(key, hashKey, v);
			hpRedisTemplate.expire(key, timeout, unit);
			return b;
		} catch (Exception e) {
			log.error("putIfAbsent value error. with key={}, hashKey={}, value={}", key, hashKey, value, e);
			//设置失败，删除key
			delete(key, hashKey);
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取所有值
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> List<V> values(String key, Class<V> clazz) {
		try {
			List<String> list = hashOperationsReadOnly.values(key);
			if (CollectionUtils.isEmpty(list)) {
				log.warn("get values error. with result is empty. with key={}", key);
				return null;
			}
			List<V> l = new ArrayList<>(list.size());
			for (String str : list) {
				l.add(RedisUtil.string2Value(str, clazz));
			}
			return l;
		} catch (Exception e) {
			log.error("values error. with key={}", key, e);
		}
		return null;
	}

	/**
	 * 获取，转为map
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> Map<String, V> entries(String key, Class<V> clazz) {
		try {
			Map<String, String> map = hashOperationsReadOnly.entries(key);
			if (MapUtils.isEmpty(map)) {
				log.warn("entries error. with result is empty. with key={}", key);
				return null;
			}
			Map<String, V> m = new HashMap<>(map.size());
			for (Entry<String, String> entry : map.entrySet()) {
				m.put(entry.getKey(), RedisUtil.string2Value(entry.getValue(), clazz));
			}
			return m;
		} catch (Exception e) {
			log.error("entries error. with key={}", key, e);
		}
		return null;
	}

	public <V> Cursor<Entry<String, V>> scan(String key, ScanOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	public RedisOperations<String, ?> getOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	public HPRedisTemplate getHpRedisTemplate() {
		return hpRedisTemplate;
	}

	public void setHpRedisTemplate(HPRedisTemplate hpRedisTemplate) {
		this.hpRedisTemplate = hpRedisTemplate;
	}

	public HashOperations<String, String, String> getHashOperations() {
		return hashOperations;
	}

	public void setHashOperations(HashOperations<String, String, String> hashOperations) {
		this.hashOperations = hashOperations;
	}

	public HashOperations<String, String, String> getHashOperationsReadOnly() {
		return hashOperationsReadOnly;
	}

	public void setHashOperationsReadOnly(HashOperations<String, String, String> hashOperationsReadOnly) {
		this.hashOperationsReadOnly = hashOperationsReadOnly;
	}
}
