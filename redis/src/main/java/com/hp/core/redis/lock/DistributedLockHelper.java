/**
 * 
 */
package com.hp.core.redis.lock;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.core.redis.HPRedisTemplate;
import com.hp.core.redis.HPValueOperations;

/**
 * 基于redis的一个分布式锁
 * @author huangping
 * 2018年3月6日
 */
@Component
public class DistributedLockHelper {

	static Logger log = LoggerFactory.getLogger(DistributedLockHelper.class);
	
	//默认超时时间（默认两分钟）
	private static final long DEFAULT_TIME_OUT = 120;
	//最大等待时间
	private static final long MAX_WAIT_TIME = 5;
	@Autowired
	private HPValueOperations hpValueOperations;
	@Autowired
	private HPRedisTemplate hpRedisTemplate;
	
	/**
	 * 获取锁
	 * @param key
	 * @return
	 */
	public boolean getLock(String key) {
		return getLock(key, DEFAULT_TIME_OUT, MAX_WAIT_TIME);
	}
	
	/**
	 * 获取锁
	 * @param key
	 * @param timeout
	 * @param maxWaitTime
	 * @return
	 */
	public boolean getLock(String key, long timeout, long maxWaitTime) {
		log.info("getLock with key={}", key);
		Boolean lock = null;
		long start = System.currentTimeMillis(), end = 0;
		while (true) {
			lock = hpValueOperations.setIfAbsent(key, "1", timeout, TimeUnit.SECONDS);
			if (lock != null && lock.booleanValue()) {
				//获取锁，直接退出
				log.info("getLock success. with key={}", key);
				return true;
			}
			if (maxWaitTime == 0) {
				//没有配置超时时间，直接返回
				log.warn("getLock error. with have not get lock. with key={}", key);
				return false;
			}
			//没有获取到，继续等待
			//判断是否超时
			end = System.currentTimeMillis();
			if ((end - start) > maxWaitTime * 1000) {
				log.error("getLock error. timeout with key={}", key);
				return false;
			}
			//线程等待
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * 释放锁
	 * @param key
	 */
	public void releaseLock(String key) {
		log.info("releaseLock with key={}", key);
		hpRedisTemplate.delete(key);
	}
}
