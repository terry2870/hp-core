/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import java.util.List;

import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.dao.IBaseSelectDAO;

/**
 * @author huangping
 * 2018年12月10日
 */
public class BaseSelectDAOImpl<MODEL, PK> implements IBaseSelectDAO<MODEL, PK> {

	@Override
	public PK selectCount(List<SQLWhere> whereList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MODEL> selectList(SQLBuilders builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MODEL selectOne(SQLBuilders builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PK selectMaxId(SQLWhere... where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PK selectMinId(SQLWhere... where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MODEL> selectListByRange(PK minId, PK maxId, SQLBuilders... builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MODEL> selectListByLargeThanId(PK largeThanId, SQLBuilders... builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MODEL selectByPrimaryKey(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MODEL> selectByPrimaryKeys(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MODEL> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> selectListForTargetClass(SQLBuilders builders, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T selectOneForTargetClass(SQLBuilders builders, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
