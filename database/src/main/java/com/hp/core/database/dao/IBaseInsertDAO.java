/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

/**
 * 实现基本的新增操作
 * 继承该接口，可以自动得到基本的新增操作
 * @author huangping
 * 2018年5月29日
 */
public interface IBaseInsertDAO<MODEL, PK> {

	/**
	 * 新增数据
	 * 插入成功后，参数对象中的主键自动赋值成功
	 * @param target
	 * @return
	 */
	public Integer insert(MODEL target);
	
	/**
	 * 根据条件，新增字段
	 * 插入成功后，参数对象中的主键自动赋值成功
	 * @param target
	 * @return
	 */
	public Integer insertSelective(MODEL target);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public Integer insertBatch(List<MODEL> list);
}
