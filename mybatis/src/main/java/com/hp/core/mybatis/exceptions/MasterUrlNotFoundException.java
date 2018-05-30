/**
 * 
 */
package com.hp.core.mybatis.exceptions;
/**
 * @author huangping
 * 2018年4月2日
 */
public class MasterUrlNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -260865143507268741L;

	public MasterUrlNotFoundException() {
		super("master url is not find from bean");
	}
}
