/**
 * 
 */
package com.hp.core.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author ping.huang
 * 2017年5月10日
 */
public class LimitTest {

	static Logger log = LoggerFactory.getLogger(LimitTest.class);
	
	public static void main(String[] args) {
		log.info("1111111");
		RateLimiter limiter = RateLimiter.create(10);
		log.info("start");
		log.info("1= " + String.valueOf(limiter.acquire(20)));
		log.info("2= " + String.valueOf(limiter.acquire()));
		log.info("3= " + String.valueOf(limiter.acquire()));
		log.info("4= " + String.valueOf(limiter.acquire()));
		log.info("5= " + String.valueOf(limiter.acquire()));
		log.info("6= " + String.valueOf(limiter.acquire()));
		log.info("7= " + String.valueOf(limiter.acquire()));
		log.info("8= " + String.valueOf(limiter.acquire()));
		log.info("9= " + String.valueOf(limiter.acquire()));
		log.info(limiter.toString());
	}
}
