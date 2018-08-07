/**
 * 
 */
package com.hp.core.webjars.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author huangping
 * 2016年8月23日 下午11:22:24
 */
public enum CodeEnum {

	
	SUCCESS(200, "成功"),
	ERROR(201, "失败"),
	DATABASE_TIME_OUT(900, "数据库查询超时"),
	SESSION_TIME_OUT(901, "登录超时"),
	NO_RIGHT(902, "没有权限")
	;
	
	private int code;
	private String message;
	
	private CodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static String getMessageByCode(int code) {
		for (CodeEnum e : values()) {
			if (code == e.getCode()) {
				return e.getMessage();
			}
		}
		return null;
	}
	
	/**
	 * 返回json格式的数据
	 * @param value
	 * @return
	 */
	public static JSONArray toJSON(int value) {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (CodeEnum e : values()) {
			json = new JSONObject();
			json.put("value", e.getClass());
			json.put("text", e.getMessage());
			arr.add(json);
		}
		return arr;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
