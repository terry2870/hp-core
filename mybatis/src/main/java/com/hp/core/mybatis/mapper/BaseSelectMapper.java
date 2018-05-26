/**
 * 
 */
package com.hp.core.mybatis.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * @author huangping
 * 2018年5月21日
 */
public interface BaseSelectMapper<T, PK> {

	/**
	 *  无条件查询所有总数
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectAllCount")
	public Integer selectAllCount();
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKey")
	public T selectByPrimaryKey(PK id);
	
	/**
	 * 根据条件，查询数量
	 * @param params
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectCountByParams")
	public Integer selectCountByParams(T params);
}
