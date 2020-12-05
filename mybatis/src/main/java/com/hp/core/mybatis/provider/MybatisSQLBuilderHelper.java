/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.mybatis.constant.SQLProviderConstant;

/**
 * @author huangping
 * Oct 24, 2019
 */
public class MybatisSQLBuilderHelper {

	private static Logger log = LoggerFactory.getLogger(MybatisSQLBuilderHelper.class);
	
	private static final String SQL_WHERE_VALUE_PREFIX = SQLProviderConstant.SQL_BUILDS_ALIAS + ".whereList";
	
	/**
	 * 根据builds，获取查询条件
	 * @param whereList
	 * @return
	 */
	public static String getSQLBySQLBuild(SQLBuilders builders) {
		Collection<SQLWhere> whereList = builders.getWhereList();
		if (CollectionUtils.isEmpty(whereList)) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			int i = 0;
			for (SQLWhere where : whereList) {
				sb.append(getSQL(where, i, StringUtils.isEmpty(builders.getSqlWherePrefix()) ? SQL_WHERE_VALUE_PREFIX : builders.getSqlWherePrefix()));
				i++;
			}
		} catch (Exception e) {
			log.error("get setSQLBySQLBuilds sql error. with whereList is {}", whereList, e);
		}
		return sb.toString();
	}
	
	/**
	 * 设置查询条件
	 * @param builder
	 * @param sql
	 * @param index
	 */
	private static String getSQL(SQLWhere where, int index, String sqlWherePrefix) {
		if (checkNull(where.getValue())) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sql = new StringBuilder();
		switch (where.getOperator()) {
		case EQUALS:
			sql.append(" AND ")
			.append(where.getField())
			.append(" = #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case NOT_EQUALS:
			sql.append(" AND ")
			.append(where.getField())
			.append(" != #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case LIKE:
			sql.append(" AND INSTR(")
			.append(where.getField())
			.append(", #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}) > 0");
			break;
		case IN:
			sql.append(" AND ")
			.append(where.getField())
			.append(" IN (").append(where.getValue()).append(")");
			break;
		case NOT_IN:
			sql.append(" AND ")
			.append(where.getField())
			.append(" NOT IN (").append(where.getValue()).append(")");
			break;
		case GT:
			sql.append(" AND ")
			.append(where.getField())
			.append(" > #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case LT:
			sql.append(" AND ")
			.append(where.getField())
			.append(" < #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case GTE:
			sql.append(" AND ")
			.append(where.getField())
			.append(" >= #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case LTE:
			sql.append(" AND ")
			.append(where.getField())
			.append(" <= #{")
			.append(sqlWherePrefix)
			.append("[").append(index).append("].value}");
			break;
		case PREFIX:
			sql.append(" AND ")
			.append(where.getField())
			.append(" like '")
			.append(where.getValue())
			.append("%'");
			break;
		case SUFFIX:
			sql.append(" AND ")
			.append(where.getField())
			.append(" like '%")
			.append(where.getValue())
			.append("'");
			break;
		default:
			break;
		}
		return sql.toString();
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
