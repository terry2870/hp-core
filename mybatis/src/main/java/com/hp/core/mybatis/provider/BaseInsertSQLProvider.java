/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.mybatis.bean.DynamicColumnBean;
import com.hp.core.mybatis.bean.DynamicEntityBean;


/**
 * 基本的插入操作
 * 获取基本的插入操作的sql
 * @author huangping
 * 2018年5月30日
 */
public class BaseInsertSQLProvider {

	private static Logger log = LoggerFactory.getLogger(BaseInsertSQLProvider.class);
	
	/**
	 * 新增数据
	 * @param target
	 * @return
	 */
	public static String insert(Object target) {
		return getInsertSQL(target, true);
	}
	
	/**
	 * 根据条件，新增字段
	 * @param target
	 * @return
	 */
	public static String insertSelective(Object target) {
		return getInsertSQL(target, false);
	}
	
	/**
	 * 批量插入
	 * @param params
	 * @return
	 */
	public static String insertBatch(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(entity.getTableName());
		sql.append("\n (");
		sql.append(entity.getInsertColumnString());
		sql.append(") \n VALUES \n");
		
		List<?> list = (List<?>) params.get("list");
		DynamicColumnBean column = null;
		for (int i = 0; i < list.size(); i++) {
			sql.append("(");
			for (int j = 0; j < entity.getInsertColumnsList().size(); j++) {
				column = entity.getInsertColumnsList().get(j);
				sql.append("#{list[").append(i).append("].").append(column.getFieldName()).append("}");
				if (j != entity.getInsertColumnsList().size() - 1) {
					sql.append(", ");
				}
			}
			sql.append(")");
			if (i != list.size() - 1) {
				sql.append(", \n");
			}
		}
		log.debug("insertBatch get sql \r\nsql={} \r\nlist.size={} \r\nentity={}", sql, list.size(), entity);
		return sql.toString();
	}
	
	/**
	 * 获取更新的sql
	 * @param target
	 * @param all
	 * @return
	 */
	private static String getInsertSQL(Object target, boolean all) {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		SQL sql = new SQL().INSERT_INTO(entity.getTableName());
		Object value = null;
		try {
			for (DynamicColumnBean column : entity.getInsertColumnsList()) {
				if (all) {
					//插入所有字段
					sql.VALUES(column.getColumnName(), "#{"+ column.getFieldName() +"}");
				} else {
					value = BeanUtils.getProperty(target, column.getFieldName());
					if (value == null) {
						//参数为空，跳过
						continue;
					}
					sql.VALUES(column.getColumnName(), "#{"+ column.getFieldName() +"}");
				}
			}
		} catch (Exception e) {
			log.error("getInsertSQL error. with params={}", target, e);
		}
		log.debug("getInsertSQL get sql \r\nsql={} \r\nparams={} \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
}
