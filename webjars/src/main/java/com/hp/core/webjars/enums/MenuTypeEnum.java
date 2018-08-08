package com.hp.core.webjars.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 菜单类型枚举
 * @author hp
 * @date 2014-03-11
 */
public enum MenuTypeEnum {

	/**
	 * 根节点
	 */
	ROOT(1, "根节点"),

	/**
	 * 子菜单
	 */
	CHILD(2, "子节点"),

	
	/**
	 * 按钮
	 */
	BUTTON(3, "按钮");

	private MenuTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private int value;
	private String name;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 返回json格式的数据
	 * @return 返回json格式的数据
	 */
	public static JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (int i = 0; i < values().length; i++) {
			json = new JSONObject();
			json.put("text", values()[i].getName());
			json.put("value", values()[i].getValue());
			arr.add(json);
		}
		return arr;
	}
}

