/**
 * 
 */
package com.hp.core.mybatis.datasource;

import com.hp.core.mybatis.bean.DatasourceConfigBean;

/**
 * @author huangping
 * 2018年4月2日
 */
public interface AbstDatabase {

	/**
	 * 获取数据库连接url
	 * @param ipPort
	 * @param databaseName
	 * @param params
	 * @return
	 */
	public String getConnectionUrl(String ipPort, String databaseName, String... params);
	
	/**
	 * 获取数据库连接的驱动
	 * @param bean
	 * @return
	 */
	public String getDriverClassName(DatasourceConfigBean bean);
	
	/**
	 * 根据数据库字段类型，转换为java字段类型
	 * @param jdbcType
	 * @return
	 */
	public String dbTypeToJavaType(String jdbcType);
	
	/**
	 * 获取检查sql语句
	 * @return
	 */
	public String getCheckSql();
}
