/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
	private static Map<String, String> databaseNameMap = new HashMap<>();
	
	//默认的数据源名称
	private static String DEFAULT_DATABASE_NAME = "";
	
	private static final String MASTER_DS_KEY_PREX = "master_";
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
	private static Pattern select = Pattern.compile("^select.*");
	private static Pattern update = Pattern.compile("^update.*");
	private static Pattern insert = Pattern.compile("^insert.*");
	private static Pattern delete = Pattern.compile("^delete.*");
	
	private Resource[] resources = new Resource[0];

	@Override
	protected Object determineCurrentLookupKey() {
		//根据用户
		DAOInterfaceInfoBean daoInfo = DynamicDataSourceHolder.getRouteDAOInfo();
		if (daoInfo == null) {
			log.warn("determineCurrentLookupKey error. with daoInfo is empty.");
			return null;
		}
		
		//按照dao的className，从数据源中获取数据源
		String mapperNamespace = daoInfo.getMapperNamespace();
		String databaseName = databaseNameMap.get(mapperNamespace);
		if (StringUtils.isEmpty(databaseName)) {
			//如果没有，则使用默认数据源
			databaseName = DEFAULT_DATABASE_NAME;
		}
		
		boolean fromMaster = false;
		//获取用户执行的sql方法名
		String statementId = daoInfo.getStatementId();
		if (select.matcher(statementId).matches()) {
			//使用slave数据源
			fromMaster = false;
		} else if (update.matcher(statementId).matches() || insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			//使用master数据源
			fromMaster = true;
		} else {
			//如果statemenetId不符合规范，则告警，并且使用master数据源
			log.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ", mapperNamespace, statementId);
			fromMaster = true;
		}
		
		if (fromMaster) {
			return buildMasterDatasourceKey(databaseName);
		} else {
			return buildSlaveDatasourceKey(databaseName);
		}
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
				targetDataSources.put(buildMasterDatasourceKey(b.getDatabaseName()), dynamicDatasourceBean.getMasterDatasource());
				
				//设置slave
				if (dynamicDatasourceBean.getSlaveDatasource() != null) {
					targetDataSources.put(buildSlaveDatasourceKey(b.getDatabaseName()), dynamicDatasourceBean.getSlaveDatasource());
				}
				
				//默认数据源
				if (b.isDefaultDatabase()) {
					DEFAULT_DATABASE_NAME = b.getDatabaseName();
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
			databaseNameMap.put(dao, bean.getDatabaseName());
		}
	}

	/**
	 * 获取主数据源的key
	 * @param databaseName
	 * @return
	 */
	private String buildMasterDatasourceKey(String databaseName) {
		return MASTER_DS_KEY_PREX + databaseName;
	}
	
	/**
	 * 获取从数据源的key
	 * @param databaseName
	 * @return
	 */
	private String buildSlaveDatasourceKey(String databaseName) {
		return SLAVE_DS_KEY_PREX + databaseName;
	}

	public void setResources(Resource... resources) {
		this.resources = resources;
	}

}
