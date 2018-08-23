/**
 * 
 */
package com.hp.core.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * 2018年5月21日
 */
public interface BaseSelectMapper<T> {

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
	public T selectByPrimaryKey(Object id);
	
	/**
	 * 根据条件，查询数量
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectCountByParams")
	public Integer selectCountByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 根据条件，查询list（不分页）
	 * @param params
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByParams")
	public List<T> selectListByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 根据条件，查询单个
	 * @param params
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOneByParams")
	public T selectOneByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 根据条件，查询list（分页）
	 * @param params
	 * @param page
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByParams")
	public List<T> selectPageListByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page);
	
	/**
	 * 根据主键，批量查询
	 * @param ids
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeys")
	public List<T> selectByPrimaryKeys(Object... ids);
}
