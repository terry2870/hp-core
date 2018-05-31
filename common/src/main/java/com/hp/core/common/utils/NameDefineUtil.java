/**
 * 
 */
package com.hp.core.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 名称规则的互相转换
 * 
 * @author huangping 2018年5月23日
 */
public class NameDefineUtil {

	/**
	 * 驼峰转下划线
	 * 
	 * @param name
	 * @return
	 */
	public static String camelCaseToUnderline(String name) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		char[] chars = name.trim().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			c = chars[i];
			if (Character.isUpperCase(c) && i > 0) {
				sb.append("_");
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 下划线转驼峰
	 * 第一个字母大写
	 * @param name
	 * @return
	 */
	public static String underlineToCamelCase(String name) {
		return underlineToCamelCase(name, true);
	}
	
	/**
	 * 下划线转驼峰
	 * @param name
	 * @param firstLower		第一个字母是否大写
	 * @return
	 */
	public static String underlineToCamelCase(String name, boolean firstUpper) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		name = name.trim().toLowerCase();
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		char[] chars = name.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			c = chars[i];
			if (i == 0) {
				sb.append(firstUpper ? Character.toUpperCase(c) : c);
				continue;
			}
			if (c == '_') {
				sb.append(Character.toUpperCase(chars[i + 1]));
				i++;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
