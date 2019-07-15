/**
 * 
 */
package com.hp.core.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.dao.IBaseSelectDAO;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * 2018年5月21日
 */
public interface BaseSelectMapper<T> extends IBaseSelectDAO<T> {

	/**
	 * 查询最大的id
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMaxId")
	public Long selectMaxId(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 查询最小id
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMinId")
	public Long selectMinId(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 根据id范围查询
	 * @param minId
	 * @param maxId
	 * @param target
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByRange")
	public List<T> selectListByRange(@Param("minId") long minId, @Param("maxId") long maxId, @Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
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
	public default List<T> selectListByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target) {
		return selectListByParamsWithOrder(target);
	}
	
	/**
	 * 根据条件，查询list（不分页）
	 * @param target
	 * @param orderBy
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByParams")
	public List<T> selectListByParamsWithOrder(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target, @Param(SQLProviderConstant.ORDER_BY) OrderBy... orderBy);
	
	/**
	 * 自定义sql，查询列表
	 * @param sql
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListBySQL")
	public List<T> selectList(@Param(SQLProviderConstant.SQL_OBJECT_ALIAS) SQL sql);
	
	/**
	 * 根据条件，查询单个
	 * @param params
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOneByParams")
	public T selectOneByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target);
	
	/**
	 * 根据条件，查询单个（带排序）
	 * @param target
	 * @param orderBy
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOneByParams")
	public T selectOneByParamsWithOrder(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target, @Param(SQLProviderConstant.ORDER_BY) OrderBy... orderBy);
	
	/**
	 * 根据条件，查询list（分页）
	 * @param params
	 * @param page
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByParams")
	public List<T> selectPageListByParams(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page);
	
	/**
	 * 根据条件，查询list（分页）
	 * @param target
	 * @param page
	 * @param largeThanId
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectPageListByParamsAndLargeThanId")
	public List<T> selectPageListByParamsAndLargeThanId(@Param(SQLProviderConstant.TARGET_OBJECT_ALIAS) T target, @Param(SQLProviderConstant.PAGE_OBJECT_ALIAS) PageModel page, @Param(SQLProviderConstant.LARGETHAN_ID_OBJECT_ALIAS) Long largeThanId);
	
	/**
	 * 根据主键，批量查询
	 * @param ids
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeys")
	public List<T> selectByPrimaryKeys(List<?> ids);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param ids
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeysWithInSort")
	public List<T> selectByPrimaryKeysWithInSort(List<?> ids);
}
