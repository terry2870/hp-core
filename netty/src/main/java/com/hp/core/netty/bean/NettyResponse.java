/**
 * 
 */
package com.hp.core.netty.bean;

import com.hp.core.api.bean.BaseBean;

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
	private Object returnValue;
	
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
	 * @param returnValue
	 * @param exception
	 */
	public NettyResponse(String messageId, Object returnValue, Throwable exception) {
		this.messageId = messageId;
		this.returnValue = returnValue;
		this.exception = exception;
	}
	
	/**
	 * @param messageId
	 * @param returnValue
	 */
	public NettyResponse(String messageId, Object returnValue) {
		this.messageId = messageId;
		this.returnValue = returnValue;
	}
	

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
