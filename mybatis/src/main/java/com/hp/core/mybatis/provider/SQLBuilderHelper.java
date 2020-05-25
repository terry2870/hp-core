/**
 * 
 */
package com.hp.core.mybatis.provider;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.SQLBuilder;
import com.hp.core.mybatis.constant.SQLProviderConstant;

/**
 * @author huangping
 * Oct 24, 2019
 */
public class SQLBuilderHelper {

	private static Logger log = LoggerFactory.getLogger(SQLBuilderHelper.class);
	
	/**
	 * 根据builds，获取查询条件
	 * @param builders
	 * @return
	 */
	public static String getSQLBySQLBuild(SQLBuilder[] builders) {
		if (ArrayUtils.isEmpty(builders)) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < builders.length; i++) {
				sb.append(getSQLBySQLBuild(builders[i], i));
			}
		} catch (Exception e) {
			log.error("get setSQLBySQLBuilds sql error. with builders is {}", builders, e);
		}
		return sb.toString();
	}
	
	/**
	 * 设置查询条件
	 * @param builder
	 * @param sql
	 * @param index
	 */
	public static String getSQLBySQLBuild(SQLBuilder builder, int index) {
		if (checkNull(builder)) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sql = new StringBuilder();
		switch (builder.getOperator()) {
		case EQUALS:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" = #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case NOT_EQUALS:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" != #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case LIKE:
			sql.append(" AND INSTR(")
			.append(builder.getField())
			.append(", #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}) > 0");
			break;
		case IN:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" IN (").append(builder.getValue()).append(")");
			break;
		case NOT_IN:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" NOT IN (").append(builder.getValue()).append(")");
			break;
		case GT:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" > #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case LT:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" < #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case GTE:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" >= #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case LTE:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" <= #{")
			.append(SQLProviderConstant.SQL_BUILD_ALIAS)
			.append("[").append(index).append("].value}");
			break;
		case PREFIX:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" like '")
			.append(builder.getValue())
			.append("%'");
			break;
		case SUFFIX:
			sql.append(" AND ")
			.append(builder.getField())
			.append(" like '%")
			.append(builder.getValue())
			.append("'");
			break;
		default:
			break;
		}
		return sql.toString();
	}
	
	/**
	 * 检查是否为空
	 * @param build
	 * @return
	 */
	private static boolean checkNull(SQLBuilder build) {
		if (build == null || build.getValue() == null) {
			//null
			return true;
		}
		if (build.getValue() instanceof String && StringUtils.isEmpty((String) build.getValue())) {
			//空字符串
			return true;
		}
		
		return false;
	}
}
