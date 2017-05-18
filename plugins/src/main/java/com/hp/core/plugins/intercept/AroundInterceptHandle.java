/**
 * 
 */
package com.hp.core.plugins.intercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环绕拦截器抽象
 * @author ping.huang
 * 2017年5月17日
 */
public interface AroundInterceptHandle {

	public static Logger log = LoggerFactory.getLogger(AroundInterceptHandle.class);
	
	/**
	 * 调用方法之前执行（如果该方法返回false，则不会执行方法体）
	 * @param target
	 * @param methodName
	 * @param args
	 * @return
	 */
	default public boolean onBefore(Object target, String methodName, Object[] args) {
		return true;
	}
	
	/**
	 * 调用方法之后执行
	 * @param target
	 * @param methodName
	 * @param args
	 * @param result
	 */
	default public void onAfter(Object target, String methodName, Object[] args, Object result) {
		// do nothing
	}
	
	/**
	 * 抛异常的时候，调用
	 * @param target
	 * @param methodName
	 * @param args
	 * @param t
	 */
	default public void onException(Object target, String methodName, Object[] args, Throwable t) {
		//do nothing
	}
	
	/**
	 * 是否忽略before判断
	 * @param target
	 * @param methodName
	 * @param args
	 * @return 返回true，则不会执行onBefore也不会执行onAfter，直接执行原方法
	 */
	default public boolean isIgnore(Object target, String methodName, Object[] args) {
		return false;
	}
	
	/**
	 * 设置返回值（当该方法返回不为null时，则直接用该返回值返回，不会执行具体方法）
	 * @param target
	 * @param methodName
	 * @param args
	 * @param returnType
	 * @return
	 */
	default public Object getReturnValue(Object target, String methodName, Object[] args, Class<?> returnType) {
		return null;
	}
	
	/**
	 * 环绕方法拦截
	 * @param join
	 * @return
	 * @throws Throwable
	 */
	default public Object around(ProceedingJoinPoint join) throws Throwable {
		Object target = join.getTarget();
		String methodName = join.getSignature().getName();
		Object[] args = join.getArgs();
		
		boolean isIgnore = isIgnore(target, methodName, args);
		
		if (!isIgnore && !onBefore(target, methodName, args)) {
			//如果before返回false，则阻止执行方法
			log.warn("around method. with isIgnore is false and before return false. with target={}, methodName={}, args={}", target, methodName, args);
			return null;
		}
		Class<?> returnType = ((MethodSignature) join.getSignature()).getReturnType();
		Object obj = getReturnValue(target, methodName, args, returnType);
		if (obj != null) {
			//有数据，则直接返回
			return obj;
		}
		try {
			//执行方法
			obj = join.proceed();
			return obj;
		} catch(Throwable t) {
			log.error("around method error. with exception. with target={}, methodName={}, args={}", target, methodName, args, t);
			onException(target, methodName, args, t);
			throw t;
		} finally {
			if (!isIgnore) {
				//只有当不是忽略的，才执行after
				onAfter(target, methodName, args, obj);
			}
			
		}
	}
	
}
