/**
 * 
 */
package com.hp.core.plugins.exceptions;

/**
 * 获取许可超时异常
 * @author ping.huang
 * 2017年5月12日
 */
public class TryAcquireErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5372395049359036990L;

	public TryAcquireErrorException() {
		super();
	}
	
	public TryAcquireErrorException(String message) {
		super(message);
	}
}
