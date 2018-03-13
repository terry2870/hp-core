/**
 * 
 */
package com.hp.core.mybatis.mapper;
/**
 * @author huangping
 * 2018年1月26日
 */
public interface BaseMapper<T> {

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id);
}
