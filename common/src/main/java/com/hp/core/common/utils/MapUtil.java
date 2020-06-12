package com.hp.core.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

/**
 * map的工具类
 * 
 */
public class MapUtil {
	
	public static final <K, V> Map<K, V> list2map(Iterable<V> values, Function<K, ? super V> keyFunction) {
		return list2Map(values, keyFunction);
	}
	
	/**
	 * Iterator 转换成map
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map的value
	 * @param values		传入的list
	 * @param keyFunction	转换函数
	 * @return
	 */
	public static final <K, V> Map<K, V> list2Map(Iterable<V> values, Function<K, ? super V> keyFunction) {
		return list2Map(values, new FunctionExPlus<V, K, V>() {
			@Override
			public boolean check(V input) {
				return keyFunction.check(input);
			}
			@Override
			public K applyKey(V input) {
				return keyFunction.apply(input);
			}

			@Override
			public V applyValue(V input) {
				return input;
			}
		});
	}

	/**
	 * Iterator 转换成map
	 * @param <T>			传入的list里面的对象
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map的value
	 * @param values		传入的list
	 * @param keyFunction	转换函数
	 * @return
	 */
	public static final <T, K, V> Map<K, V> list2Map(Iterable<T> values, FunctionExPlus<T, K, V> keyFunction) {
		if (values == null || Iterables.isEmpty(values) || null == keyFunction) {
			return Maps.newHashMap();
		}
		Map<K, V> builder = new HashMap<K, V>();
		Iterator<T> iterator = values.iterator();
		K key = null;
		T value = null;
		while (iterator.hasNext()) {
			value = iterator.next();
			if (value == null) {
				continue;
			}
			if (!keyFunction.check(value)) {
				continue;
			}
			key = keyFunction.applyKey(value);
			if (key == null) {
				continue;
			}
			builder.put(key, keyFunction.applyValue(value));
		}
		return builder;
	}
	
	/**
	 * list格式的，转为map<key, List>
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map里面的list的值对象
	 * @param values		输入的list
	 * @param keyFunction	转换函数
	 * @return
	 */
	public static final <K, V> Map<K, List<V>> transformListMap(Iterable<V> values, Function<K, V> keyFunction) {
		return transformListMap(values, keyFunction, 0);
	}
	
	/**
	 * list格式的，转为map<key, List>
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map的value
	 * @param values		传入的list
	 * @param keyFunction	转换函数
	 * @param maxNum		list中最多取多少个（0，为全部）
	 * @return
	 */
	public static final <K, V> Map<K, List<V>> transformListMap(Iterable<V> values, Function<K, V> keyFunction, int maxNum) {
		return transformListMap(values, new FunctionExPlus<V, K, V>() {
			@Override
			public boolean check(V input) {
				return keyFunction.check(input);
			}
			@Override
			public K applyKey(V input) {
				return keyFunction.apply(input);
			}

			@Override
			public V applyValue(V input) {
				return input;
			}
		}, maxNum);
	}
	
	/**
	 * list格式的，转为map<key, List>
	 * @param <T>			传入的list里面的对象
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map的value
	 * @param values		传入的list
	 * @param keyFunction	转换函数
	 * @return
	 */
	public static final <T, K, V> Map<K, List<V>> transformListMap(Iterable<T> values, FunctionExPlus<T, K, V> keyFunction) {
		return transformListMap(values, keyFunction, 0);
	}
	
	/**
	 * list格式的，转为map<key, List>
	 * @param <T>			传入的list里面的对象
	 * @param <K>			返回的map的key
	 * @param <V>			返回的map的value
	 * @param values		传入的list
	 * @param keyFunction	转换函数
	 * @param maxNum		list中最多取多少个（0，为全部）
	 * @return
	 */
	public static final <T, K, V> Map<K, List<V>> transformListMap(Iterable<T> values, FunctionExPlus<T, K, V> keyFunction, int maxNum) {
		if (Iterables.isEmpty(values) || null == keyFunction) {
			return Maps.newHashMap();
		}
		Map<K, List<V>> builder = new LinkedHashMap<>();
		Iterator<T> iterator = values.iterator();
		K key = null;
		V value = null;
		T t = null;
		List<V> newArrayList = null;
		while (iterator.hasNext()) {
			t = iterator.next();
			key = keyFunction.applyKey(t);
			if (!keyFunction.check(t)) {
				continue;
			}
			value = keyFunction.applyValue(t);
			if (builder.containsKey(key)) {
				if (maxNum != 0 && builder.get(key).size() >= maxNum) {
					//超过，则丢弃
					continue;
				}
				builder.get(key).add(value);
			} else {
				newArrayList = new ArrayList<>();
				newArrayList.add(value);
				builder.put(key, newArrayList);
			}
		}
		return builder;
	}

	/**
	 * 回调方法
	 * @author huangping
	 * Apr 3, 2019
	 * @param <K>	返回的map的key
	 * @param <V>	返回的map的value
	 */
	public static interface Function<K, V> {
		/**
		 * 检查是否加入map
		 * @param input
		 * @return
		 */
		default boolean check(V input) {
			return true;
		}
		/**
		 * 获取key值
		 * @param input
		 * @return
		 */
		K apply(V input);
	}

	/**
	 * 回调方法
	 * @author huangping
	 * Apr 3, 2019
	 * @param <T>	输入的对象
	 * @param <K>	返回的map的key
	 * @param <V>	返回的map的value
	 */
	public static interface FunctionExPlus<T, K, V> {
		/**
		 * 检查是否加入map
		 * @param input
		 * @return
		 */
		default boolean check(T input) {
			return true;
		}
		
		/**
		 * 获取key
		 * @param input
		 * @return
		 */
		K applyKey(T input);
		
		/**
		 * 获取value
		 * @param input
		 * @return
		 */
		V applyValue(T input);
	}

	public static Map<String,String> objectToMap(Object obj) {
		Map<String, String> map = new HashMap<>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		Object o = null;
		try {
			for (Field field : declaredFields) {
				field.setAccessible(true);
				// 过滤内容为空的
				o = field.get(obj);
				if (o == null) {
					continue;
				}
				map.put(field.getName(), o.toString());
			}
		} catch (Exception e) {
			return null;
		}

		return map;
	}
	
	/**
	 * 从map中，返回list
	 * @param <K>
	 * @param <V>
	 * @param list
	 * @param map
	 * @return
	 */
	public static <K, V> List<V> map2List(List<K> list, Map<K, V> map) {
		if (CollectionUtils.isEmpty(list) || MapUtils.isEmpty(map)) {
			return null;
		}
		
		List<V> l = new ArrayList<>();
		V v = null;
		for (K k : list) {
			v = map.get(k);
			if (v != null) {
				l.add(v);
			}
		}
		return l;
	}

}