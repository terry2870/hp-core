/**
 * 
 */
package com.hp.core.test;

import java.util.concurrent.TimeUnit;

import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.redis.HPValueOperations;

/**
 * @author huangping
 * Jun 28, 2020
 */
public class CacheHelper {
	
	public static <T> T getData(Object params, Function<T> function) {
		//获取redis key
		String key = function.getKey(params);
		
		//从spring容器里面获取redis操作类
		//该类是我项目中自定义封装的redis操作类，自动进行json格式的序列化和反序列化
		HPValueOperations hpValueOperations = SpringContextUtil.getBean(HPValueOperations.class);
		
		//从缓存获取
		T response = hpValueOperations.get(key, function.getResponseClass());
		if (response != null) {
			//缓存命中，则直接返回
			return response;
		}
		
		//缓存没有命中，则查询数据库
		response = function.getDataFromDB(params);
		
		//保存到redis
		hpValueOperations.set(key, response, function.getCacheTimeoutMinute(), TimeUnit.MINUTES);
		
		return null;
	}
	
	/**
	 * 
	 * @author huangping
	 * Jun 28, 2020
	 * @param <T>
	 */
	public static interface Function<T> {
		/**
		 * 获取缓存的key
		 * @param params
		 * @return
		 */
		public String getKey(Object params);
		
		/**
		 * 缓存不命中时，从数据库获取
		 * @param params
		 * @return
		 */
		public T getDataFromDB(Object params);
		
		/**
		 * 获取返回对象的class
		 * @return
		 */
		public Class<T> getResponseClass();
		
		/**
		 * 获取缓存超时时间（分钟）
		 * @return
		 */
		public default int getCacheTimeoutMinute() {
			return 10;
		}
	}
	
	public static void main(String[] args) {
		//外部调用
		//getUersById
		CacheHelper.getData(1, new Function<Object>() {
			@Override
			public String getKey(Object params) {
				return "getUersById_" + params;
			}

			@Override
			public Object getDataFromDB(Object params) {
				//查询数据库操作
				return null;
			}

			@Override
			public Class<Object> getResponseClass() {
				return Object.class;
			}
		});
		
		//getShoolInfoById
		CacheHelper.getData(1, new Function<Object>() {
			@Override
			public String getKey(Object params) {
				return "getShoolInfoById_" + params;
			}

			@Override
			public Object getDataFromDB(Object params) {
				//查询数据库操作
				return null;
			}

			@Override
			public Class<Object> getResponseClass() {
				return Object.class;
			}
		});
		
		// 等等.....
	}
}
