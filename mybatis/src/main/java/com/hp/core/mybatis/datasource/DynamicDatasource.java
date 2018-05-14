/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.hp.core.mybatis.bean.DAOInterfaceInfoBean;
import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;
import com.hp.core.mybatis.enums.ConnectionPoolFactoryEnum;
import com.hp.core.mybatis.exceptions.DataSourceNotFoundException;
import com.hp.core.mybatis.exceptions.DynamicDataSourceRouteException;
import com.hp.core.mybatis.interceptor.DAOMethodInterceptorHandle;

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource implements InitializingBean {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	
	//存放所有的dao对应的数据源的key
	// key=dao名称，value=databaseName
	private static Map<String, String> databaseNameMap = new HashMap<>();
	
	/**
	 * 存放所有的数据源主从的个数
	 * master_databaseName,10
	 * slave_databaseName,20
	 */
	private static Map<String, Integer> databaseIPCountMap = new HashMap<>();
	
	//默认的数据源名称
	private static String DEFAULT_DATABASE_NAME = "";
	
	private static final String MASTER_DS_KEY_PREX = "master_";
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
	private static Pattern select = Pattern.compile("^select.*");
	private static Pattern update = Pattern.compile("^update.*");
	private static Pattern insert = Pattern.compile("^insert.*");
	private static Pattern delete = Pattern.compile("^delete.*");
	
	private Map<String, Object> databasesMap;

	@Override
	protected Object determineCurrentLookupKey() {
		//根据用户
		DAOInterfaceInfoBean daoInfo = DAOMethodInterceptorHandle.getRouteDAOInfo();
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
		statementId = statementId.toLowerCase();
		if (select.matcher(statementId).matches()) {
			//使用slave数据源
			fromMaster = false;
		} else if (update.matcher(statementId).matches() || insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			//使用master数据源
			fromMaster = true;
		} else {
			//如果statemenetId不符合规范，则告警，并且使用master数据源
			log.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ", mapperNamespace, daoInfo.getStatementId());
			fromMaster = true;
		}
		
		String result = getDatasourceByKey(databaseName, fromMaster);
		log.debug("-------select route datasource with statementId={} and result is {}", (daoInfo.getMapperNamespace() + "." + daoInfo.getStatementId()), result);
		return result;
	}
	
	/**
	 * 随机获取路由
	 * @param databaseName
	 * @param fromMaster
	 * @return
	 */
	private String getDatasourceByKey(String databaseName, boolean fromMaster) {
		String datasourceKey = null;
		Integer num = null;
		if (fromMaster) {
			datasourceKey = buildMasterDatasourceKey(databaseName, -1);
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//没找到，直接抛出异常
				log.error("datasource not found with databaseName= {}", databaseName);
				throw new DataSourceNotFoundException("datasource not found with databaseName= " + databaseName);
			}
		} else {
			datasourceKey = buildSlaveDatasourceKey(databaseName, -1);
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//从库没有，则路由到主库
				return getDatasourceByKey(databaseName, true);
			}
		}
		
		int random = 0;
		if (num == 1) {
			//如果就只有一个数据源，则就选择它
			random = 0;
		} else {
			//随机获取一个数据源
			random = RandomUtils.nextInt(0, num);
		}
		return fromMaster ? buildMasterDatasourceKey(databaseName, random) : buildSlaveDatasourceKey(databaseName, random);
	}

	@Override
	public void afterPropertiesSet() {
		//设置targetDataSources 值
		if (databasesMap == null) {
			log.error("set DynamicDatasource error. with databasesMap is null.");
			throw new DynamicDataSourceRouteException("DynamicDatasource route error. with databasesMap is null");
		}
		try {
			//解析文件，设置db
			//String jsonTxt = FileUtils.readFileToString(ResourceUtils.getFile(databaseConfigLocation), "UTF-8");
			//List<DatasourceConfigBean> datasourceList = JSON.parseArray(jsonTxt, DatasourceConfigBean.class);
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			DynamicDatasourceBean dynamicDatasourceBean = null;
			AbstConnectionPoolFactory connectionPool = null;
			String databaseName = null;
			Object poolName = null, connectionParam = null, databaseType = null, driverClassName = null;
			Map<String, Object> obj = null;
			Map<String, Object> databaseMap = (Map<String, Object>) databasesMap.get("datasources");
			DatasourceConfigBean datasourceBean = null;
			Map<String, Object> serversList = null;
			List<String> daoList = null, masterList = null, slaveList = null;
			int i = 0;
			for (Entry<String, Object> entry : databaseMap.entrySet()) {
				databaseName = entry.getKey();
				obj = (Map<String, Object>) entry.getValue();
				poolName = obj.get("poolName");
				
				datasourceBean = new DatasourceConfigBean();
				if (poolName != null) {
					datasourceBean.setPoolName((String) poolName);
				}
				connectionParam = obj.get("connectionParam");
				if (connectionParam != null) {
					datasourceBean.setConnectionParam((String) connectionParam);
				}
				datasourceBean.setDatabaseName(databaseName);
				databaseType = obj.get("databaseType");
				if (databaseType != null) {
					datasourceBean.setDatabaseType((String) databaseType);
				}
				driverClassName = obj.get("driverClassName");
				if (driverClassName != null) {
					datasourceBean.setDriverClassName((String) driverClassName);
				}
				datasourceBean.setUsername((String) obj.get("username"));
				datasourceBean.setPassword(obj.get("password").toString());
				serversList = (Map<String, Object>) obj.get("servers");
				if (MapUtils.isEmpty(serversList)) {
					log.error("init database error. with servers is empty.");
					throw new DynamicDataSourceRouteException("servers is empty with databaseName is: " + datasourceBean.getDatabaseName());
				}
				masterList = (List<String>) serversList.get("master");
				slaveList = (List<String>) serversList.get("slave");
				
				if (CollectionUtils.isEmpty(masterList)) {
					log.error("init database error. with masterUrls is empty.");
					throw new DynamicDataSourceRouteException("masterUrls is empty. with databaseName is: " + datasourceBean.getDatabaseName());
				}
				datasourceBean.setMasterIpPort(masterList);
				if (CollectionUtils.isNotEmpty(slaveList)) {
					datasourceBean.setSlaveIpPort(slaveList);
				}
				daoList = (List<String>) obj.get("daos");
				if (CollectionUtils.isNotEmpty(daoList)) {
					datasourceBean.setDaos(daoList);
				}
				
				datasourceBean.setDefaultDatabase(i == 0);
				connectionPool = ConnectionPoolFactoryEnum.getConnectionPoolFactory(datasourceBean.getPoolName());
				
				
				dynamicDatasourceBean = connectionPool.getDynamicDatasource(datasourceBean);
				if (dynamicDatasourceBean == null || CollectionUtils.isEmpty(dynamicDatasourceBean.getMasterDatasource())) {
					log.error("init database error. with masterUrls is empty.");
					throw new DynamicDataSourceRouteException("masterUrls is empty. with databaseName is: " + datasourceBean.getDatabaseName());
				}
				
				//设置master
				for (int j = 0; j < dynamicDatasourceBean.getMasterDatasource().size(); j++) {
					targetDataSources.put(buildMasterDatasourceKey(databaseName, j), dynamicDatasourceBean.getMasterDatasource().get(j));
				}
				//设置master有几个数据源
				databaseIPCountMap.put(buildMasterDatasourceKey(databaseName, -1), dynamicDatasourceBean.getMasterDatasource().size());
				
				//设置slave
				if (CollectionUtils.isNotEmpty(dynamicDatasourceBean.getSlaveDatasource())) {
					for (int j = 0; j < dynamicDatasourceBean.getSlaveDatasource().size(); j++) {
						targetDataSources.put(buildSlaveDatasourceKey(databaseName, j), dynamicDatasourceBean.getSlaveDatasource().get(j));
					}
					//设置slave有几个数据源
					databaseIPCountMap.put(buildSlaveDatasourceKey(databaseName, -1), dynamicDatasourceBean.getSlaveDatasource().size());
				}
				
				//默认数据源
				if (datasourceBean.isDefaultDatabase()) {
					DEFAULT_DATABASE_NAME = databaseName;
				}
				
				//处理dao
				dealDAOS(datasourceBean);
				i++;
			}
			super.setTargetDataSources(targetDataSources);
			super.afterPropertiesSet();
		} catch (Exception e) {
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
	 * @param index
	 * @return
	 */
	private String buildMasterDatasourceKey(String databaseName, int index) {
		StringBuilder sb = new StringBuilder(MASTER_DS_KEY_PREX).append(databaseName);
		if (index >= 0) {
			sb.append("_").append(index);
		}
		return sb.toString();
	}
	
	/**
	 * 获取从数据源的key
	 * @param databaseName
	 * @param index
	 * @return
	 */
	private String buildSlaveDatasourceKey(String databaseName, int index) {
		StringBuilder sb = new StringBuilder(SLAVE_DS_KEY_PREX).append(databaseName);
		if (index >= 0) {
			sb.append("_").append(index);
		}
		return sb.toString();
	}

	public Map<String, Object> getDatabasesMap() {
		return databasesMap;
	}

	public void setDatabasesMap(Map<String, Object> databasesMap) {
		this.databasesMap = databasesMap;
	}

}
