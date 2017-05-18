/**
 * 
 */
package com.hp.core.netty.bean;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author huangping
 * 2016年7月24日 上午1:17:52
 */
public class NettyResponse extends BaseBean {

	private static final long serialVersionUID = -110311144131171151L;

	/**
	 * 该返回的唯一标识
	 */
	private String messageId;
	
	/**
	 * 返回值
	 */
	private Object data;
	
	private Class<?> className = Object.class;
	
	/**
	 * 抛出的异常
	 */
	private Throwable exception;
	
	/**
	 * 
	 */
	public NettyResponse() {
	}

	/**
	 * @param messageId
	 * @param data
	 * @param exception
	 */
	public NettyResponse(String messageId, Object data, Throwable exception) {
		this.messageId = messageId;
		this.data = data;
		this.exception = exception;
	}
	
	/**
	 * @param messageId
	 * @param returnValue
	 */
	public NettyResponse(String messageId, Object data) {
		this.messageId = messageId;
		this.data = data;
	}

	/**
	 * @param messageId
	 * @param data
	 * @param responseClass
	 */
	public NettyResponse(String messageId, Object data, Class<?> className) {
		super();
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

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}
}
