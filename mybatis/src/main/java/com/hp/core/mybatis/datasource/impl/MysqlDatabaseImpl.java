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

}
