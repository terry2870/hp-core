/**
 * 
 */
package com.hp.core.limiter.api.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;
import com.hp.core.limiter.api.LimiterService;

/**
 * google的令牌桶算法限流
 * @author ping.huang
 * 2017年5月10日
 */
public class RateLimiterImpl implements LimiterService {

	static Logger log = LoggerFactory.getLogger(RateLimiterImpl.class);
	
	RateLimiter limiter = null;
	
	/**
	 * 每秒生成的令牌数
	 */
	private double permitsPerSecond;
	
	private long warmupPeriod;
	
	private TimeUnit unit = TimeUnit.MILLISECONDS;
	
	public RateLimiterImpl() {
	}
	
	public RateLimiterImpl(double permitsPerSecond) {
		this();
		this.permitsPerSecond = permitsPerSecond;
	}
	
	public RateLimiterImpl(double permitsPerSecond, long warmupPeriod) {
		this(permitsPerSecond);
		this.warmupPeriod = warmupPeriod;
	}
	
	@Override
	public void init() {
		log.info("init RateLimiter start. with permitsPerSecond={}, warmupPeriod={}", permitsPerSecond, warmupPeriod);
		if (limiter != null) {
			return;
		}
		if (warmupPeriod == 0) {
			limiter = RateLimiter.create(permitsPerSecond);
		} else {
			limiter = RateLimiter.create(permitsPerSecond, warmupPeriod, unit);
		}
		log.info("init RateLimiter success. with permitsPerSecond={}, warmupPeriod={}", permitsPerSecond, warmupPeriod);
	}

	@Override
	public double acquire(int permits) throws Exception {
		return limiter.acquire(permits);
	}

	@Override
	public void release(int permits) {
		// do nothing
	}
	
	@Override
	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws Exception {
		return limiter.tryAcquire(permits, timeout, unit);
	}

	public double getPermitsPerSecond() {
		return permitsPerSecond;
	}

	public void setPermitsPerSecond(double permitsPerSecond) {
		this.permitsPerSecond = permitsPerSecond;
	}

	public long getWarmupPeriod() {
		return warmupPeriod;
	}

	public void setWarmupPeriod(long warmupPeriod) {
		this.warmupPeriod = warmupPeriod;
	}

	@Override
	public void destroy() throws Exception {
		limiter = null;
	}

}
