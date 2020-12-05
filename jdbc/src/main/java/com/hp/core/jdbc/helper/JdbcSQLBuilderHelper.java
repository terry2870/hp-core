/**
 * 
 */
package com.hp.core.jdbc.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.exceptions.ProviderSQLException;
import com.hp.core.jdbc.model.JdbcSQLResponseBO;

/**
 * @author huangping
 * Jul 13, 2020
 */
public class JdbcSQLBuilderHelper {

	private static Logger log = LoggerFactory.getLogger(JdbcSQLBuilderHelper.class);
	
	/**
	 * 获取sql
	 * @param builders
	 * @param entity
	 * @param query
	 * @return
	 */
	public static JdbcSQLResponseBO getSQL(SQLBuilders builders, DynamicEntityBean entity, String... query) {
		if (builders == null) {
			throw new ProviderSQLException("builders is null.");
		}
		StringBuilder sql = new StringBuilder("SELECT ")
				.append(CollectionUtils.isEmpty(builders.getSelectList()) ? entity.getSelectColumns() : StringUtils.join(builders.getSelectList(), ", "))
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1 = 1 ");
		JdbcSQLResponseBO jdbc = getWhereSQLBySQLBuild(builders);
		if (jdbc == null) {
			jdbc = new JdbcSQLResponseBO();
			jdbc.setSql(sql.toString());
			return jdbc;
		}
		
		//额外查询条件
		if (ArrayUtils.isNotEmpty(query)) {
			for (String q : query) {
				sql.append(" AND ").append(q);
			}
		}
		
		//查询条件
		if (CollectionUtils.isNotEmpty(jdbc.getWhereSQL())) {
			for (String where : jdbc.getWhereSQL()) {
				sql.append(" AND ").append(where);
			}
		}
		
		//排序
		if (CollectionUtils.isNotEmpty(builders.getOrderbyList())) {
			sql.append(getOrderBy(builders.getOrderbyList()));
		}
		
		//分页
		if (builders.getPage() != null) {
			sql.append(getPageSQL(builders.getPage()));
		}
		
		jdbc.setSql(sql.toString());
		return jdbc;
	}
	
	/**
	 * 排序
	 * @param orderBy
	 * @param sql
	 */
	private static String getOrderBy(List<OrderBy> orderBy) {
		if (CollectionUtils.isEmpty(orderBy)) {
			return StringUtils.EMPTY;
		}
		List<String> orderByStringList = new ArrayList<>();
		for (OrderBy o : orderBy) {
			if (StringUtils.isEmpty(o.getFieldName())) {
				continue;
			}
			orderByStringList.add(o.toString());
		}
		if (CollectionUtils.isEmpty(orderByStringList)) {
			return StringUtils.EMPTY;
		} else {
			return " ORDER BY " + StringUtils.join(orderByStringList, ", ");
		}
	}
	
	/**
	 * 获取分页sql
	 * (由于mybatis的SQL对象不支持 append 方法，所以这里只能这样处理)
	 * @param page
	 * @param sql
	 */
	private static String getPageSQL(PageModel page) {
		if (page == null) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sql = new StringBuilder();
		if (CollectionUtils.isNotEmpty(page.getOrderBy())) {
			sql.append(getOrderBy(page.getOrderBy()));
		}
		
		if (page.getPageSize() > 0) {
			sql.append(" LIMIT ").append(page.getStartIndex()).append(", ").append(page.getPageSize());
		}
		
		return sql.toString();
	}
	
	/**
	 * 根据builds，获取查询条件
	 * @param whereList
	 * @return
	 */
	private static JdbcSQLResponseBO getWhereSQLBySQLBuild(SQLBuilders builders) {
		Collection<SQLWhere> whereList = builders.getWhereList();
		if (CollectionUtils.isEmpty(whereList)) {
			return null;
		}
		
		List<Object> params = new ArrayList<>();
		List<String> whereSQL = new ArrayList<>();
		try {
			for (SQLWhere where : whereList) {
				dealWhereSQL(where, whereSQL, params);
			}
			return new JdbcSQLResponseBO(params, whereSQL);
		} catch (Exception e) {
			log.error("get setSQLBySQLBuilds sql error. with whereList is {}", whereList, e);
		}
		return null;
	}
	
	/**
	 * 设置查询条件
	 * @param where
	 * @param sql
	 * @param params
	 */
	private static void dealWhereSQL(SQLWhere where, List<String> whereSQL, List<Object> params) {
		if (checkNull(where.getValue())) {
			return;
		}
		
		switch (where.getOperator()) {
		case EQUALS:
			whereSQL.add(where.getField() + " = ?");
			params.add(where.getValue());
			break;
		case NOT_EQUALS:
			whereSQL.add(where.getField() + " != ?");
			params.add(where.getValue());
			break;
		case LIKE:
			whereSQL.add("INSTR("+ where.getField() +", ?) > 0");
			params.add(where.getValue());
			break;
		case IN:
			whereSQL.add(where.getField() + " IN ("+ where.getValue() +")");
			break;
		case NOT_IN:
			whereSQL.add(where.getField() + " NOT IN ("+ where.getValue() +")");
			break;
		case GT:
			whereSQL.add(where.getField() + " > ?");
			params.add(where.getValue());
			break;
		case LT:
			whereSQL.add(where.getField() + " < ?");
			params.add(where.getValue());
			break;
		case GTE:
			whereSQL.add(where.getField() + " >= ?");
			params.add(where.getValue());
			break;
		case LTE:
			whereSQL.add(where.getField() + " <= ?");
			params.add(where.getValue());
			break;
		case PREFIX:
			whereSQL.add(where.getField() + " LIKE '"+ where.getValue() +"%'");
			break;
		case SUFFIX:
			whereSQL.add(where.getField() + " LIKE '%"+ where.getValue() +"'");
			break;
		default:
			break;
		}
	}
	
	/**
	 * 检查是否为空
	 * @param value
	 * @return
	 */
	private static boolean checkNull(Object value) {
		if (value == null) {
			//null
			return true;
		}
		if (value instanceof String && StringUtils.isEmpty((String) value)) {
			//空字符串
			return true;
		}
		
		return false;
	}
}
