/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

import com.hp.core.database.bean.SQLBuilder;

/**
 * 实现基本的删除操作
 * 继承该接口，可以自动得到基本的删除操作
 * @author huangping
 * 2018年5月29日
 */
public interface IBaseDeleteDAO<T, PK> {

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public Integer deleteByPrimaryKey(PK id);
	
	/**
	 * 根据主键批量删除
	 * @param idList
	 * @return
	 */
	public Integer deleteByPrimaryKeys(List<PK> idList);
	
	/**
	 * 根据传入的参数，删除
	 * @param target
	 * @return
	 */
	public Integer deleteByParams(T target);
	
	/**
	 * 根据传入的参数，删除
	 * @param builder
	 * @return
	 */
	public Integer deleteByBuilder(SQLBuilder... builder);
}
