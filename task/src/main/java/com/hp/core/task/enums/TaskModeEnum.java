/**
 * 
 */
package com.hp.core.task.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * 
 * @author ping.huang
 * 2016年5月21日
 */
public enum TaskModeEnum {

	SINGLE(1, "单次执行"),
	LOOP(2, "循环执行");
	
	private int value;
	private String name;
	
	private TaskModeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * 获取枚举的列表
	 * @return
	 */
	public static JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (TaskModeEnum e : values()) {
			json = new JSONObject();
			json.put("text", e.getName());
			json.put("value", e.getValue());
			arr.add(json);
		}
		return arr;
	}
	
	public static String getNameByValue(int value) {
		for (TaskModeEnum e : values()) {
			if (value == e.getValue()) {
				return e.getName();
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
