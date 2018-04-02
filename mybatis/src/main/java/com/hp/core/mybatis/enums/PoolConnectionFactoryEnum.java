/**
 * 
 */
package com.hp.core.mybatis.enums;
/**
 * @author huangping
 * 2018年4月2日
 */

import org.apache.commons.lang3.StringUtils;

import com.hp.core.mybatis.datasource.AbstPoolConnectionFactory;
import com.hp.core.mybatis.datasource.impl.DBCPPoolConnectionFactoryImpl;

public enum PoolConnectionFactoryEnum {

	DBCP,
	C3P0,
	;
	
	/**
	 * 获取连接池的实现类
	 * @param dsName
	 * @return
	 */
	public static AbstPoolConnectionFactory getPoolConnectionFactory(String poolName) {
		if (StringUtils.isEmpty(poolName)) {
			poolName = DBCP.toString();
		}
		if (poolName.equals(DBCP.toString())) {
			return new DBCPPoolConnectionFactoryImpl();
		}
		
		return new DBCPPoolConnectionFactoryImpl();
	}
	
}
