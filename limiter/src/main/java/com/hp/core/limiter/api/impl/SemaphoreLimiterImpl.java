/**
 * 
 */
package com.hp.core.limiter.api.impl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.limiter.api.LimiterService;

/**
 * 通行证限流
 * @author ping.huang
 * 2017年5月11日
 */
public class SemaphoreLimiterImpl implements LimiterService {
	
	static Logger log = LoggerFactory.getLogger(SemaphoreLimiterImpl.class);

	Semaphore semp = null;
	
	/**
	 * 最大允许的个数
	 */
	private int permits;
	private boolean fair;
	
	public SemaphoreLimiterImpl() {
	}
	
	public SemaphoreLimiterImpl(int permits) {
		this();
		this.permits = permits;
	}
	
	public SemaphoreLimiterImpl(int permits, boolean fair) {
		this(permits);
		this.fair = fair;
	}
	
	
	@Override
	public void init() {
		log.info("init Semaphore start. with permits={}, fair={}", permits, fair);
		
		if (semp != null) {
			return;
		}
		semp = new Semaphore(permits, fair);
		
		log.info("init Semaphore success with permits={}, fair={}", permits, fair);
	}

	@Override
	public double acquire(int permits) throws Exception {
		semp.acquire(permits);
		return 0;
	}

	@Override
	public void release(int permits) {
		semp.release(permits);
	}
	
	@Override
	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws Exception {
		return semp.tryAcquire(permits, timeout, unit);
	}
	
	@Override
	public void destroy() throws Exception {
		semp = null;
	}

	public int getPermits() {
		return permits;
	}

	public void setPermits(int permits) {
		this.permits = permits;
	}

	public boolean isFair() {
		return fair;
	}

	public void setFair(boolean fair) {
		this.fair = fair;
	}

}
