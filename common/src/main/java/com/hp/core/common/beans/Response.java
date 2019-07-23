package com.hp.core.common.beans;

import com.hp.core.common.enums.CodeEnum;

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
	private static final int SUCCESS_CODE = CodeEnum.SUCCESS.getCode();
	private static final String SUCCESS_MESSAGE = "success";
	
	
	private int code = SUCCESS_CODE;
	private String message = SUCCESS_MESSAGE;
	
	private T data;
	
	
	public static <T> Response<T> success(T data) {
		return success(SUCCESS_MESSAGE, data);
	}
	
	public static <T> Response<T> success(String message) {
		return success(message, null);
	}
	
	public static <T> Response<T> success(String message, T data) {
		return new Response<>(SUCCESS_CODE, message, data);
	}
	
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
	
	public Response(CodeEnum code, String message, T data) {
		this(code.getCode(), message, data);
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

