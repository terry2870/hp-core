/**
 * 
 */
package com.hp.core.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import com.hp.core.redis.utils.RedisUtil;


/**
 * @author ping.huang
 * 2017年5月17日
 */
public class HPSetOperations {

	
	static Logger log = LoggerFactory.getLogger(HPSetOperations.class);
	
	private HPRedisTemplate hpRedisTemplate;
	
	private SetOperations<String, String> setOperations;

	private SetOperations<String, String> setOperationsReadOnly;

	/**
	 * 增加
	 * @param key
	 * @param values
	 * @return
	 */
	public Long add(String key, Object... values) {
		log.info("add start. with key={}", key);
		if (ArrayUtils.isEmpty(values)) {
			log.warn("add error. with values is empty. with key={}", key);
			return null;
		}
		try {
			String[] arr = new String[values.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = RedisUtil.value2String(values[i]);
			}
			Long result = setOperations.add(key, arr);
			return result;
		} catch (Exception e) {
			log.error("add error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * 删除
	 * @param key
	 * @param values
	 * @return
	 */
	public Long remove(String key, Object... values) {
		log.info("remove values start. with key={}", key);
		if (ArrayUtils.isEmpty(values)) {
			log.warn("remove values error. with values is empty with key={}", key);
			return null;
		}
		try {
			return setOperations.remove(key, values);
		} catch (Exception e) {
			log.error("remove error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * pop
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V pop(String key, Class<V> clazz) {
		log.info("pop start with key={}", key);
		try {
			String value = setOperations.pop(key);
			if (StringUtils.isEmpty(value)) {
				log.warn("pop error. with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(value, clazz);
		} catch (Exception e) {
			log.error("pop error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * move
	 * @param key
	 * @param value
	 * @param destKey
	 * @return
	 */
	public Boolean move(String key, Object value, String destKey) {
		log.info("move start. with key={}, destKey={}", key, destKey);
		try {
			return setOperations.move(key, RedisUtil.value2String(value), destKey);
		} catch (Exception e) {
			log.error("move error. with exception. with key={}, destKey={}", key, destKey, e);
		}
		return null;
	}

	/**
	 * size
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		log.info("get size start. with key={}", key);
		try {
			return setOperationsReadOnly.size(key);
		} catch (Exception e) {
			log.error("get size error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * isMember
	 * @param key
	 * @param o
	 * @return
	 */
	public Boolean isMember(String key, Object o) {
		log.info("isMember start. with key={}", key);
		if (o == null) {
			log.warn("isMember error. with o=null. with key={}", key);
			return Boolean.FALSE;
		}
		try {
			return setOperationsReadOnly.isMember(key, o);
		} catch (Exception e) {
			log.error("isMember error. with exception. with key={}", key);
		}
		return false;
	}

	/**
	 * 取交集
	 * @param key
	 * @param otherKey
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> intersect(String key, String otherKey, Class<V> clazz) {
		log.info("intersect start. with key={}, otherKey={}", key, otherKey);
		try {
			Set<String> value = setOperationsReadOnly.intersect(key, otherKey);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("intersect error. with result is empty. with key={}, otherKey={}", key, otherKey);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("intersect error. with exception. with key={}, otherKey={}", key, otherKey, e);
		}
		return null;
	}

	/**
	 * 取交集
	 * @param key
	 * @param otherKeys
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> intersect(String key, Collection<String> otherKeys, Class<V> clazz) {
		log.info("intersect start. with key={}, otherKeys={}", key, otherKeys);
		try {
			Set<String> value = setOperationsReadOnly.intersect(key, otherKeys);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("intersect error. with result is empty. with key={}, otherKeys={}", key, otherKeys);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("intersect error. with exception. with key={}, otherKeys={}", key, otherKeys, e);
		}
		return null;
	}

	/**
	 * 取交集，并且存储
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long intersectAndStore(String key, String otherKey, String destKey) {
		log.info("intersectAndStore start. with key={}, otherKey={}, destKey={}", key, otherKey, destKey);
		try {
			return setOperations.intersectAndStore(key, otherKey, destKey);
		} catch (Exception e) {
			log.error("intersectAndStore error. with exception. with key={}, otherKey={}, destKey={}", key, otherKey, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取交集，并且存储（带超时）
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long intersectAndStore(String key, String otherKey, String destKey, long timeout, TimeUnit unit) {
		Long result = intersectAndStore(key, otherKey, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取交集，并且存储
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
		log.info("intersectAndStore start. with key={}, otherKeys={}, destKey={}", key, otherKeys, destKey);
		try {
			return setOperations.intersectAndStore(key, otherKeys, destKey);
		} catch (Exception e) {
			log.error("intersectAndStore error. with exception. with key={}, otherKeys={}, destKey={}", key, otherKeys, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取交集，并且存储（带超时）
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey, long timeout, TimeUnit unit) {
		Long result = intersectAndStore(key, otherKeys, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取并集
	 * @param key
	 * @param otherKey
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> union(String key, String otherKey, Class<V> clazz) {
		log.info("union start. with key={}, otherKey={}", key, otherKey);
		try {
			Set<String> value = setOperationsReadOnly.union(key, otherKey);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("union error. with value is empty. with key={}, otherKey={}", key, otherKey);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("union error. with value iexception. with key={}, otherKey={}", key, otherKey, e);
		}
		return null;
	}

	/**
	 * 取并集
	 * @param key
	 * @param otherKeys
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> union(String key, Collection<String> otherKeys, Class<V> clazz) {
		log.info("union start. with key={}, otherKeys={}", key, otherKeys);
		try {
			Set<String> value = setOperationsReadOnly.union(key, otherKeys);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("union error. with value is empty. with key={}, otherKeys={}", key, otherKeys);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("union error. with exception. with key={}, otherKeys={}", key, otherKeys, e);
		}
		return null;
	}

	/**
	 * 取并集，并且存储
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, String otherKey, String destKey) {
		log.info("unionAndStore start. with key={}, otherKey={}", key, otherKey);
		try {
			return setOperations.unionAndStore(key, otherKey, destKey);
		} catch (Exception e) {
			log.error("unionAndStore error. with exception. with key={}, otherKey={}", key, otherKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取并集，并且存储（带超时）
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long unionAndStore(String key, String otherKey, String destKey, long timeout, TimeUnit unit) {
		Long result = unionAndStore(key, otherKey, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取并集，并且存储
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
		log.info("unionAndStore start. with key={}, otherKeys={}", key, otherKeys);
		try {
			return setOperations.unionAndStore(key, otherKeys, destKey);
		} catch (Exception e) {
			log.error("unionAndStore error. with exception. with key={}, otherKeys={}", key, otherKeys, e);
		}
		return 0L;
	}
	
	/**
	 * 取并集，并且存储（带超时）
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long unionAndStore(String key, Collection<String> otherKeys, String destKey, long timeout, TimeUnit unit) {
		Long result = unionAndStore(key, otherKeys, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取差集
	 * @param key
	 * @param otherKey
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> difference(String key, String otherKey, Class<V> clazz) {
		log.info("difference start. with value is empty. with key={}, otherKey={}", key, otherKey);
		try {
			Set<String> value = setOperationsReadOnly.difference(key, otherKey);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("difference error. with value is empty. with key={}, otherKey={}", key, otherKey);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("difference error. with exception. with key={}, otherKey={}", key, otherKey, e);
		}
		return null;
	}

	/**
	 * 取差集
	 * @param key
	 * @param otherKeys
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> difference(String key, Collection<String> otherKeys, Class<V> clazz) {
		log.info("difference start. with value is empty. with key={}, otherKeys={}", key, otherKeys);
		try {
			Set<String> value = setOperationsReadOnly.difference(key, otherKeys);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("difference error. with value is empty. with key={}, otherKeys={}", key, otherKeys);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("difference error. with exception. with key={}, otherKeys={}", key, otherKeys, e);
		}
		return null;
	}

	/**
	 * 取差集，并存储
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long differenceAndStore(String key, String otherKey, String destKey) {
		log.info("differenceAndStore start. key={}, otherKey={}, destKey={}", key, otherKey, destKey);
		try {
			return setOperations.differenceAndStore(key, otherKey, destKey);
		} catch (Exception e) {
			log.error("differenceAndStore error. with exception. key={}, otherKey={}, destKey={}", key, otherKey, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取差集，并存储（带超时）
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long differenceAndStore(String key, String otherKey, String destKey, long timeout, TimeUnit unit) {
		Long result = differenceAndStore(key, otherKey, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取差集，并存储
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
		log.info("differenceAndStore start. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey);
		try {
			return setOperations.differenceAndStore(key, otherKeys, destKey);
		} catch (Exception e) {
			log.error("differenceAndStore error. with exception. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取差集，并存储（带超时）
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long differenceAndStore(String key, Collection<String> otherKeys, String destKey, long timeout, TimeUnit unit) {
		Long result = differenceAndStore(key, otherKeys, destKey);
		hpRedisTemplate.setTimeout(destKey, timeout, unit);
		return result;
	}

	/**
	 * 获取所有值
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> members(String key, Class<V> clazz) {
		log.info("get members start. with key={}", key);
		try {
			Set<String> value = setOperationsReadOnly.members(key);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("get members error. with result is empty. with key={}", key);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("get members error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * 随机元素
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V randomMember(String key, Class<V> clazz) {
		log.info("get randomMember start. with key={}", key);
		try {
			String value = setOperationsReadOnly.randomMember(key);
			if (StringUtils.isEmpty(value)) {
				log.warn("get randomMember error. with value is empty. with key={}", key);
				return null;
			}
			return RedisUtil.string2Value(value, clazz);
		} catch (Exception e) {
			log.error("get randomMember error. with exception. with key={}", key, e);
		}
		return null;
	}

	/**
	 * 取不重复的随机个数
	 * @param key
	 * @param count
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> distinctRandomMembers(String key, long count, Class<V> clazz) {
		log.info("get distinctRandomMembers start. with key={}, count={}", key, count);
		try {
			Set<String> value = setOperationsReadOnly.distinctRandomMembers(key, count);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("get distinctRandomMembers error. with result is empty. with key={}, count={}", key, count);
				return null;
			}
			return RedisUtil.convertSet(value, clazz);
		} catch (Exception e) {
			log.error("get distinctRandomMembers error. with exception. with key={}, count={}", key, count, e);
		}
		return null;
	}

	/**
	 * 获取随机个元素
	 * @param key
	 * @param count
	 * @param clazz
	 * @return
	 */
	public <V> List<V> randomMembers(String key, long count, Class<V> clazz) {
		log.info("get randomMembers start. with key={}, count={}", key, count);
		try {
			List<String> value = setOperationsReadOnly.randomMembers(key, count);
			if (CollectionUtils.isEmpty(value)) {
				log.warn("get randomMembers error. with result is empty. with key={}, count={}", key, count);
				return null;
			}
			return RedisUtil.convertList(value, clazz);
		} catch (Exception e) {
			log.error("get randomMembers error. with exception. with key={}, count={}", key, count, e);
		}
		return null;
	}

	public <V> Cursor<V> scan(String key, ScanOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	public <V> RedisOperations<String, V> getOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	public HPRedisTemplate getHpRedisTemplate() {
		return hpRedisTemplate;
	}

	public void setHpRedisTemplate(HPRedisTemplate hpRedisTemplate) {
		this.hpRedisTemplate = hpRedisTemplate;
	}

	public SetOperations<String, String> getSetOperations() {
		return setOperations;
	}

	public void setSetOperations(SetOperations<String, String> setOperations) {
		this.setOperations = setOperations;
	}

	public SetOperations<String, String> getSetOperationsReadOnly() {
		return setOperationsReadOnly;
	}

	public void setSetOperationsReadOnly(SetOperations<String, String> setOperationsReadOnly) {
		this.setOperationsReadOnly = setOperationsReadOnly;
	}
}
