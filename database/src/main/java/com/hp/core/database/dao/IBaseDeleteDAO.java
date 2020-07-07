/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

import com.hp.core.database.bean.SQLWhere;

/**
 * 实现基本的删除操作
 * 继承该接口，可以自动得到基本的删除操作
 * @author huangping
 * 2018年5月29日
 */
public interface IBaseDeleteDAO<MODEL, PK> {

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public Integer deleteByPrimaryKey(PK id);
	
	/**
	 * 根据主键批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteByPrimaryKeys(List<PK> ids);
	
	/**
	 * 根据传入的参数，删除
	 * @param target
	 * @return
	 */
	public Integer deleteByParams(MODEL target);
	
	/**
	 * 根据传入的条件删除
	 * @param whereList
	 * @return
	 */
	public Integer deleteByBuilder(List<SQLWhere> whereList);
}
