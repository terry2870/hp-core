/**
 * 
 */
package com.hp.core.mybatis.interceptor;

import org.aopalliance.intercept.Joinpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangping 2018年4月11日
 */
public class DAOMethodInterceptorHandle {

	private static Logger log = LoggerFactory.getLogger(DAOMethodInterceptorHandle.class);
	
	public void before(Joinpoint point) {
		log.info("start before");
	}

	public void after(Joinpoint point) {
		log.info("start after");
	}
}
