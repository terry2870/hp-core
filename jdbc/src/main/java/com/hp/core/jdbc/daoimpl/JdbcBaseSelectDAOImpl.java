/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.dao.IBaseSelectDAO;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;
import com.hp.core.jdbc.helper.JdbcSQLBuilderHelper;
import com.hp.core.jdbc.model.JdbcSQLResponseBO;

/**
 * @author huangping
 * Jul 14, 2020
 */
public interface JdbcBaseSelectDAOImpl<MODEL, PK> extends IBaseSelectDAO<MODEL, PK>, JdbcTemplateService {
	
	@Override
	public default PK selectCount(List<SQLWhere> whereList) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQLBuilders builders = SQLBuilders.create()
				.withWhere(whereList)
				.withSelect("COUNT(1)")
				;
		JdbcSQLResponseBO sql = JdbcSQLBuilderHelper.getSQL(builders, entity);
		PK total =  (PK) getJdbcTemplate().queryForObject(sql.getSql(), entity.getPrimaryKeyJavaType(), sql.getParamsArray());
		return total;
	}

	@Override
	public default List<MODEL> selectList(SQLBuilders builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default MODEL selectOne(SQLBuilders builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default PK selectMaxId(SQLWhere... where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default PK selectMinId(SQLWhere... where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default List<MODEL> selectListByRange(PK minId, PK maxId, SQLBuilders... builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default List<MODEL> selectListByLargeThanId(PK largeThanId, SQLBuilders... builders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default MODEL selectByPrimaryKey(PK id) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQLBuilders builders = SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq(entity.getPrimaryKeyColumnName(), id)
						.build()
						)
				;
		JdbcSQLResponseBO sql = JdbcSQLBuilderHelper.getSQL(builders, entity);
		List<MODEL> list =  (List<MODEL>) getJdbcTemplate().query(sql.getSql(), sql.getParamsArray(), BeanPropertyRowMapper.newInstance(entity.getClassName()));
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public default List<MODEL> selectByPrimaryKeys(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default List<MODEL> selectByPrimaryKeysWithInSort(List<PK> primaryKeyIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default <T> List<T> selectListForTargetClass(SQLBuilders builders, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default <T> T selectOneForTargetClass(SQLBuilders builders, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
}
