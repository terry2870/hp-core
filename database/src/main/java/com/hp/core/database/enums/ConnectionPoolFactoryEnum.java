/**
 * 
 */
package com.hp.core.database.enums;
/**
 * @author huangping
 * 2018年4月2日
 */

import org.apache.commons.lang3.StringUtils;

import com.hp.core.database.datasource.AbstConnectionPoolFactory;
import com.hp.core.database.datasource.impl.DBCPConnectionPoolFactoryImpl;
import com.hp.core.database.datasource.impl.DruidConnectionPoolFactoryImpl;

public enum ConnectionPoolFactoryEnum {

	DBCP,
	C3P0,
	DRUID,
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
		
		ConnectionPoolFactoryEnum e = ConnectionPoolFactoryEnum.valueOf(poolName);
		if (e == null) {
			poolName = DBCP.toString();
		}
		
		AbstConnectionPoolFactory bean = null;
		switch (e) {
		case DBCP:
			bean =  new DBCPConnectionPoolFactoryImpl();
			break;
		case C3P0:
			
			break;
		case DRUID:
			bean = new DruidConnectionPoolFactoryImpl();
			break;
		default:
			break;
		}
		
		return bean;
	}
	
}
