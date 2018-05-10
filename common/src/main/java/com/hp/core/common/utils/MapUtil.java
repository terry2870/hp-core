package com.hp.core.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

/**
 * map的工具类
 * 
 * @author MALI
 */
public class MapUtil {
	/**
	 * Iterator 转换成map
	 * 
	 * @param values
	 * @param keyFunction
	 * @return
	 */
	public static final <K, V> Map<K, V> list2map(Iterable<V> values, Function<K, ? super V> keyFunction) {
		if (Iterables.isEmpty(values) || null == keyFunction) {
			return Maps.newHashMap();
		}

		Map<K, V> builder = new HashMap<K, V>();
		Iterator<V> iterator = values.iterator();
		while (iterator.hasNext()) {
			V value = iterator.next();
			builder.put(keyFunction.apply(value), value);
		}
		return builder;
	}

	/**
	 * Iterator 转换成map
	 * T-原value值
	 * K-新的key值
	 * V-新的value值
	 * @param values
	 * @param keyFunction
	 * @return
	 */
	public static final <T, K, V> Map<K, V> list2map(Iterable<T> values, FunctionExPlus<T, K, V> keyFunction) {
		if (Iterables.isEmpty(values) || null == keyFunction) {
			return Maps.newHashMap();
		}
		Map<K, V> builder = new HashMap<>();
		Iterator<T> iterator = values.iterator();
		while (iterator.hasNext()) {
			T value = iterator.next();
			builder.put(keyFunction.applyKey(value), keyFunction.applyValue(value));
		}
		return builder;
	}

	/**
	 * Iterator 转为map（value为 list）
	 * @param values
	 * @param keyFunction
	 * @return
	 */
	public static final <T, K, V> Map<K, List<V>> transformListMap(Iterable<T> values, FunctionExPlus<T, K, V> keyFunction) {
		if (Iterables.isEmpty(values) || null == keyFunction) {
			return Maps.newHashMap();
		}
		Map<K, List<V>> builder = new HashMap<>();
		Iterator<T> iterator = values.iterator();
		while (iterator.hasNext()) {
			T t = iterator.next();
			K key = keyFunction.applyKey(t);
			V value = keyFunction.applyValue(t);
			if (builder.containsKey(key)) {
				builder.get(key).add(value);
			} else {
				List<V> newArrayList = new ArrayList<>();
				newArrayList.add(value);
				builder.put(key, newArrayList);
			}
		}
		return builder;
	}

	public static interface Function<K, V> {
		K apply(V input);
	}

	public static interface FunctionExPlus<T, K, V> {
		K applyKey(T input);
		V applyValue(T input);
	}

}