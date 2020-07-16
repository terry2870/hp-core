/**
 * 
 */
package com.hp.core.database.interceptor;

import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 强制到主库的拦截器
 * @author huangping
 * Jan 17, 2020
 */
@Aspect
@Component
public class ForceMasterInterceptor {

	private static Logger log = LoggerFactory.getLogger(ForceMasterInterceptor.class);
	
	private static final ThreadLocal<Boolean> forceMaster = new InheritableThreadLocal<Boolean>();
	
	/**
	 * 设置强制走主库
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.hp.core.database.annotation.ForceMaster) or @annotation(org.springframework.transaction.annotation.Transactional)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("start before");
		forceMaster.set(Boolean.TRUE);
		Object obj = null;
		try {
			obj = joinPoint.proceed();
			return obj;
		} catch (Exception e) {
			throw e;
		} finally {
			log.debug("start after");
			forceMaster.remove();
		}
	}
	
	/**
	 * 获取是否强制走主库
	 * @return
	 */
	public static boolean getForceMaster() {
		return BooleanUtils.toBoolean(forceMaster.get());
	}

}
