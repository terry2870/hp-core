/**
 * 
 */
package com.hp.core.redis;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import com.hp.core.redis.utils.RedisUtil;


/**
 * @author ping.huang
 * 2017年5月17日
 */
public class HPZSetOperations {

	
	static Logger log = LoggerFactory.getLogger(HPZSetOperations.class);
	
	private HPRedisTemplate hpRedisTemplate;
	
	private ZSetOperations<String, String> zSetOperations;

	private ZSetOperations<String, String> zSetOperationsReadOnly;

	/**
	 * add
	 * @param key
	 * @param value
	 * @param score
	 * @return
	 */
	public Boolean add(String key, Object value, double score) {
		log.info("add start. with key={}, value={}, score={}", key, value, score);
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("add error. value is null. with key={}, score={}", key, score);
			return Boolean.FALSE;
		}
		try {
			return zSetOperations.add(key, v, score);
		} catch (Exception e) {
			log.error("add error. with key={}, value={}, score={}", key, value, score, e);
		}
		return Boolean.FALSE;
	}

	
	public Long add(String key, Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<Object>> tuples) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * remove
	 * @param key
	 * @param values
	 * @return
	 */
	public Long remove(String key, Object... values) {
		log.info("remove start. with key={}", key);
		try {
			return zSetOperations.remove(key, values);
		} catch (Exception e) {
			log.error("remove error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * incrementScore
	 * @param key
	 * @param value
	 * @param delta
	 * @return
	 */
	public Double incrementScore(String key, Object value, double delta) {
		log.info("incrementScore start. with key={}, value={}, delta={}", key, value, delta);
		String v = RedisUtil.value2String(value);
		if (v == null) {
			log.warn("incrementScore error. value is null. with key={}, delta={}", key, delta);
			return 0D;
		}
		try {
			return zSetOperations.incrementScore(key, v, delta);
		} catch (Exception e) {
			log.error("incrementScore error. with key={}, value={}, delta={}", key, value, delta, e);
		}
		return 0D;
	}

	/**
	 * rank
	 * @param key
	 * @param o
	 * @return
	 */
	public Long rank(String key, Object o) {
		log.info("rank start. with key={}", key);
		try {
			return zSetOperations.rank(key, o);
		} catch (Exception e) {
			log.error("rank error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * reverseRank
	 * @param key
	 * @param o
	 * @return
	 */
	public Long reverseRank(String key, Object o) {
		log.info("reverseRank start. with key={}", key);
		try {
			return zSetOperations.reverseRank(key, o);
		} catch (Exception e) {
			log.error("reverseRank error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * 取指定位置的数据
	 * @param key
	 * @param start
	 * @param end
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> range(String key, long start, long end, Class<V> clazz) {
		log.info("range start. with key={}, start={}, end={}", key, start, end);
		try {
			Set<String> set = zSetOperationsReadOnly.range(key, start, end);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("range error. result is empty. with key={}, start={}, end={}", key, start, end);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("range error. with key={}, start={}, end={}", key, start, end, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeWithScores(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * rangeByScore
	 * @param key
	 * @param min
	 * @param max
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> rangeByScore(String key, double min, double max, Class<V> clazz) {
		log.info("rangeByScore start. with key={}, min={}, max={}", key, min, max);
		try {
			Set<String> set = zSetOperationsReadOnly.rangeByScore(key, min, max);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("rangeByScore error. result is empty. with key={}, min={}, max={}", key, min, max);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("rangeByScore error. with key={}, min={}, max={}", key, min, max, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * rangeByScore
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> rangeByScore(String key, double min, double max, long offset, long count, Class<V> clazz) {
		log.info("rangeByScore start. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count);
		try {
			Set<String> set = zSetOperationsReadOnly.rangeByScore(key, min, max, offset, count);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("rangeByScore error. result is empty. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("rangeByScore error. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * reverseRange
	 * @param key
	 * @param start
	 * @param end
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> reverseRange(String key, long start, long end, Class<V> clazz) {
		log.info("reverseRange start. with key={}, start={}, end={}", key, start, end);
		try {
			Set<String> set = zSetOperationsReadOnly.reverseRange(key, start, end);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("reverseRange error. result is empty. with key={}, start={}, end={}", key, start, end);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("reverseRange error. with key={}, start={}, end={}", key, start, end, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeWithScores(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * reverseRangeByScore
	 * @param key
	 * @param min
	 * @param max
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> reverseRangeByScore(String key, double min, double max, Class<V> clazz) {
		log.info("reverseRangeByScore start. with key={}, min={}, max={}", key, min, max);
		try {
			Set<String> set = zSetOperationsReadOnly.reverseRangeByScore(key, min, max);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("reverseRangeByScore error. result is empty. with key={}, min={}, max={}", key, min, max);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("reverseRangeByScore error. with key={}, min={}, max={}", key, min, max, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * reverseRangeByScore
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> reverseRangeByScore(String key, double min, double max, long offset, long count, Class<V> clazz) {
		log.info("reverseRangeByScore start. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count);
		try {
			Set<String> set = zSetOperationsReadOnly.reverseRangeByScore(key, min, max, offset, count);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("reverseRangeByScore error. result is empty. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("reverseRangeByScore error. with key={}, min={}, max={}, offset={}, count={}", key, min, max, offset, count, e);
		}
		return null;
	}

	public <V> Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * count
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long count(String key, double min, double max) {
		log.info("count start. with key={}, min={}, max={}", key, min, max);
		try {
			return zSetOperationsReadOnly.count(key, min, max);
		} catch (Exception e) {
			log.error("count error. with key={}, min={}, max={}", key, min, max, e);
		}
		return 0L;
	}

	/**
	 * size
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		log.info("size start. with key={}", key);
		try {
			return zSetOperationsReadOnly.size(key);
		} catch (Exception e) {
			log.error("size error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * zCard
	 * @param key
	 * @return
	 */
	public Long zCard(String key) {
		log.info("zCard start. with key={}", key);
		try {
			return zSetOperationsReadOnly.zCard(key);
		} catch (Exception e) {
			log.error("zCard error. with key={}", key, e);
		}
		return 0L;
	}

	/**
	 * score
	 * @param key
	 * @param o
	 * @return
	 */
	public Double score(String key, Object o) {
		log.info("score start. with key={}", key);
		try {
			return zSetOperationsReadOnly.score(key, o);
		} catch (Exception e) {
			log.error("score error. with key={}", key, e);
		}
		return 0D;
	}

	/**
	 * removeRange
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long removeRange(String key, long start, long end) {
		log.info("removeRange start. with key={}, start={}, end={}", key, start, end);
		try {
			return zSetOperations.removeRange(key, start, end);
		} catch (Exception e) {
			log.error("removeRange error. with key={}, start={}, end={}", key, start, end, e);
		}
		return 0L;
	}

	/**
	 * removeRangeByScore
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long removeRangeByScore(String key, double min, double max) {
		log.info("removeRangeByScore start. with key={}, min={}, max={}", key, min, max);
		try {
			return zSetOperations.removeRangeByScore(key, min, max);
		} catch (Exception e) {
			log.error("removeRangeByScore error. with key={}, min={}, max={}", key, min, max, e);
		}
		return 0L;
	}

	/**
	 * 合并，然后存储
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, String otherKey, String destKey) {
		log.info("unionAndStore start. key={}, otherKey={}, destKey={}", key, otherKey, destKey);
		try {
			return zSetOperations.unionAndStore(key, otherKey, destKey);
		} catch (Exception e) {
			log.error("unionAndStore error. with exception. key={}, otherKey={}, destKey={}", key, otherKey, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 合并，然后存储（带超时）
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long unionAndStore(String key, String otherKey, String destKey, long timeout, TimeUnit unit) {
		Long result = unionAndStore(key, otherKey, destKey);
		hpRedisTemplate.expire(destKey, timeout, unit);
		return result;
	}
	
	/**
	 * 合并，然后存储
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
		log.info("unionAndStore start. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey);
		try {
			return zSetOperations.unionAndStore(key, otherKeys, destKey);
		} catch (Exception e) {
			log.error("unionAndStore error. with exception. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 合并，然后存储（带超时）
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long unionAndStore(String key, Collection<String> otherKeys, String destKey, long timeout, TimeUnit unit) {
		Long result = unionAndStore(key, otherKeys, destKey);
		hpRedisTemplate.expire(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取交集，然后存储
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long intersectAndStore(String key, String otherKey, String destKey) {
		log.info("intersectAndStore start. key={}, otherKey={}, destKey={}", key, otherKey, destKey);
		try {
			return zSetOperations.intersectAndStore(key, otherKey, destKey);
		} catch (Exception e) {
			log.error("intersectAndStore error. with exception. key={}, otherKey={}, destKey={}", key, otherKey, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取交集，然后存储（带超时）
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long intersectAndStore(String key, String otherKey, String destKey, long timeout, TimeUnit unit) {
		Long result = intersectAndStore(key, otherKey, destKey);
		hpRedisTemplate.expire(destKey, timeout, unit);
		return result;
	}

	/**
	 * 取交集，然后存储
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
		log.info("intersectAndStore start. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey);
		try {
			return zSetOperations.intersectAndStore(key, otherKeys, destKey);
		} catch (Exception e) {
			log.error("intersectAndStore error. with exception. key={}, otherKeys={}, destKey={}", key, otherKeys, destKey, e);
		}
		return 0L;
	}
	
	/**
	 * 取交集，然后存储（带超时）
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey, long timeout, TimeUnit unit) {
		Long result = intersectAndStore(key, otherKeys, destKey);
		hpRedisTemplate.expire(destKey, timeout, unit);
		return result;
	}

	
	public <V> Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> scan(String key, ScanOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * rangeByLex
	 * @param key
	 * @param range
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> rangeByLex(String key, Range range, Class<V> clazz) {
		log.info("rangeByLex start. with key={}", key);
		try {
			Set<String> set = zSetOperations.rangeByLex(key, range);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("rangeByLex error. result is empty. with key={}", key);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("rangeByLex error. with key={}", key, e);
		}
		return null;
	}

	/**
	 * rangeByLex
	 * @param key
	 * @param range
	 * @param limit
	 * @param clazz
	 * @return
	 */
	public <V> Set<V> rangeByLex(String key, Range range, Limit limit, Class<V> clazz) {
		log.info("rangeByLex start. with key={}", key);
		try {
			Set<String> set = zSetOperations.rangeByLex(key, range, limit);
			if (CollectionUtils.isEmpty(set)) {
				log.warn("rangeByLex error. result is empty. with key={}", key);
				return null;
			}
			return RedisUtil.convertSet(set, clazz);
		} catch (Exception e) {
			log.error("rangeByLex error. with key={}", key, e);
		}
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

	public ZSetOperations<String, String> getzSetOperations() {
		return zSetOperations;
	}

	public void setzSetOperations(ZSetOperations<String, String> zSetOperations) {
		this.zSetOperations = zSetOperations;
	}

	public ZSetOperations<String, String> getzSetOperationsReadOnly() {
		return zSetOperationsReadOnly;
	}

	public void setzSetOperationsReadOnly(ZSetOperations<String, String> zSetOperationsReadOnly) {
		this.zSetOperationsReadOnly = zSetOperationsReadOnly;
	}
}
