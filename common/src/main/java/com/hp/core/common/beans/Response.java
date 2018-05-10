package com.hp.core.common.beans;

/**
 * service返回信息对象
 * @author hp
 * 2014-03-11
 */
public class Response<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266480114572636927L;
	private int code = 200;
	private String message = "success";
	
	private T data;
	
	public Response() {}
	
	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Response(T data) {
		this.data = data;
	}
	
	public Response(int code, String message, T data) {
		this(code, message);
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}

