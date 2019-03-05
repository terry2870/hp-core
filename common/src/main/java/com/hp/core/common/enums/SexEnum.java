package com.hp.core.common.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum SexEnum {

	MALE(1, "男"),
	FEMALE(2, "女");
	
	private Integer value;
	private String text;
	
	private SexEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (SexEnum e : values()) {
			json = new JSONObject();
			json.put("value", e.getValue());
			json.put("text", e.getText());
			arr.add(json);
		}
		return arr;
	}
	
	/**
	 * 根据value值，获取枚举对象
	 * @param value
	 * @return
	 */
	public static SexEnum getEnumByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (SexEnum e : values()) {
			if (value == e.getValue()) {
				return e;
			}
		}
		return null;
	}
	
	public static String getTextByValue(Integer value) {
		SexEnum e = getEnumByValue(value);
		return e == null ? "" : e.getText();
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
