package com.my.core.common.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author huangping<br />
 * 2013-3-19
 */
public enum DatabaseTypeEnum {
	
	ORACLE,
	MYSQL,
	SQLSERVER,
	DB2;
	
	/**
	 * 返回json格式的数据
	 * @return 返回json格式的数据
	 */
	public static JSONArray toJson() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (int i = 0; i < values().length; i++) {
			json = new JSONObject();
			json.put("value", values()[i].toString());
			json.put("text", values()[i].toString());
			arr.add(json);
		}
		return arr;
	}

}
