/**
 * 
 */
package com.hp.core.mybatis.exceptions;
/**
 * @author huangping
 * 2018年5月14日
 */
public class DataSourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3488699974375454334L;

	public DataSourceNotFoundException(String message) {
		super("datasource not found with databaseName= " + message);
	}
}
