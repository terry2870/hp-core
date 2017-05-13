/**
 * 
 */
package com.hp.core.limiter.api;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author ping.huang
 * 2017年5月10日
 */
public interface LimiterService extends DisposableBean {

	/**
	 * 初始化
	 */
	public void init();
	
	/**
	 * 获取一个许可
	 * @return
	 */
	default public double acquire() throws Exception {
		return acquire(1);
	}
	
	/**
	 * 获取指定许可
	 * @param permits
	 * @return
	 */
	public double acquire(int permits) throws Exception;
	
	/**
	 * 试图获取一个令牌（超过指定时间，则直接返回）
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	default public boolean tryAcquire(long timeout, TimeUnit unit) throws Exception {
		return tryAcquire(1, timeout, unit);
	}
	
	/**
	 * 试图获取指定的令牌（超过指定时间，则直接返回）
	 * @param permits
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws Exception;
	
	/**
	 * 释放一个许可
	 */
	default public void release() {
		release(1);
	}
	
	/**
	 * 释放指定个数许可
	 * @param permits
	 */
	public void release(int permits);
	
}
