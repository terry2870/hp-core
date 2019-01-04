/**
 * 
 */
package com.hp.core.database.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huangping
 * 2019年1月2日
 */
public enum DirectionEnum {

	ASC(1),
	DESC(2);
	
	private Integer value;
	
	/**
	 * 根据value，查询枚举
	 * @param value
	 * @return
	 */
	public static DirectionEnum getDirectionEnumByValue(Integer value) {
		if (value == null || value.intValue() == 0) {
			return null;
		}
		for (DirectionEnum e : values()) {
			if (value.equals(e.getValue())) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 根据文字，查询枚举
	 * @param text
	 * @return
	 */
	public static DirectionEnum getDirectionEnumByText(String text) {
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		for (DirectionEnum e : values()) {
			if (text.equalsIgnoreCase(e.toString())) {
				return e;
			}
		}
		return null;
	}
		
	private DirectionEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
