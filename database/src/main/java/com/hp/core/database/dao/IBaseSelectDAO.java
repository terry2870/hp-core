/**
 * 
 */
package com.hp.core.database.dao;

import java.util.List;

import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilder;

/**
 * 基本的查询操作
 * 继承该接口，可以实现一些简单的查询操作
 * @author huangping
 * 2018年5月21日
 */
public interface IBaseSelectDAO<T, PK> {

	/**
	 * 查询最大的id
	 * @param target
	 * @return
	 */
	public PK selectMaxId(SQLBuilder... builder);
	
	/**
	 * 查询最小id
	 * @param target
	 * @return
	 */
	public PK selectMinId(SQLBuilder... builder);
	
	/**
	 * 根据id范围查询
	 * @param minId
	 * @param maxId
	 * @param target
	 * @return
	 */
	public List<T> selectListByRange(PK minId, PK maxId, SQLBuilder... builder);
	
	
	/**
	 * 查询总数
	 * @param builder
	 * @return
	 */
	public PK selectCount(SQLBuilder... builder);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(PK id);
	
	/**
	 * 根据主键，批量查询
	 * @param primaryKeyIdList
	 * @return
	 */
	public List<T> selectByPrimaryKeys(List<PK> primaryKeyIdList);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param primaryKeyIdList
	 * @return
	 */
	public List<T> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param builder
	 * @param orderBy
	 * @return
	 */
	public List<T> selectList(SQLBuilder[] builder, OrderBy... orderBy);
	
	/**
	 * 根据传入的sqlbuild，查询（分页）
	 * @param builder
	 * @param page
	 * @return
	 */
	public List<T> selectPageList(SQLBuilder[] builder, PageModel page);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * @param builder
	 * @return
	 */
	public T selectOne(SQLBuilder... builder);
	
	/**
	 * 查询大于该id的数据
	 * @param largeThanId
	 * @param page
	 * @param builder
	 * @return
	 */
	public List<T> selectPageListLargeThanId(PK largeThanId, PageModel page, SQLBuilder... builder);
	
	/**
	 * 查询主键列表
	 * @param builder
	 * @param orderBy
	 * @return
	 */
	public List<PK> selectPrimaryKeyList(SQLBuilder[] builder, OrderBy... orderBy);
	
	/**
	 * 查询主键列表
	 * @param builder
	 * @param page
	 * @return
	 */
	public List<PK> selectPrimaryKeyPageList(SQLBuilder[] builder, PageModel page);

}
