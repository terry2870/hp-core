/**
 * 
 */
package com.hp.core.mybatis.enums;
/**
 * @author huangping
 * 2018年4月2日
 */

import org.apache.commons.lang3.StringUtils;

import com.hp.core.mybatis.datasource.AbstConnectionPoolFactory;
import com.hp.core.mybatis.datasource.impl.DBCPConnectionPoolFactoryImpl;

public enum ConnectionPoolFactoryEnum {

	DBCP,
	C3P0,
	;
	
	/**
	 * 获取连接池的实现类
	 * @param dsName
	 * @return
	 */
	public static AbstConnectionPoolFactory getConnectionPoolFactory(String poolName) {
		if (StringUtils.isEmpty(poolName)) {
			poolName = DBCP.toString();
		}
		if (poolName.equals(DBCP.toString())) {
			return new DBCPConnectionPoolFactoryImpl();
		}
		
		return new DBCPConnectionPoolFactoryImpl();
	}
	
}
