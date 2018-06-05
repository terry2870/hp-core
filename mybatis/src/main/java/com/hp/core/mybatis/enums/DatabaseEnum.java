/**
 * 
 */
package com.hp.core.mybatis.enums;

import com.hp.core.mybatis.datasource.AbstDatabase;
import com.hp.core.mybatis.datasource.impl.MysqlDatabaseImpl;

/**
 * @author huangping
 * 2018年4月2日
 */
public enum DatabaseEnum {

	MYSQL,
	ORACLE;
	
	/**
	 * 获取数据库连接url
	 * @param databaseType
	 * @return
	 */
	public static AbstDatabase getDatabaseByDatabaseType(String databaseType) {
		if (MYSQL.toString().equalsIgnoreCase(databaseType)) {
			return new MysqlDatabaseImpl();
		}
		return null;
	}
}
