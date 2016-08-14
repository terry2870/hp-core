package com.hp.core.common.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum CharSetEnum {

	UTF_8("UTF-8", "UTF-8"),
	GBK("GBK", "GBK"),
	GB2312("GB2312", "GB2312"),
	ISO_8859_1("ISO-8859-1", "ISO-8859-1");

	private String value;
	private String text;

	private CharSetEnum(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 返回json格式的数据
	 * 
	 * @return 返回json格式的数据
	 */
	public static JSONArray toJson() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		for (int i = 0; i < values().length; i++) {
			json = new JSONObject();
			json.put("text", values()[i].getText());
			json.put("value", values()[i].toString());
			arr.add(json);
		}
		return arr;
	}

	public String getText() {
		return text;
	}
}
