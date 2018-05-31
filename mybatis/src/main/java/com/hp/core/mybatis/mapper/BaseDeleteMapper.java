/**
 * 
 */
package com.hp.core.mybatis.mapper;

import org.apache.ibatis.annotations.DeleteProvider;

import com.hp.core.mybatis.provider.BaseDeleteSQLProvider;

/**
 * 实现基本的删除操作
 * 继承该接口，可以自动得到基本的删除操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseDeleteMapper<T> {

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@DeleteProvider(type = BaseDeleteSQLProvider.class, method = "deleteByPrimaryKey")
	public Integer deleteByPrimaryKey(Object id);
	
	/**
	 * 根据主键批量删除
	 * @param ids
	 * @return
	 */
	@DeleteProvider(type = BaseDeleteSQLProvider.class, method = "deleteByPrimaryKeys")
	public Integer deleteByPrimaryKeys(Object... ids);
}
