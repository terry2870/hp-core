/**
 * 
 */
package com.hp.core.mybatis.datasource;
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
}
