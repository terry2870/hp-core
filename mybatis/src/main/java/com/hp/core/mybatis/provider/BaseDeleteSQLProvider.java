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

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.mybatis.bean.DynamicEntityBean;

public class BaseDeleteSQLProvider {

	private static Logger log = LoggerFactory.getLogger(BaseDeleteSQLProvider.class);
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @return
	 */
	public static String deleteByPrimaryKey(Object id) {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
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
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		Object[] arr = (Object[]) params.get("array");
		StringBuilder sql = new StringBuilder("DELETE FROM ").append(entity.getTableName());
		sql.append("\n");
		sql.append(" WHERE ").append(entity.getPrimaryKeyColumnName()).append(" IN (");
		for (int i = 0; i < arr.length; i++) {
			sql.append("#{array[").append(i).append("]}");
			if (i != arr.length - 1) {
				sql.append(", ");
			}
		}
		sql.append(")");
		log.debug("deleteByPrimaryKey get sql \r\nsql={} \r\narr={} \r\nentity={}", sql, arr.length, entity);
		return sql.toString();
	}
}
