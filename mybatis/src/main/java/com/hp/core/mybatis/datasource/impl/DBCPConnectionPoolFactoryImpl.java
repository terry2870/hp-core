/**
 * 
 */
package com.hp.core.mybatis.datasource.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;
import com.hp.core.mybatis.datasource.AbstConnectionPoolFactory;
import com.hp.core.mybatis.datasource.AbstDatabase;
import com.hp.core.mybatis.enums.DatabaseEnum;
import com.hp.core.mybatis.exceptions.MasterUrlNotFoundException;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DBCPConnectionPoolFactoryImpl implements AbstConnectionPoolFactory {

	private static Logger log = LoggerFactory.getLogger(DBCPConnectionPoolFactoryImpl.class);
	
	@Override
	public DynamicDatasourceBean getDatasource(DatasourceConfigBean bean) {
		if (bean == null || StringUtils.isEmpty(bean.getMasterIpPort())) {
			log.error("getDatasource error. with masterIpPort is empty");
			throw new MasterUrlNotFoundException("master url is not find from bean");
		}
		
		DynamicDatasourceBean result = new DynamicDatasourceBean();
		BasicDataSource ds = null;
		AbstDatabase database = DatabaseEnum.getConnectionUrlByDatabaseType(bean.getDatabaseType());
		
		//处理master的数据源
		ds = getDs(bean);
		ds.setUrl(database.getConnectionUrl(bean.getMasterIpPort(), bean.getDatabaseName(), bean.getConnectionParam()));
		ds.setDriverClassName(database.getDriverClassName(bean));
		result.setMasterDatasource(ds);
		
		//处理slave数据源
		if (StringUtils.isNotEmpty(bean.getSlaveIpPort())) {
			ds.setUrl(database.getConnectionUrl(bean.getSlaveIpPort(), bean.getDatabaseName(), bean.getConnectionParam()));
			ds.setDriverClassName(database.getDriverClassName(bean));
			result.setSlaveDatasource(ds);
		}
		return result;
	}
	
	/**
	 * 获取基础信息
	 * @param bean
	 * @return
	 */
	private BasicDataSource getDs(DatasourceConfigBean bean) {
		BasicDataSource ds = new BasicDataSource();
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
		return ds;
	}
	

}
