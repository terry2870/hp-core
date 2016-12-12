/**
 * 
 */
package com.hp.core.zookeeper.bean;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年12月11日
 */
public class ZKConfig extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7777736346089104220L;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * zk地址
	 */
	private String connectString;
	
	/**
	 * 服务注册到zk的根节点
	 */
	private String basePath;
	
	/**
	 * session超时时间
	 */
	private int sessionTimeoutMs;
	
	/**
	 * 连接超时时间
	 */
	private int connectionTimeoutMs;
	
	/**
	 * 重试次数
	 */
	private int retryTimes;
	
	/**
	 * 重试休眠时间
	 */
	private int retrySleepTimeMs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getRetrySleepTimeMs() {
		return retrySleepTimeMs;
	}

	public void setRetrySleepTimeMs(int retrySleepTimeMs) {
		this.retrySleepTimeMs = retrySleepTimeMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
