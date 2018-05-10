package com.hp.core.common.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum StatusEnum {

	OPEN(1, "正常"),
	DELETE(2, "已删除");
	
	private int value;
	private String text;
	
	private StatusEnum(int value, String text) {
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
		for (StatusEnum e : values()) {
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
	public static StatusEnum getEnumByValue(int value) {
		for (StatusEnum e : values()) {
			if (value == e.getValue()) {
				return e;
			}
		}
		return null;
	}
	
	public static String getTextByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (StatusEnum e : values()) {
			if (value.byteValue() == e.getValue()) {
				return e.getText();
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
