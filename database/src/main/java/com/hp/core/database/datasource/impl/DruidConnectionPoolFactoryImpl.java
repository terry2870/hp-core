/**
 * 
 */
package com.hp.core.database.datasource.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.hp.core.database.bean.DatasourceConfigBean;
import com.hp.core.database.datasource.AbstConnectionPoolFactory;
import com.hp.core.database.datasource.AbstDatabase;

/**
 * @author huangping
 * Jul 16, 2020
 */
public class DruidConnectionPoolFactoryImpl implements AbstConnectionPoolFactory {

	@Override
	public DataSource getDatasource(DatasourceConfigBean bean, AbstDatabase database, String ipPort) {
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(database.getConnectionUrl(ipPort, bean.getDatabaseName(), bean.getConnectionParam()));
		ds.setUsername(bean.getUsername());
		ds.setPassword(bean.getPassword());
        
        //下面都是可选的配置
        ds.setInitialSize(bean.getInitialSize());  //初始连接数，默认0
        ds.setMaxActive(bean.getMaxTotal());  //最大连接数，默认8
        ds.setMinIdle(bean.getMinIdle());  //最小闲置数
        ds.setMaxWait(bean.getMaxWaitMillis());  //获取连接的最大等待时间，单位毫秒
		ds.setTimeBetweenEvictionRunsMillis(bean.getTimeBetweenEvictionRunsMillis());
		ds.setTestWhileIdle(bean.isTestWhileIdle());
		ds.setValidationQuery(bean.getValidationQuery());
		ds.setPoolPreparedStatements(bean.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(bean.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			ds.setFilters("stat");
		} catch (SQLException e) {
		}
		return ds;
	}

}
