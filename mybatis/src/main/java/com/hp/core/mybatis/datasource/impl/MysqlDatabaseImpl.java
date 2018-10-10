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
		if (bean != null && StringUtils.isNotEmpty(bean.getDriverClassName())) {
			return bean.getDriverClassName();
		}
		return "com.mysql.cj.jdbc.Driver";
	}

	@Override
	public String dbTypeToJavaType(String jdbcType) {
		String dataType = jdbcType.toLowerCase();
		if (dataType.indexOf("int") >= 0 || dataType.equalsIgnoreCase("bit")) {
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

	@Override
	public String getCheckSql() {
		return "select 1";
	}

}
