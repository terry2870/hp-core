/**
 * 
 */
package com.hp.core.netty.bean;

import com.hp.core.api.bean.BaseBean;

/**
 * @author huangping
 * 2016年7月24日 上午1:32:11
 */
public class Request extends BaseBean {

	private static final long serialVersionUID = 1103241642485266988L;

	/**
	 * 请求唯一标识
	 */
	private String messageId;
	
	/**
	 * 请求的对象
	 */
	private Class<?> className;

	/**
	 * 
	 */
	public Request() {
	}

	/**
	 * @param messageId
	 * @param className
	 */
	public Request(String messageId, Class<?> className) {
		this.messageId = messageId;
		this.className = className;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}
}
