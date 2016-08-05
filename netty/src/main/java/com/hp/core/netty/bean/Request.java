/**
 * 
 */
package com.hp.core.netty.bean;

import com.hp.core.api.bean.BaseBean;

/**
 * @author huangping
 * 2016年7月24日 上午1:32:11
 */
public class Request<T> extends BaseBean {

	private static final long serialVersionUID = 1103241642485266988L;

	/**
	 * 请求唯一标识
	 */
	private String messageId;
	
	/**
	 * 请求的对象
	 */
	private Class<T> className;
	
	private T data;

	/**
	 * 
	 */
	public Request() {
	}

	
	public Request(String messageId, T data, Class<T> className) {
		this.messageId = messageId;
		this.data = data;
		this.className = className;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Class<T> getClassName() {
		return className;
	}

	public void setClassName(Class<T> className) {
		this.className = className;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
