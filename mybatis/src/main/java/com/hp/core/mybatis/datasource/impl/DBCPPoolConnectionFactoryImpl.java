/**
 * 
 */
package com.hp.core.mybatis.datasource.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;
import com.hp.core.mybatis.datasource.AbstPoolConnectionFactory;
import com.hp.core.mybatis.enums.DatabaseEnum;
import com.hp.core.mybatis.exceptions.MasterUrlNotFoundException;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DBCPPoolConnectionFactoryImpl implements AbstPoolConnectionFactory {

	private static Logger log = LoggerFactory.getLogger(DBCPPoolConnectionFactoryImpl.class);
	
	@Override
	public DynamicDatasourceBean getDatasource(DatasourceConfigBean bean) {
		if (bean == null || CollectionUtils.isEmpty(bean.getMasterIpPort())) {
			log.error("getDatasource error. with masterIpPort is empty");
			throw new MasterUrlNotFoundException("master url is not find from bean");
		}
		
		DynamicDatasourceBean result = new DynamicDatasourceBean();
		BasicDataSource ds = null;
		List<DataSource> masterDatasource = new ArrayList<>(bean.getMasterIpPort().size());
		for (String ipPort : bean.getMasterIpPort()) {
			ds = getDs(bean);
			ds.setUrl(DatabaseEnum.getConnectionUrlByDatabaseType(bean.getDatabaseType()).getConnectionUrl(ipPort, bean.getDatabaseName(), bean.getConnectionParam()));
			masterDatasource.add(ds);
		}
		result.setMasterDatasource(masterDatasource);
		
		if (CollectionUtils.isNotEmpty(bean.getSlaveIpPort())) {
			List<DataSource> slaveDatasource = new ArrayList<>(bean.getSlaveIpPort().size());
			for (String ipPort : bean.getSlaveIpPort()) {
				ds = getDs(bean);
				ds.setUrl(DatabaseEnum.getConnectionUrlByDatabaseType(bean.getDatabaseType()).getConnectionUrl(ipPort, bean.getDatabaseName(), bean.getConnectionParam()));
				slaveDatasource.add(ds);
			}
			result.setSlaveDatasource(slaveDatasource);
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
		ds.setDriverClassName(bean.getDriverClassName());
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
