/**
 * 
 */
package com.hp.core.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.dao.IBaseSelectDAO;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * @param <MODEL>	数据库对象
 * @param <PK>		数据库主键
 * 2018年5月21日
 */
public interface BaseSelectMapper<MODEL, PK> extends IBaseSelectDAO<MODEL, PK> {

	/**
	 * 根据传入的sql，查询list
	 * @param sql
	 * @param param
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectBySQL")
	public List<MODEL> selectListBySQL(@Param(SQLProviderConstant.SQL_ALIAS) SQL sql, @Param(SQLProviderConstant.PARAM_OBJECT_ALIAS) Object param);
	
	/**
	 * 根据传入的sql，查询一个
	 * @param sql
	 * @param param
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectBySQL")
	public MODEL selectOneBySQL(@Param(SQLProviderConstant.SQL_ALIAS) SQL sql, @Param(SQLProviderConstant.PARAM_OBJECT_ALIAS) Object param);
	
	/**
	 * 根据传入的sql，查询指定的对象
	 * @param <T>
	 * @param sql
	 * @param param
	 * @param clazz
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectBySQL")
	public <T> T selectAnyBySQL(@Param(SQLProviderConstant.SQL_ALIAS) SQL sql, @Param(SQLProviderConstant.PARAM_OBJECT_ALIAS) Object param, Class<T> clazz);
	
	/**
	 * 查询总数
	 * @param whereList
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectCount")
	public PK selectCount(@Param(SQLProviderConstant.SQL_WHERE_ALIAS) List<SQLWhere> whereList);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param sqlBuilders
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectList")
	public List<MODEL> selectList(@Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders sqlBuilders);
	
	/**
	 * 根据传入的builders，查询一个
	 * @param sqlBuilders
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOne")
	public MODEL selectOne(@Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders sqlBuilders);
	
	/**
	 * 查询最大的id
	 * @param where
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMaxId")
	public PK selectMaxId(@Param(SQLProviderConstant.SQL_WHERE_ALIAS) SQLWhere... where);
	
	/**
	 * 查询最小id
	 * @param where
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectMinId")
	public PK selectMinId(@Param(SQLProviderConstant.SQL_WHERE_ALIAS) SQLWhere... where);
	
	/**
	 * 根据id范围查询
	 * @param minId
	 * @param maxId
	 * @param sqlBuilders
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByRange")
	public List<MODEL> selectListByRange(@Param(SQLProviderConstant.MIN_ID_ALIAS) PK minId, @Param(SQLProviderConstant.MAX_ID_ALIAS) PK maxId, @Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders... sqlBuilders);
	
	/**
	 * 根据条件，查询大于某一个id值的数据
	 * @param largeThanId
	 * @param sqlBuilders
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectListByLargeThanId")
	public List<MODEL> selectListByLargeThanId(@Param(SQLProviderConstant.LARGETHAN_ID_OBJECT_ALIAS) PK largeThanId, @Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders... sqlBuilders);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKey")
	public MODEL selectByPrimaryKey(@Param(SQLProviderConstant.KEY_PROPERTY_ID) PK id);
	
	/**
	 * 根据主键，批量查询
	 * @param primaryKeyIdList
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeys")
	public List<MODEL> selectByPrimaryKeys(List<PK> primaryKeyIdList);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param primaryKeyIdList
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKeysWithInSort")
	public List<MODEL> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList);
	
	/**
	 * 查询列表
	 * （返回指定的一个类型字段的list）
	 * @param <T>
	 * @param sqlBuilders
	 * @param clazz
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectList")
	public <T> List<T> selectListForTargetClass(@Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders sqlBuilders, Class<T> clazz);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * （返回指定的一个类型字段的对象）
	 * @param sqlBuilders
	 * @param clazz
	 * @return
	 */
	@SelectProvider(type = BaseSelectProvider.class, method = "selectOne")
	public <T> T selectOneForTargetClass(@Param(SQLProviderConstant.SQL_BUILDS_ALIAS) SQLBuilders sqlBuilders, Class<T> clazz);
}
