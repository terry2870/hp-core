/**
 * 
 */
package com.hp.core.database.interceptor;

import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 强制到主库的拦截器
 * @author huangping
 * Jan 17, 2020
 */
public class ForceMasterInterceptor {

	private static Logger log = LoggerFactory.getLogger(ForceMasterInterceptor.class);
	
	private static final ThreadLocal<Boolean> forceMaster = new InheritableThreadLocal<Boolean>();
	
	/**
	 * 设置forceMaster
	 * @param join
	 */
	public void before(JoinPoint join) {
		log.debug("start before");
		forceMaster.set(Boolean.TRUE);
	}
	
	/**
	 * 释放
	 */
	public void after() {
		log.debug("start after");
		forceMaster.remove();
	}
	
	public void throwing(JoinPoint join, Exception ex) {
		log.debug("start throwing");
		log.error("execute db error. with exceptions is: ", ex);
	}
	
	/**
	 * 获取是否强制走主库
	 * @return
	 */
	public static boolean getForceMaster() {
		return BooleanUtils.toBoolean(forceMaster.get());
	}

}
