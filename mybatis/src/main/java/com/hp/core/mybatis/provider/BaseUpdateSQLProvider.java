/**
 * 
 */
package com.hp.core.mybatis.provider;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DynamicColumnBean;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;

/**
 * 基本的更新操作
 * 继承该接口，可以实现一些简单的更新操作
 * @author huangping
 * 2018年5月29日
 */
public class BaseUpdateSQLProvider {
	
	static Logger log = LoggerFactory.getLogger(BaseUpdateSQLProvider.class);

	/**
	 * 根据主键更新
	 * @param target
	 * @return
	 */
	public static String updateByPrimaryKey(Object target) {
		return getUpdateSQL(target, true);
	}
	
	/**
	 * 根据条件，更新字段
	 * @param target
	 * @return
	 */
	public static String updateByPrimaryKeySelective(Object target) {
		return getUpdateSQL(target, false);
	}
	
	/**
	 * 获取更新的sql
	 * @param params
	 * @param all
	 * @return
	 */
	private static String getUpdateSQL(Object target, boolean all) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQL sql = new SQL().UPDATE(entity.getTableName());
		Object value = null;
		try {
			for (DynamicColumnBean column : entity.getUpdateColumnsList()) {
				if (column.isPrimaryKey()) {
					// 主键，跳过
					continue;
				}
				if (all) {
					//更新所有
					sql.SET(column.getColumnName() + " = #{" + column.getFieldName() + "}");
				} else {
					value = BeanUtils.getProperty(target, column.getFieldName());
					if (value == null) {
						//参数为空，跳过
						continue;
					}
					sql.SET(column.getColumnName() + " = #{" + column.getFieldName() + "}");
				}
			}
		} catch (Exception e) {
			log.error("getUpdateSQL error. with params={}", target, e);
		}
		sql.WHERE(entity.getPrimaryKeyColumnName() + " = #{" + entity.getPrimaryKeyFieldName() + "}");
		log.debug("getUpdateSQL get sql \r\nsql={} \r\nparams={} \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
}
