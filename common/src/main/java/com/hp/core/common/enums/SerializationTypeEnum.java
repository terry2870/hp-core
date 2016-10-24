/**
 * 
 */
package com.hp.core.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ping.huang
 * 2016年10月15日
 */
public enum SerializationTypeEnum {

	
	PROTOSTUFF(1, "Protostuff"),
	LINEBASED(2, "LineBased");
	
	private int type;
	private String name;
	
	private SerializationTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	
	/**
	 * 根据type，获取枚举
	 * @param type
	 * @return
	 */
	public static SerializationTypeEnum getEnumByType(int type) {
		for (SerializationTypeEnum e : values()) {
			if (e.getType() == type) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 根据name，获取枚举
	 * @param type
	 * @return
	 */
	public static SerializationTypeEnum getEnumByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		for (SerializationTypeEnum e : values()) {
			if (name.equalsIgnoreCase(e.getName())) {
				return e;
			}
		}
		return null;
	}
}
