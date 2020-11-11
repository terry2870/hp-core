/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * @param <MODEL>	数据库对象
 * @param <PK>		数据库主键
 * 2018年5月21日
 */
public interface IBaseSelectDAO<MODEL, PK> {

	/**
	 * 查询总数
	 * @param whereList
	 * @return
	 */
	public PK selectCount(List<SQLWhere> whereList);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param sqlBuilders
	 */
	public List<MODEL> selectList(SQLBuilders sqlBuilders);
	
	/**
	 * 根据传入的builders，查询一个
	 * @param sqlBuilders
	 * @return
	 */
	public MODEL selectOne(SQLBuilders sqlBuilders);
	
	/**
	 * 查询最大的id
	 * @param where
	 * @return
	 */
	public PK selectMaxId(SQLWhere... where);
	
	/**
	 * 查询最小id
	 * @param where
	 * @return
	 */
	public PK selectMinId(SQLWhere... where);
	
	/**
	 * 根据id范围查询
	 * @param minId
	 * @param maxId
	 * @param sqlBuilders
	 * @return
	 */
	public List<MODEL> selectListByRange(PK minId, PK maxId, SQLBuilders... sqlBuilders);
	
	/**
	 * 根据条件，查询大于某一个id值的数据
	 * @param largeThanId
	 * @param sqlBuilders
	 * @return
	 */
	public List<MODEL> selectListByLargeThanId(PK largeThanId, SQLBuilders... sqlBuilders);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public MODEL selectByPrimaryKey(PK id);
	
	/**
	 * 根据主键，批量查询
	 * @param primaryKeyIdList
	 * @return
	 */
	public List<MODEL> selectByPrimaryKeys(List<PK> primaryKeyIdList);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param primaryKeyIdList
	 * @return
	 */
	public List<MODEL> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList);
	
	/**
	 * 查询列表
	 * （返回指定的一个类型字段的list）
	 * @param <T>
	 * @param sqlBuilders
	 * @param clazz
	 * @return
	 */
	public <T> List<T> selectListForTargetClass(SQLBuilders sqlBuilders, Class<T> clazz);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * （返回指定的一个类型字段的对象）
	 * @param sqlBuilders
	 * @param clazz
	 * @return
	 */
	public <T> T selectOneForTargetClass(SQLBuilders sqlBuilders, Class<T> clazz);

}
