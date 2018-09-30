/**
 * 
 */
package com.hp.core.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author ping.huang
 * 2017年5月12日
 */
public class HPRedisTemplate extends StringRedisTemplate {

	static Logger log = LoggerFactory.getLogger(HPRedisTemplate.class);
	
	/**
	 * 删除key
	 * 
	 * @param key
	 * @param value
	 */
	public Boolean delete(String key) {
		if (key == null) {
			log.warn("HPRedisTemplate delete redis error with key is empty");
			return Boolean.FALSE;
		}
		try {
			return super.delete(key);
		} catch (Exception e) {
			log.error("RedisValueHelper delete redis error. with key={}", key, e);
			//删除失败，重试一次
			try {
				super.delete(key);
			} catch (Exception e2) {
				log.error("RedisValueHelper delete redis again error. with key={}", key, e);
				throw e2;
			}
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 批量删除
	 */
	public Long delete(Collection<String> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			log.warn("HPRedisTemplate delete redis error with key is empty");
			return 0L;
		}
		try {
			return super.delete(keys);
		} catch (Exception e) {
			log.error("RedisValueHelper delete redis error. with key={}s", keys, e);
			//删除失败，重试一次
			try {
				super.delete(keys);
			} catch (Exception e2) {
				log.error("RedisValueHelper delete redis again error. with keys={}", keys, e);
				throw e2;
			}
			return 0L;
		}
	}
	
	/**
	 * 设置超时
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void setTimeout(String key, long timeout, TimeUnit unit) {
		try {
			super.expire(key, timeout, unit);
		} catch (Exception e) {
			log.error("setTimepout error. with key={}", key, e);
			delete(key);
		}
	}
}
