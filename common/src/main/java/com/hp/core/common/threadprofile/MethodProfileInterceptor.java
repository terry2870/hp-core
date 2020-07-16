package com.hp.core.common.threadprofile;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

public class MethodProfileInterceptor implements MethodInterceptor, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		/**
		 * 被代理的方法的，类名、方法名、参数
		 */
		String className = invocation.getMethod().getDeclaringClass().getName() ;
		String method = invocation.getMethod().getName();
		/**
		 * 全局性能统计，并记录堆栈，函数调用开始
		 */
		ThreadProfile.enter(className, method);

		/**
		 * 返回结果
		 */
		Object result = null ;
		try {
			/**
			 * 执行被代理（拦截）的调用
			 */
			result = invocation.proceed();
		} catch (Exception e) {
			throw  e;
		} finally {
			/**
			 * 全局性能统计，并记录堆栈，函数调用结束
			 */
			ThreadProfile.exit();
		}
		return result;
	}

}
