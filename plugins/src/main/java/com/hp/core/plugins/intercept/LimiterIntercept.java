/**
 * 
 */
package com.hp.core.plugins.intercept;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.limiter.api.LimiterService;
import com.hp.core.limiter.enums.LimiterTypeEnum;
import com.hp.tools.common.exceptions.TryAcquireErrorException;

/**
 * 限流拦截器插件
 * @author ping.huang
 * 2017年5月11日
 */
public class LimiterIntercept {

	static Logger log = LoggerFactory.getLogger(LimiterIntercept.class);
	
	private static final int DEFAULT_MAX_SIZE = 1000;
	
	private LimiterService limiterService;
	
	/**
	 * 限流实现类
	 * 1-令牌桶算法
	 * 2-信号量算法
	 */
	private byte limitType = LimiterTypeEnum.RATELIMITER.getValue();
	
	/**
	 * 令牌桶算法-每秒生成令牌
	 * 默认每秒1000个
	 */
	private double permitsPerSecond = DEFAULT_MAX_SIZE;
	private long warmupPeriod;
	
	/**
	 * 信号量算法-最大允许的个数
	 * 默认最大1000个
	 */
	private int permits = DEFAULT_MAX_SIZE;
	
	/**
	 * 是否是阻塞模式
	 */
	private boolean blocking = true;
	
	/**
	 * 非阻塞模式下，最大等待时间
	 */
	private long timeout = 1000;
	
	public void init() {
		log.info("init LimiterIntercept start");
		if (limiterService != null) {
			log.warn("limiterService is not null");
			return;
		}
		
		//根据限流类型，获取实现类
		limiterService = LimiterTypeEnum.getLimiterService(limitType, permitsPerSecond, warmupPeriod, permits);
		
		//初始化限流实现
		limiterService.init();
		log.info("init LimiterIntercept success");
	}
	
	/**
	 * 环绕方法拦截
	 * @param join
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint join) throws Throwable {
		try {
			//获取许可
			if (blocking) {
				//阻塞模式，一直等待
				limiterService.acquire();
			} else {
				if (!limiterService.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
					//非阻塞模式，指定时间内获取不到，抛异常
					throw new TryAcquireErrorException("tryAcquire timeout. with timeout=" + timeout);
				}
			}
			
			//执行方法
			Object obj = join.proceed();
			return obj;
		} finally {
			//回收资源
			limiterService.release();
		}
	}

	public byte getLimitType() {
		return limitType;
	}

	public void setLimitType(byte limitType) {
		this.limitType = limitType;
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

	public int getPermits() {
		return permits;
	}

	public void setPermits(int permits) {
		this.permits = permits;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
