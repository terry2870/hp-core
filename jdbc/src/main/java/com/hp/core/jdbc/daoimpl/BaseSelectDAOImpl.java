/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.dao.IBaseSelectDAO;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;

/**
 * @author huangping
 * 2018年12月10日
 */
public abstract class BaseSelectDAOImpl<T> implements IBaseSelectDAO<T> {

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectAllCount()
	 */
	@Override
	public Integer selectAllCount() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectByPrimaryKey(java.lang.Object)
	 */
	@Override
	public T selectByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectCountByParams(java.lang.Object)
	 */
	@Override
	public Integer selectCountByParams(T target) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectListByParamsWithOrder(java.lang.Object, com.hp.core.database.bean.OrderBy[])
	 */
	@Override
	public List<T> selectListByParamsWithOrder(T target, OrderBy... orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectOneByParams(java.lang.Object)
	 */
	@Override
	public T selectOneByParams(T target) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectPageListByParams(java.lang.Object, com.hp.core.common.beans.page.PageModel)
	 */
	@Override
	public List<T> selectPageListByParams(T target, PageModel page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectByPrimaryKeys(java.util.List)
	 */
	@Override
	public List<T> selectByPrimaryKeys(List<?> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hp.core.database.dao.IBaseSelectDAO#selectByPrimaryKeysWithInSort(java.util.List)
	 */
	@Override
	public List<T> selectByPrimaryKeysWithInSort(List<?> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
