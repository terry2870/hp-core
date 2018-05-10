/**
 * 
 */
package com.hp.core.zookeeper.bean;

import com.hp.core.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年12月11日
 */
public class RegisterInstanceDetail extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4921742441553993303L;

	/**
	 * 监听端口
	 */
	private int linstenPort;
	
	/**
	 * 监听的地址
	 */
	private String linstenAddress;
	
	/**
	 * web在tomcat中的Context
	 */
	private String context;
	
	/**
	 * 服务类型
	 */
	private String serviceType;
	
	/**
	 * spring的bean 名称
	 */
	private String beanName;
	/**
	 * 服务的类名称
	 */
	private String className;
	
	/**
	 * 方法名称
	 */
	private String methodName;
	
	/**
	 * controller类上mapping的url
	 */
	private String controllerRequestMapping;
	
	/**
	 * controller方法上mapping的URL
	 */
	private String methodRequestMapping;
	
	/**
	 * 服务的名称
	 */
	private String serviceName;

	public String getLinstenAddress() {
		return linstenAddress;
	}

	public void setLinstenAddress(String linstenAddress) {
		this.linstenAddress = linstenAddress;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getControllerRequestMapping() {
		return controllerRequestMapping;
	}

	public void setControllerRequestMapping(String controllerRequestMapping) {
		this.controllerRequestMapping = controllerRequestMapping;
	}

	public String getMethodRequestMapping() {
		return methodRequestMapping;
	}

	public void setMethodRequestMapping(String methodRequestMapping) {
		this.methodRequestMapping = methodRequestMapping;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getLinstenPort() {
		return linstenPort;
	}

	public void setLinstenPort(int linstenPort) {
		this.linstenPort = linstenPort;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
}
