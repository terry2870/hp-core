/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.dao.BaseDAO;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;
import com.hp.core.jdbc.helper.JdbcSQLBuilderHelper;
import com.hp.core.jdbc.model.JdbcSQLResponseBO;

/**
 * @author huangping
 * 2018年12月10日
 */
public interface AbstractJdbcBaseDAOImpl<MODEL, PK> extends BaseDAO<MODEL, PK> {

	/**
	 * 获取JdbcTemplate
	 * @return
	 */
	public abstract JdbcTemplate getJdbcTemplate();
	
	

	@Override
	public default Integer deleteByPrimaryKey(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer deleteByPrimaryKeys(List<PK> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer deleteByParams(MODEL target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer deleteByBuilder(List<SQLWhere> whereList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer insert(MODEL target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer insertSelective(MODEL target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer insertBatch(List<MODEL> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer updateByPrimaryKey(MODEL target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Integer updateByPrimaryKeySelective(MODEL target) {
		// TODO Auto-generated method stub
		return null;
	}

}
