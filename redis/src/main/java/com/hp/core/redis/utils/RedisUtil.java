/**
 * 
 */
package com.hp.core.redis.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author ping.huang
 * 2016年7月1日
 */
public class RedisUtil {

	static Logger log = LoggerFactory.getLogger(RedisUtil.class);
	
	/**
	 * 序列化value值
	 * @param value
	 * @return
	 */
	public static String value2String(Object value) {
		String v = null;
		if (value == null) {
			return null;
		}
		try {
			if (value instanceof String) {
				v = (String) value;
			} else if (value instanceof Number) {
				v = value.toString();
			} else {
				v = JSON.toJSONString(value);
			}
		} catch (Exception e) {
			log.error("value2String error with value={}", value, e);
		}
		return v;
	}
	
	/**
	 * 反序列化value值
	 * @param value
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T string2Value(String value, Class<T> clazz) {
		if (value == null) {
			return null;
		}
		try {
			if (clazz.getName().equalsIgnoreCase("java.lang.String")) {
				return (T) value;
			}
			return (T) JSON.parseObject(value, clazz);
		} catch (Exception e) {
			log.error("string2Value error with value={}, clazz={}", value, clazz.getName(), e);
		}
		return null;
	}
	
	/**
	 * 转换
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static <T> Set<T> convertSet(Set<String> value, Class<T> clazz) {
		if (CollectionUtils.isEmpty(value)) {
			log.warn("convertSet error. with value is empty.");
			return null;
		}
		Set<T> set = new HashSet<>(value.size());
		for (String str : value) {
			set.add(string2Value(str, clazz));
		}
		return set;
	}
	
	/**
	 * 转换
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertList(List<String> value, Class<T> clazz) {
		if (CollectionUtils.isEmpty(value)) {
			log.warn("convertList error. with value is empty.");
			return null;
		}
		List<T> list = new ArrayList<>(value.size());
		for (String str : value) {
			list.add(string2Value(str, clazz));
		}
		return list;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("str", "1234");
		map.put("id", 222);
		System.out.println(value2String(map));
		
		System.out.println(value2String(123));
	}
}
