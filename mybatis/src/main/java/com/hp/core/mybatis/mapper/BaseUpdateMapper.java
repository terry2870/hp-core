/**
 * 
 */
package com.hp.core.mybatis.mapper;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hp.core.database.dao.IBaseUpdateDAO;
import com.hp.core.mybatis.provider.BaseUpdateSQLProvider;

/**
 * 实现基本的更新操作
 * 继承该接口，可以自动得到基本的更新操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseUpdateMapper<T, PK> extends IBaseUpdateDAO<T, PK> {

	/**
	 * 根据主键更新数据
	 * @param target
	 * @return
	 */
	@UpdateProvider(type = BaseUpdateSQLProvider.class, method = "updateByPrimaryKey")
	public Integer updateByPrimaryKey(T target);
	
	/**
	 * 根据条件，更新字段
	 * @param t
	 * @return
	 */
	@UpdateProvider(type = BaseUpdateSQLProvider.class, method = "updateByPrimaryKeySelective")
	public Integer updateByPrimaryKeySelective(T target);
}
