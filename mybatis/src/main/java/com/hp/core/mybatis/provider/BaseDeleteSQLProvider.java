/**
 * 
 */
package com.hp.core.mybatis.provider;
/**
 * 基本的删除操作
 * 获取基本的删除操作的sql
 * @author huangping
 * 2018年5月29日
 */

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DynamicColumnBean;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.exceptions.ProviderSQLException;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;
import com.hp.core.mybatis.constant.SQLProviderConstant;

public class BaseDeleteSQLProvider {

	private static Logger log = LoggerFactory.getLogger(BaseDeleteSQLProvider.class);
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @return
	 */
	public static String deleteByPrimaryKey(Object id) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQL sql = new SQL();
		sql.DELETE_FROM(entity.getTableName())
			.WHERE(entity.getPrimaryKeyColumnName() + " = #{id}");
		log.debug("deleteByPrimaryKey get sql \r\nsql={} \r\nid={} \r\nentity={}", sql, id, entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键批量删除数据
	 * @param ids
	 * @return
	 */
	public static String deleteByPrimaryKeys(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		List<?> list = (List<?>) params.get("list");
		StringBuilder sql = new StringBuilder("DELETE FROM ").append(entity.getTableName());
		sql.append("\n");
		sql.append(" WHERE ").append(entity.getPrimaryKeyColumnName()).append(" IN (");
		for (int i = 0; i < list.size(); i++) {
			sql.append("#{list[").append(i).append("]}");
			if (i != list.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(")");
		log.debug("deleteByPrimaryKey get sql \r\nsql={} \r\narr={} \r\nentity={}", sql, list.size(), entity);
		return sql.toString();
	}
	
	/**
	 * 根据传入的参数，删除
	 * @param params
	 * @return
	 */
	public static String deleteByParams(Map<String, Object> params) {
		if (params == null) {
			log.error("deleteByParams error. with params is null.");
			throw new ProviderSQLException("params is null");
		}
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder("DELETE ")
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		
		StringBuilder where = getDeleteSQLByParams(params.get(SQLProviderConstant.PARAM_OBJECT_ALIAS), entity);
		if (where == null || where.length() == 0) {
			log.error("deleteByParams error. with where sql is empty. not allow. with \r\nparams={}, \r\nentity={}", params, entity);
			throw new ProviderSQLException("deleteByParams error. with where sql is empty");
		}
		
		log.debug("deleteByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, params, entity);
		return sql.append(where.toString()).toString();
	}
	
	/**
	 * 根据build，删除
	 * @param params
	 * @return
	 */
	public static String deleteByBuilder(Map<String, Object> target) {
		if (MapUtils.isEmpty(target)) {
			log.error("deleteByBuilder error. with params is null.");
			throw new ProviderSQLException("params is null");
		}
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder("DELETE ")
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1 ");
		
		@SuppressWarnings("unchecked")
		List<SQLWhere> whereList = (List<SQLWhere>) target.get(SQLProviderConstant.SQL_WHERE_ALIAS);
		
		SQLBuilders builders = SQLBuilders.create()
				.withSqlWherePrefix(SQLProviderConstant.SQL_WHERE_ALIAS)
				.withWhere(whereList);
		
		String where = MybatisSQLBuilderHelper.getSQLBySQLBuild(builders);
		
		if (StringUtils.isEmpty(where)) {
			log.error("deleteByBuilder error. with where sql is empty. not allow. with \r\nparams={}, \r\nentity={}", target, entity);
			throw new ProviderSQLException("deleteByBuilder error. with where sql is empty");
		}
		
		log.debug("deleteByBuilder get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.append(where).toString();
	}
	
	private static StringBuilder getDeleteSQLByParams(Object params, DynamicEntityBean entity) {
		if (params == null) {
			return null;
		}
		String key = null;
		Object value = null;
		StringBuilder sql = new StringBuilder();
		try {
			for (DynamicColumnBean column : entity.getColumns()) {
				key = column.getFieldName();
				value = BeanUtils.getProperty(params, key);
				if (value == null) {
					//为空，跳过
					continue;
				}
				sql.append(" AND ")
					.append(column.getColumnName())
					.append(" = #{")
					.append(SQLProviderConstant.PARAM_OBJECT_ALIAS)
					.append(".")
					.append(column.getFieldName())
					.append("}");
			}
		} catch (Exception e) {
			log.error("get getDeleteSQLByParams sql error. with params is {}", params, e);
		}
		return sql;
	}

}
