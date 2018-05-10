/**
 * 
 */
package com.hp.core.common.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 描述：
 * 
 * @author ping.huang
 * 2016年5月6日
 */
public class ObjectUtil {

	
	/**
	 * 执行方法
	 * 
	 * @param classObj
	 * @param method
	 * @param classArr
	 * @param paramArr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T executeJavaMethod(Object classObj, String method, Class<?>[] classArr, Object[] paramArr) throws Exception {
		if (classObj == null || StringUtils.isEmpty(method)) {
			return null;
		}
		T result = null;
		if (classObj instanceof Class) {
			Class<?> clazz = (Class<?>) classObj;
			result = (T) clazz.getMethod(method, classArr).invoke(classObj, paramArr);
		} else {
			result = (T) classObj.getClass().getMethod(method, classArr).invoke(classObj, paramArr);
		}
		return result;
	}
	
	/**
	 * 从对象中取属性
	 * 
	 * @param obj
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Object getValueFromObject(Object obj, String name) throws Exception {
		return getValueFromObject(obj, name, null);
	}

	/**
	 * 从对象中取属性
	 * 
	 * @param obj
	 * @param name
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static Object getValueFromObject(Object obj, String name, Object defaultValue) throws Exception {
		if (obj == null || StringUtils.isEmpty(name)) {
			return defaultValue;
		}
		Object result = null;
		String[] arr = name.split("[.]");
		result = getProperty(obj, arr[0], defaultValue);
		for (int i = 1; i < arr.length; i++) {
			if (result == null) {
				return defaultValue;
			}
			if (result instanceof String) {
				return result.toString();
			} else {
				result = getProperty(result, arr[i], defaultValue);
			}
		}
		return result;
	}
	
	/**
	 * 从对象中取值，相当于beanUtils.getProperty
	 * 
	 * @param obj
	 * @param name
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(Object obj, String name, Object defaultValue) throws Exception {
		Object result = null;
		if (obj == null) {
			result = null;
		} else if (obj instanceof ServletRequest) {
			result = getValueFromRequest((ServletRequest) obj, name, defaultValue);
		} else {
			result = BeanUtils.getProperty(obj, name);
		}
		if (result == null) {
			result = defaultValue;
		}
		return result;
	}
	
	/**
	 * 从request中取值，先getParameter再getAttribute最后取request.getSession().
	 * getAttribute
	 * 
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static Object getValueFromRequest(ServletRequest request, String param, Object defaultValue) {
		if (request == null || param == null) {
			return defaultValue;
		}
		Object obj = request.getAttribute(param);
		if (obj == null) {
			obj = request.getParameter(param);
		}
		if (obj == null) {
			HttpServletRequest req = (HttpServletRequest) request;
			obj = req.getSession().getAttribute(param);
		}
		if (obj == null) {
			obj = defaultValue;
		}
		return obj;
	}
}
