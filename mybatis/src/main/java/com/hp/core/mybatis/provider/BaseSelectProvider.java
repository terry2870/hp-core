/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.mybatis.bean.DynamicColumnBean;
import com.hp.core.mybatis.bean.DynamicEntityBean;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.enums.QueryTypeEnum;
import com.hp.core.mybatis.exceptions.ProviderSQLException;

/**
 * 基本的查询操作
 * 获取基本的查询操作的sql
 * @author huangping 2018年5月21日
 */
public class BaseSelectProvider {

	private static Logger log = LoggerFactory.getLogger(BaseSelectProvider.class);
	
	/**
	 * 无条件，查询总数
	 * @return
	 */
	public static String selectAllCount() {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		SQL sql = new SQL()
				.SELECT("count(*)")
				.FROM(entity.getTableName());
		log.debug("selectAllCount get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public static String selectByPrimaryKey(Object id) {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		SQL sql = new SQL()
				.SELECT(entity.getSelectColumns())
				.FROM(entity.getTableName())
				.WHERE(entity.getPrimaryKeyColumnName() + "=#{id}");
		log.debug("selectByPrimaryKey get sql \r\nsql={} \r\nid={}  \r\nentity={}", sql, id, entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键，批量查询
	 * @param params
	 * @return
	 */
	public static String selectByPrimaryKeys(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		List<?> list = (List) params.get("list");
		StringBuilder sql = new StringBuilder("SELECT ")
				.append("\n")
				.append(entity.getSelectColumns())
				.append("\n")
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE ")
				.append(entity.getPrimaryKeyColumnName())
				.append(" IN (");
		for (int i = 0; i < list.size(); i++) {
			sql.append("#{list[").append(i).append("]}");
			if (i != list.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(")");
		log.debug("selectByPrimaryKeys get sql \r\nsql={} \r\nlist={}  \r\nentity={}", sql, list.size(), entity);
		return sql.toString();
	}
	
	/**
	 * 根据条件，查询数量
	 * @param target
	 * @return
	 */
	public static String selectCountByParams(Map<String, Object> target) {
		if (target == null) {
			return selectAllCount();
		}
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		
		//遍历属性字段，不为空的都加入sql查询条件
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		log.debug("selectCountByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	/**
	 * 根据条件，查询list（分页）
	 * @param target
	 * @return
	 */
	public static String selectListByParams(Map<String, Object> target) {
		if (target == null) {
			log.error("selectListByParams error. with params is null.");
			throw new ProviderSQLException("params is null");
		}
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		StringBuilder sql = new StringBuilder("SELECT ")
				.append(entity.getSelectColumns())
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		
		if (target.containsKey(SQLProviderConstant.PAGE_OBJECT_ALIAS)) {
			getPageSQL((PageModel) target.get(SQLProviderConstant.PAGE_OBJECT_ALIAS), entity, sql);
		}
		
		log.debug("selectListByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	/**
	 * 获取分页sql
	 * (由于mybatis的SQL对象不支持 append 方法，所以这里只能这样处理)
	 * @param page
	 * @param entity
	 */
	private static void getPageSQL(PageModel page, DynamicEntityBean entity, StringBuilder sql) {
		if (page == null) {
			return;
		}
		if (StringUtils.isNotEmpty(page.getSortColumn())) {
			sql.append(" order by ").append(page.getSortColumn()).append(" ").append(page.getOrder());
		}
		if (page.getPageSize() > 0) {
			sql.append(" limit ").append(page.getStartIndex()).append(", ").append(page.getPageSize());
		}
	}
	
	/**
	 * 设置查询条件
	 * @param params
	 * @param entity
	 * @param sql
	 */
	private static void setSQLByParams(Object params, DynamicEntityBean entity, StringBuilder sql) {
		if (params == null) {
			return;
		}
		String key = null;
		Object value = null;
		try {
			for (DynamicColumnBean column : entity.getColumns()) {
				key = column.getFieldName();
				value = BeanUtils.getProperty(params, key);
				if (value == null) {
					//为空，跳过
					continue;
				}
				if (QueryTypeEnum.EQUALS.equals(column.getQueryType())) {
					sql.append(" AND ")
						.append(column.getColumnName())
						.append(" = #{")
						.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
						.append(".")
						.append(column.getFieldName())
						.append("}");
				} else if (QueryTypeEnum.LIKE.equals(column.getQueryType())) {
					sql.append(" AND INSTR(")
						.append(column.getColumnName())
						.append(", #{")
						.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
						.append(".")
						.append(column.getFieldName())
						.append("}) > 0");
				}
				
			}
		} catch (Exception e) {
			log.error("get setSQLByParams sql error. with params is {}", params, e);
		}
	}
	
}
