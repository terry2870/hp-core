/**
 * 
 */
package com.hp.core.database.dao;

/**
 * 实现基本的更新操作
 * 继承该接口，可以自动得到基本的更新操作
 * @author huangping
 * 2018年5月29日
 */
public interface IBaseUpdateDAO<T, PK> {

	/**
	 * 根据主键更新数据
	 * @param target
	 * @return
	 */
	public Integer updateByPrimaryKey(T target);
	
	/**
	 * 根据条件，更新字段
	 * @param t
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(T target);
}
