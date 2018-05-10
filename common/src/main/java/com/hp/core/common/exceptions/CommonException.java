package com.hp.core.common.exceptions;

/**
 * client_secutity 不匹配
 *
 * Created by chzhang@yoho.cn on 2015/11/5.
 */
public class CommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7839810860650514527L;
	private int code;
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 异常
	 *
	 */
	public CommonException(int code, String message) {
		super(message);
		this.setCode(code);
		this.setMessage(message);
	}

}
