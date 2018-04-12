/**
 * 
 */
package com.hp.core.mybatis.bean;

import java.lang.reflect.Method;

import com.hp.tools.common.beans.BaseBean;

/**
 * 当前执行的dao的类属性
 * @author huangping
 * 2018年4月11日
 */
public class DAOInterfaceInfoBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4162275762456111854L;

	/**
	 * dao的类名称
	 */
	private Class<?> className;
	
	/**
	 * 执行的方法
	 */
	private Method method;

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
