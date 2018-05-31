/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

import com.hp.core.mybatis.annotation.ForceMaster;
import com.hp.core.mybatis.annotation.ForceSlave;
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
	
	/**
	 * 存放方法是否有注解的map
	 */
	private static Map<String, Boolean> methodAnnotationMap = new HashMap<>();
	
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
			//如果没有获取到拦截信息，则取主数据库
			log.warn("determineCurrentLookupKey error. with daoInfo is empty.");
			//return null;
			//new一个对象出来
			daoInfo = new DAOInterfaceInfoBean();
		}
		
		//按照dao的className，从数据源中获取数据源
		String mapperNamespace = daoInfo.getMapperNamespace();
		String databaseName = databaseNameMap.get(mapperNamespace);
		if (StringUtils.isEmpty(databaseName)) {
			//如果没有，则使用默认数据源
			databaseName = DEFAULT_DATABASE_NAME;
		}
		
		String result = getDatasourceByKey(databaseName, getFormMaster(daoInfo));
		log.debug("-------select route datasource with statementId={} and result is {}", (daoInfo.getMapperNamespace() + "." + daoInfo.getStatementId()), result);
		return result;
	}
	
	private boolean getFormMaster(DAOInterfaceInfoBean daoInfo) {
		String key = daoInfo.getMapperNamespace() + "." + daoInfo.getStatementId();
		//查询缓存
		Boolean bool = methodAnnotationMap.get(key);
		if (bool != null) {
			return bool.booleanValue();
		}
		
		boolean b = getFormMasterFromAnnotation(daoInfo);
		methodAnnotationMap.put(key, new Boolean(b));
		return b;
	}
	
	/**
	 * 查询是从哪个数据源
	 */
	private boolean getFormMasterFromAnnotation(DAOInterfaceInfoBean daoInfo) {
		//先判断方法上面
		ForceMaster forceMaster = getAnnotationByMethod(daoInfo.getMethod(), ForceMaster.class);
		if (forceMaster != null) {
			//方法上有注解，直接返回
			return true;
		}
		ForceSlave forceSlave = getAnnotationByMethod(daoInfo.getMethod(), ForceSlave.class);
		if (forceSlave != null) {
			//方法上有注解，直接返回
			return false;
		}
		
		//再判断接口上面
		forceMaster = getAnnotationByClass(daoInfo.getClassName(), ForceMaster.class);
		if (forceMaster != null) {
			//方法上有注解，直接返回
			return true;
		}
		forceSlave = getAnnotationByClass(daoInfo.getClassName(), ForceSlave.class);
		if (forceSlave != null) {
			//方法上有注解，直接返回
			return false;
		}
		
		//根据方法名称去判断
		boolean fromMaster = false;
		//获取用户执行的sql方法名
		String statementId = daoInfo.getStatementId();
		if (StringUtils.isEmpty(statementId)) {
			//没有获取到方法，走master
			return true;
		}
		statementId = statementId.toLowerCase();
		if (select.matcher(statementId).matches()) {
			//使用slave数据源
			fromMaster = false;
		} else if (update.matcher(statementId).matches() || insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			//使用master数据源
			fromMaster = true;
		} else {
			//如果statemenetId不符合规范，则告警，并且使用master数据源
			log.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ", daoInfo.getMapperNamespace(), daoInfo.getStatementId());
			fromMaster = true;
		}
		return fromMaster;
	}
	
	/**
	 * 查询方法上是否有注解
	 * @param method
	 * @param annotationType
	 * @return
	 */
	private <T extends Annotation> T getAnnotationByMethod(Method method, Class<T> annotationType) {
		if (method == null) {
			return null;
		}
		T a = method.getAnnotation(annotationType);
		if (a == null) {
			return null;
		}
		return a;
	}
	
	/**
	 * 查询接口上是否有注解
	 * @param clazz
	 * @param annotationType
	 * @return
	 */
	private <T extends Annotation> T getAnnotationByClass(Class<?> clazz, Class<T> annotationType) {
		if (clazz == null) {
			return null;
		}
		T a = clazz.getAnnotation(annotationType);
		if (a == null) {
			return null;
		}
		return a;
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
				throw new DataSourceNotFoundException(databaseName);
			}
		} else {
			datasourceKey = buildSlaveDatasourceKey(databaseName, -1);
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//没有配置从库，则路由到主库
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
			
			Map<String, Object> obj = null;
			Map<String, Object> databaseMap = (Map<String, Object>) databasesMap.get("datasources");
			DatasourceConfigBean datasourceBean = null;
			Map<String, Object> serversList = null;
			List<String> daoList = null, masterList = null, slaveList = null;
			int i = 0;
			for (Entry<String, Object> entry : databaseMap.entrySet()) {
				databaseName = entry.getKey();
				obj = (Map<String, Object>) entry.getValue();
				
				datasourceBean = new DatasourceConfigBean();
				//设置数据源基础信息
				dealDatasourceBaseInfo(obj, datasourceBean);
				datasourceBean.setDatabaseName(databaseName);
				
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
	 * 设置数据源基础的信息
	 * @param map
	 * @param bean
	 */
	private void dealDatasourceBaseInfo(Map<String, Object> map, DatasourceConfigBean bean) {
		Object maxTotal = map.get("maxTotal");
		Object maxIdle = map.get("maxIdle");
		Object initialSize = map.get("initialSize");
		Object maxWaitMillis = map.get("maxWaitMillis");
		Object timeBetweenEvictionRunsMillis = map.get("timeBetweenEvictionRunsMillis");
		Object testWhileIdle = map.get("testWhileIdle");
		Object numTestsPerEvictionRun = map.get("numTestsPerEvictionRun");
		Object poolName = map.get("poolName");
		Object connectionParam = map.get("connectionParam");
		Object databaseType = map.get("databaseType");
		Object driverClassName = map.get("driverClassName");
		if (maxTotal != null) {
			bean.setMaxTotal((int) maxTotal);
		}
		if (maxIdle != null) {
			bean.setMaxIdle((int) maxIdle);
		}
		if (initialSize != null) {
			bean.setInitialSize((int) initialSize);
		}
		if (maxWaitMillis != null) {
			bean.setMaxWaitMillis((int) maxWaitMillis);
		}
		if (timeBetweenEvictionRunsMillis != null) {
			bean.setTimeBetweenEvictionRunsMillis((int) timeBetweenEvictionRunsMillis);
		}
		if (testWhileIdle != null) {
			bean.setTestWhileIdle((boolean) testWhileIdle);
		}
		if (numTestsPerEvictionRun != null) {
			bean.setNumTestsPerEvictionRun((int) numTestsPerEvictionRun);
		}
		if (poolName != null) {
			bean.setPoolName((String) poolName);
		}
		if (connectionParam != null) {
			bean.setConnectionParam((String) connectionParam);
		}
		if (databaseType != null) {
			bean.setDatabaseType((String) databaseType);
		}
		if (driverClassName != null) {
			bean.setDriverClassName((String) driverClassName);
		}
		bean.setUsername(map.get("username").toString());
		bean.setPassword(map.get("password").toString());
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
