/**
 * 
 */
package com.hp.core.netty.bean;

import java.util.UUID;

import com.hp.core.common.beans.BaseBean;


/**
 * @author huangping
 * 2016年7月24日 上午1:32:11
 */
public class NettyRequest extends BaseBean {

	private static final long serialVersionUID = 1103241642485266988L;

	/**
	 * 请求唯一标识
	 */
	private String messageId = UUID.randomUUID().toString();
	
	/**
	 * 请求的对象
	 */
	private Class<?> className;
	
	private Object data;

	/**
	 * 
	 */
	public NettyRequest() {
	}

	public NettyRequest(Object data, Class<?> className) {
		this.data = data;
		this.className = className;
	}
	
	public NettyRequest(String messageId, Object data, Class<?> className) {
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

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
