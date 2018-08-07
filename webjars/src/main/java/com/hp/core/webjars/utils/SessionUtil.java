/**
 * 
 */
package com.hp.core.webjars.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hp.core.webjars.interceptor.UrlInterceptor;
import com.hp.core.webjars.model.OperaBean;
import com.hp.core.webjars.model.response.SysUserResponseBO;

/**
 * @author huangping
 * 2016年8月24日 下午10:58:41
 */
public class SessionUtil {

	/**
	 * 判断当前用户是否是超级管理员
	 * @return
	 */
	public static boolean isSuperUser() {
		return getOperater().isSuperUser();
	}
	
	/**
	 * 获取当前操作者信息
	 * @return
	 */
	public static OperaBean getOperater() {
		OperaBean opera = UrlInterceptor.getOperater();
		return opera == null ? new OperaBean() : opera;
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static SysUserResponseBO getSessionUser() {
		return getOperater().getUser();
	}
	
	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取response
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
}
