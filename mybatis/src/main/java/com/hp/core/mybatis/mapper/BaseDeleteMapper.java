/**
 * 
 */
package com.hp.core.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.hp.core.database.dao.IBaseDeleteDAO;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.provider.BaseDeleteSQLProvider;

/**
 * 实现基本的删除操作
 * 继承该接口，可以自动得到基本的删除操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseDeleteMapper<T> extends IBaseDeleteDAO<T> {

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
	public Integer deleteByPrimaryKeys(List<?> ids);
	
	/**
	 * 根据传入的参数，删除
	 * @param target
	 * @return
	 */
	@DeleteProvider(type = BaseDeleteSQLProvider.class, method = "deleteByParams")
	public Integer deleteByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
}
