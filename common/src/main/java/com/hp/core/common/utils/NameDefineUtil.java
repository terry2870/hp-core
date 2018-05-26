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

}
