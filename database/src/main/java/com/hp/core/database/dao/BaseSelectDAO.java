/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

import com.hp.core.common.beans.page.PageModel;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * 2018年5月21日
 */
public interface BaseSelectDAO<T> {

	/**
	 *  无条件查询所有总数
	 * @return
	 */
	public Integer selectAllCount();
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id);
	
	/**
	 * 根据条件，查询数量
	 * @param target
	 * @return
	 */
	public Integer selectCountByParams(T target);
	
	/**
	 * 根据条件，查询list（不分页）
	 * @param params
	 * @return
	 */
	public List<T> selectListByParams(T target);
	
	/**
	 * 根据条件，查询单个
	 * @param params
	 * @return
	 */
	public T selectOneByParams(T target);
	
	/**
	 * 根据条件，查询list（分页）
	 * @param params
	 * @param page
	 * @return
	 */
	public List<T> selectPageListByParams(T target, PageModel page);
	
	/**
	 * 根据主键，批量查询
	 * @param ids
	 * @return
	 */
	public List<T> selectByPrimaryKeys(List<?> ids);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param ids
	 * @return
	 */
	public List<T> selectByPrimaryKeysWithInSort(List<?> ids);
}
