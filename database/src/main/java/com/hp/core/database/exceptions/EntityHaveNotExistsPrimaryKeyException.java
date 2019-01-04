/**
 * 
 */
package com.hp.core.database.exceptions;
/**
 * 实体对象没有主键异常
 * @author huangping
 * 2018年5月28日
 */
public class EntityHaveNotExistsPrimaryKeyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4290261834574248684L;

	
	public EntityHaveNotExistsPrimaryKeyException(Class<?> clazz) {
		super("entity have not exists Primary key. with class=" + clazz.getName());
	}
}
