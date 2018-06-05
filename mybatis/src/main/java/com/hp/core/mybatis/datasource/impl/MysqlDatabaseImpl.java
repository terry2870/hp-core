/**
 * 
 */
package com.hp.core.mybatis.datasource.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.datasource.AbstDatabase;

/**
 * @author huangping
 * 2018年4月2日
 */
public class MysqlDatabaseImpl implements AbstDatabase {

	@Override
	public String getConnectionUrl(String ipPort, String databaseName, String... params) {
		String url = "jdbc:mysql://"+ ipPort +"/"+ databaseName;
		if (ArrayUtils.isNotEmpty(params) && StringUtils.isNotEmpty(params[0])) {
			url += "?" + params[0];
		}
		return url;
	}

	@Override
	public String getDriverClassName(DatasourceConfigBean bean) {
		if (StringUtils.isNotEmpty(bean.getDriverClassName())) {
			return bean.getDriverClassName();
		}
		return "com.mysql.cj.jdbc.Driver";
	}

	@Override
	public String getAllTableSql(String findTableName) {
		String sql = "SELECT LCASE(a.table_name) table_name, a.table_comment table_comment, b.column_name primary_key FROM Information_schema.tables a LEFT JOIN Information_schema.columns b ON a.table_name=b.table_name WHERE b.column_key='PRI'";
		if (StringUtils.isNotEmpty(findTableName)) {
			sql += " AND LCASE(a.table_name) LIKE '%" + findTableName.toLowerCase() + "%'";
		}
		sql += " ORDER BY a.table_name";
		return sql;
	}

	@Override
	public String getColumnByTableNameSql(String tableName) {
		String sql = "select LCASE(table_name) table_name, LCASE(column_name) column_name, is_nullable, data_type, column_key, column_comment from Information_schema.columns where table_name='"+ tableName +"'";
		return sql;
	}

	@Override
	public String dbTypeToJavaType(String jdbcType) {
		String dataType = jdbcType.toLowerCase();
		if (dataType.indexOf("int") >= 0) {
			return "Integer";
		} else if (dataType.indexOf("long") >= 0) {
			return "Long";
		} else if (dataType.equalsIgnoreCase("double")) {
			return "Double";
		} else if (dataType.equalsIgnoreCase("float")) {
			return "Float";
		} else {
			return "String";
		}
	}

}
