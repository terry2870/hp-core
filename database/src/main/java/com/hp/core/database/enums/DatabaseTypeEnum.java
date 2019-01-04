/**
 * 
 */
package com.hp.core.database.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hp.core.database.datasource.AbstDatabase;
import com.hp.core.database.datasource.impl.MysqlDatabaseImpl;

/**
 * @author huangping
 * 2018年4月2日
 */
public enum DatabaseTypeEnum {

	MYSQL(1),
	ORACLE(2);
	
	private Integer value;
	
	private DatabaseTypeEnum(Integer value) {
		this.value = value;
	}
	
	/**
	 * 返回json格式的数据
	 * @return 返回json格式的数据
	 */
	public static JSONArray toJson() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (DatabaseTypeEnum e : values()) {
			json = new JSONObject();
			json.put("value", e.getValue());
			json.put("text", e.toString());
			arr.add(json);
		}
		return arr;
	}
	
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

	public Integer getValue() {
		return value;
	}
}
