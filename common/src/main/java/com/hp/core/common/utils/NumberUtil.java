/**
 * 
 */
package com.hp.core.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author huangping
 * Jul 25, 2019
 */
public class NumberUtil {

	/**
	 * 为空或为0
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Integer value) {
		return value == null || NumberUtils.INTEGER_ZERO.equals(value);
	}
	
	/**
	 * 不为空，且不为0
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Integer value) {
		return !isEmpty(value);
	}
	
	/**
	 * 取int值
	 * @param value
	 * @return
	 */
	public static int defaultIntValue(Integer value) {
		return defaultIntValue(value, NumberUtils.INTEGER_ZERO.intValue());
	}
	
	/**
	 * 取int值
	 * @param value
	 * @param dftValue
	 * @return
	 */
	public static int defaultIntValue(Integer value, int dftValue) {
		if (value == null) {
			return dftValue;
		}
		return value.intValue();
	}
	
	/**
	 * hashMap的  hash方法
	 * @param key
	 * @return
	 */
	public static int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
}
