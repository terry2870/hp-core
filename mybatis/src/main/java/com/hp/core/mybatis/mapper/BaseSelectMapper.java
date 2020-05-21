/**
 * 
 */
package com.hp.core.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilder;
import com.hp.core.database.dao.IBaseSelectDAO;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * 2018年5月21日
 */
public interface BaseSelectMapper<T, PK> extends IBaseSelectDAO<T, PK> {

	/**
	 * 查询最大的id
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMaxId")
	public PK selectMaxId(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 查询最小id
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMinId")
	public PK selectMinId(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 根据id范围查询
	 * @param minId
	 * @param maxId
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByRange")
	public List<T> selectListByRange(@Param("minId") PK minId, @Param("maxId") PK maxId, @Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 查询总数
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectCount")
	public PK selectCount(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKey")
	public T selectByPrimaryKey(@Param(SQLProviderConstant.KEY_PROPERTY_ID) PK id);
	
	/**
	 * 根据主键，批量查询
	 * @param primaryKeyIdList
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeys")
	public List<T> selectByPrimaryKeys(List<PK> primaryKeyIdList);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param primaryKeyIdList
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeysWithInSort")
	public List<T> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param builder
	 * @param orderBy
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectList")
	public List<T> selectList(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder[] builder, @Param(SQLProviderConstant.ORDER_BY) OrderBy... orderBy);
	
	/**
	 * 根据传入的sqlbuild，查询（分页）
	 * @param builder
	 * @param page
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectList")
	public List<T> selectPageList(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder[] builder, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * @param builder
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOne")
	public T selectOne(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 查询大于该id的数据
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectPageListLargeThanId")
	public List<T> selectPageListLargeThanId(@Param(SQLProviderConstant.LARGETHAN_ID_OBJECT_ALIAS) PK largeThanId, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page, @Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder... builder);
	
	/**
	 * 查询主键列表
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectPrimaryKeyList")
	public List<PK> selectPrimaryKeyList(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder[] builder, @Param(SQLProviderConstant.ORDER_BY) OrderBy... orderBy);
	
	/**
	 * 查询主键列表(分页)
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectPrimaryKeyList")
	public List<PK> selectPrimaryKeyPageList(@Param(SQLProviderConstant.SQL_BUILD_ALIAS) SQLBuilder[] builder, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page);
}
