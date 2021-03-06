/**
 * 
 */
package com.hp.core.database.datasource.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.hp.core.database.bean.DatasourceConfigBean;
import com.hp.core.database.datasource.AbstConnectionPoolFactory;
import com.hp.core.database.datasource.AbstDatabase;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DBCPConnectionPoolFactoryImpl implements AbstConnectionPoolFactory {
	
	/**
	 * 获取基础信息
	 */
	@Override
	public DataSource getDatasource(DatasourceConfigBean bean, AbstDatabase database, String ipPort) {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(database.getConnectionUrl(ipPort, bean.getDatabaseName(), bean.getConnectionParam()));
		ds.setDriverClassName(database.getDriverClassName(bean));
		ds.setUsername(bean.getUsername());
		ds.setPassword(bean.getPassword());
		ds.setMaxTotal(bean.getMaxTotal());
		ds.setMaxIdle(bean.getMaxIdle());
		ds.setInitialSize(bean.getInitialSize());
		ds.setMaxWaitMillis(bean.getMaxWaitMillis());
		ds.setTimeBetweenEvictionRunsMillis(bean.getTimeBetweenEvictionRunsMillis());
		ds.setTestWhileIdle(bean.isTestWhileIdle());
		ds.setNumTestsPerEvictionRun(bean.getNumTestsPerEvictionRun());
		ds.setValidationQuery(bean.getValidationQuery());
		ds.setPoolPreparedStatements(bean.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(bean.getMaxPoolPreparedStatementPerConnectionSize());
		return ds;
	}
	

}
