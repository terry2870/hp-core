/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.fastjson.JSON;
import com.hp.core.mybatis.bean.DAOInterfaceInfoBean;
import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;
import com.hp.core.mybatis.enums.ConnectionPoolFactoryEnum;

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource implements InitializingBean {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	
	//存放所有的dao对应的数据源的key
	// key=dao名称，value=databaseName
	private static Map<String, String> datasourceKey = new HashMap<>();
	
	//默认的数据源名称
	private static String DEFAULT_DATABASE_NAME = "";
	
	private static final String MASTER_DS_KEY_PREX = "master_";
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
	private Resource[] resources = new Resource[0];

	@Override
	protected Object determineCurrentLookupKey() {
		//根据用户
		DAOInterfaceInfoBean daoInfo = DynamicDataSourceHolder.getRouteDAOInfo();
		if (datasourceKey == null) {
			log.warn("determineCurrentLookupKey error. with daoInfo is empty.");
			return null;
		}
		
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		//设置targetDataSources 值
		if (ArrayUtils.isEmpty(this.resources)) {
			log.error("set DynamicDatasource error. with resource is empty.");
			return;
		}
		try {
			//解析文件，设置db
			String jsonTxt = FileUtils.readFileToString(this.resources[0].getFile(), "UTF-8");
			List<DatasourceConfigBean> datasourceList = JSON.parseArray(jsonTxt, DatasourceConfigBean.class);
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			DynamicDatasourceBean dynamicDatasourceBean = null;
			AbstConnectionPoolFactory connectionPool = null;
			for (DatasourceConfigBean b : datasourceList) {
				connectionPool = ConnectionPoolFactoryEnum.getConnectionPoolFactory(b.getPoolName());
				dynamicDatasourceBean = connectionPool.getDatasource(b);
				
				//设置master
				targetDataSources.put(buildMasterDatasourceKey(b), dynamicDatasourceBean.getMasterDatasource());
				
				//设置slave
				if (dynamicDatasourceBean.getSlaveDatasource() != null) {
					targetDataSources.put(buildSlaveDatasourceKey(b), dynamicDatasourceBean.getSlaveDatasource());
				}
				
				//处理dao
				dealDAOS(b);
			}
			super.setTargetDataSources(targetDataSources);
			super.afterPropertiesSet();
		} catch (IOException e) {
			log.error("deal DynamicDatasource error.", e);
		}
	}
	
	/**
	 * 处理dao
	 * @param bean
	 */
	private void dealDAOS(DatasourceConfigBean bean) {
		if (CollectionUtils.isEmpty(bean.getDaos())) {
			return;
		}
		for (String dao : bean.getDaos()) {
			datasourceKey.put(dao, bean.getDatabaseName());
		}
	}

	/**
	 * 获取主数据源的key
	 * @param bean
	 * @return
	 */
	private String buildMasterDatasourceKey(DatasourceConfigBean bean) {
		return MASTER_DS_KEY_PREX + bean.getDatabaseName();
	}
	
	/**
	 * 获取从数据源的key
	 * @param bean
	 * @return
	 */
	private String buildSlaveDatasourceKey(DatasourceConfigBean bean) {
		return SLAVE_DS_KEY_PREX + bean.getDatabaseName();
	}

	public void setResources(Resource... resources) {
		this.resources = resources;
	}

}
