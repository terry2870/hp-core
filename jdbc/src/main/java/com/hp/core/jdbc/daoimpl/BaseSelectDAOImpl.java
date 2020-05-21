/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import java.util.List;

import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilder;
import com.hp.core.database.dao.IBaseSelectDAO;

/**
 * @author huangping
 * 2018年12月10日
 */
public class BaseSelectDAOImpl<T, PK> implements IBaseSelectDAO<T, PK> {

	@Override
	public PK selectMaxId(SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PK selectMinId(SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectListByRange(PK minId, PK maxId, SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PK selectCount(SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T selectByPrimaryKey(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectByPrimaryKeys(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectList(SQLBuilder[] builder, OrderBy... orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectPageList(SQLBuilder[] builder, PageModel page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T selectOne(SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectPageListLargeThanId(PK largeThanId, PageModel page, SQLBuilder... builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PK> selectPrimaryKeyList(SQLBuilder[] builder, OrderBy... orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PK> selectPrimaryKeyPageList(SQLBuilder[] builder, PageModel page) {
		// TODO Auto-generated method stub
		return null;
	}}
