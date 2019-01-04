/**
 * 
 */
package com.hp.core.database.bean;

import java.util.List;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.database.enums.ConnectionPoolFactoryEnum;
import com.hp.core.database.enums.DatabaseTypeEnum;

/**
 * 动态数据库基础bean
 * @author huangping
 * 2018年4月1日 下午10:57:45
 */
public class DatasourceConfigBean extends BaseBean {

	private static final long serialVersionUID = 381003465978823718L;

	private String poolName = ConnectionPoolFactoryEnum.DBCP.name();
	
	//数据库类型
	private String databaseType = DatabaseTypeEnum.MYSQL.name();
	
	//别名（暂时用不到）
	private String alias;
	
	//数据库驱动
	private String driverClassName;
	
	//数据库连接串
	private String connectionParam = "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&useInformationSchema=true";
	
	//数据库名称
	private String databaseName;
	
	//主数据库的ip端口号
	private List<String> masterIpPort;
	
	//从数据库的ip端口号
	private List<String> slaveIpPort;
	
	//数据库用户名
	private String username;
	
	//数据库密码
	private String password;
	
	//是否是默认
	private boolean defaultDatabase = false;
	
	//对应的dao
	private List<String> daos;
	
	private int maxTotal = 50;
	private int maxIdle = 5;
	private int initialSize = 5;
	private long maxWaitMillis = 5000;
	private long timeBetweenEvictionRunsMillis = -1L;
	private boolean testWhileIdle = true;
	private int numTestsPerEvictionRun = 3;
	private String validationQuery = "select 1";
	
	
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public List<String> getDaos() {
		return daos;
	}
	public void setDaos(List<String> daos) {
		this.daos = daos;
	}
	public boolean isDefaultDatabase() {
		return defaultDatabase;
	}
	public void setDefaultDatabase(boolean defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getConnectionParam() {
		return connectionParam;
	}
	public void setConnectionParam(String connectionParam) {
		this.connectionParam = connectionParam;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public List<String> getMasterIpPort() {
		return masterIpPort;
	}
	public void setMasterIpPort(List<String> masterIpPort) {
		this.masterIpPort = masterIpPort;
	}
	public List<String> getSlaveIpPort() {
		return slaveIpPort;
	}
	public void setSlaveIpPort(List<String> slaveIpPort) {
		this.slaveIpPort = slaveIpPort;
	}
}
