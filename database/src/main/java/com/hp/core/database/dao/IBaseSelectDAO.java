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
public interface IBaseSelectDAO<T> {

	/**
	 *  无条件查询所有总数
	 * @return
	 */
	public Integer selectAllCount();
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id);
	
	/**
	 * 根据条件，查询数量
	 * @param target
	 * @return
	 */
	public Integer selectCountByParams(T target);
	
	/**
	 * 根据条件，查询list（不分页）
	 * @param params
	 * @return
	 */
	public default List<T> selectListByParams(T target) {
		return selectListByParamsWithOrder(target);
	}
	
	/**
	 * 根据条件查询，带排序
	 * @param target
	 * @param orderBy
	 * @return
	 */
	public List<T> selectListByParamsWithOrder(T target, OrderBy... orderBy);
	
	/**
	 * 根据条件，查询单个
	 * @param params
	 * @return
	 */
	public T selectOneByParams(T target);
	
	/**
	 * 根据条件，查询list（分页）
	 * @param params
	 * @param page
	 * @return
	 */
	public List<T> selectPageListByParams(T target, PageModel page);
	
	/**
	 * 根据主键，批量查询
	 * @param ids
	 * @return
	 */
	public List<T> selectByPrimaryKeys(List<?> ids);
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param ids
	 * @return
	 */
	public List<T> selectByPrimaryKeysWithInSort(List<?> ids);
	
	/**
	 * 根据传入的sqlbuild，查询数量
	 * @param builder
	 * @return
	 */
	public Integer selectCountByBuilder(SQLBuilder... builder);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param build
	 * @return
	 */
	public List<T> selectListByBuilder(SQLBuilder... builder);
	
	/**
	 * 根据传入的sqlbuild，查询
	 * @param build
	 * @return
	 */
	public List<T> selectListByBuilderWithOrder(SQLBuilder[] builder, OrderBy... orderBy);
	
	/**
	 * 根据传入的sqlbuild，查询分页数据
	 * @param builder
	 * @param page
	 * @return
	 */
	public List<T> selectPageListByBuilder(SQLBuilder[] builder, PageModel page);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * @param builder
	 * @return
	 */
	public T selectOneByBuilder(SQLBuilder... builder);
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * @param builder
	 * @param orderBy
	 * @return
	 */
	public T selectOneByBuilderWithOrder(SQLBuilder[] builder, OrderBy... orderBy);
}
