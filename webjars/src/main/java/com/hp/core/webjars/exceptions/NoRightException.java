/**
 * 
 */
package com.hp.core.webjars.exceptions;

/**
 * @author huangping
 * 2016年9月14日 上午1:01:22
 */
public class NoRightException extends Exception {

	private static final long serialVersionUID = -3275824588595638150L;

	private int code;
	private String message;
	
	
	
	/**
	 * 
	 */
	public NoRightException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public NoRightException(String message) {
		super(message);
	}
	
	/**
	 * @param code
	 * @param message
	 */
	public NoRightException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
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
}
