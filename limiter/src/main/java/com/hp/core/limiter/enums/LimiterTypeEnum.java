/**
 * 限流枚举操作类
 */
package com.hp.core.limiter.enums;

import com.hp.core.limiter.api.LimiterService;
import com.hp.core.limiter.api.impl.RateLimiterImpl;
import com.hp.core.limiter.api.impl.SemaphoreLimiterImpl;

/**
 * @author ping.huang
 * 2017年5月11日
 */
public enum LimiterTypeEnum {

	/**
	 * 令牌桶算法
	 */
	RATELIMITER((byte) 1),
	
	/**
	 * 信号量算法
	 */
	SEMAPHORE((byte) 2),
	;
	
	private byte value;
	
	private LimiterTypeEnum(Byte value) {
		this.value = value;
	}
	
	/**
	 * 获取限流的实现类
	 * @param value
	 * @return
	 */
	public static LimiterService getLimiterService(byte value, double permitsPerSecond, long warmupPeriod, int permits) {
		if (value == RATELIMITER.getValue()) {
			return new RateLimiterImpl(permitsPerSecond, warmupPeriod);
		} else if (value == SEMAPHORE.getValue()) {
			return new SemaphoreLimiterImpl(permits);
		}
		return null;
	}

	public byte getValue() {
		return value;
	}
}
