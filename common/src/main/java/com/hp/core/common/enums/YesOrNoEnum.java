package com.hp.core.common.enums;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum YesOrNoEnum {

	
	YES(NumberUtils.INTEGER_ONE, "是"),
	NO(NumberUtils.INTEGER_ZERO, "否");
	
	public Integer getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	private Integer value;
	private String text;

	private YesOrNoEnum(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 是
	 * @param value
	 * @return
	 */
	public static boolean yes(Integer value) {
		if (value == null) {
			return false;
		}
		return YES.getValue().equals(value);
	}
	
	/**
	 * 否
	 * @param value
	 * @return
	 */
	public static boolean no(Integer value) {
		return !yes(value);
	}
	
	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (YesOrNoEnum e : values()) {
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
	public static YesOrNoEnum getEnumByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (YesOrNoEnum e : values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 根据value，获取text
	 * @param value
	 * @return
	 */
	public static String getTextByValue(Integer value) {
		if (value == null) {
			return null;
		}
		YesOrNoEnum e = getEnumByValue(value.intValue());
		if (e != null) {
			return e.getText();
		} else {
			return null;
		}
	}
	
}
