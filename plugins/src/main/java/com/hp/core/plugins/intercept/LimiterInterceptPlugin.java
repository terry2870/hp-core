/**
 * 
 */
package com.hp.core.plugins.intercept;

import java.util.concurrent.TimeUnit;

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
public class LimiterInterceptPlugin implements AroundInterceptHandle {

	static Logger log = LoggerFactory.getLogger(LimiterInterceptPlugin.class);
	
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
	
	@Override
	public boolean onBefore(Object target, String methodName, Object[] args) {
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
			log.debug("get acquire with target={}, methodName={}", target, methodName);
			return true;
		} catch (Exception e) {
			log.error("onBefore error. with target={}, methodName={}", target, methodName);
		}
		return false;
	}
	
	@Override
	public void onAfter(Object target, String methodName, Object[] args, Object result) {
		//回收资源
		limiterService.release();
		log.debug("release with target={}, methodName={}", target, methodName);
	}


	public void setLimitType(byte limitType) {
		this.limitType = limitType;
	}

	public void setPermitsPerSecond(double permitsPerSecond) {
		this.permitsPerSecond = permitsPerSecond;
	}

	public void setWarmupPeriod(long warmupPeriod) {
		this.warmupPeriod = warmupPeriod;
	}

	public void setPermits(int permits) {
		this.permits = permits;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
