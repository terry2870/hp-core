/**
 * 
 */
package com.hp.core.plugins.intercept;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.core.redis.HPValueOperations;
import com.hp.tools.common.utils.MD5Util;

/**
 * redis缓存插件
 * @author ping.huang
 * 2017年5月17日
 */
public class RedisInterceptPlugin implements AroundInterceptHandle {

	static Logger log = LoggerFactory.getLogger(RedisInterceptPlugin.class);
	
	ThreadLocal<String> redisKey = new ThreadLocal<>();
	
	@Autowired
	HPValueOperations hpValueOperations;
	
	//key的前缀
	private String keyPrefix = "";
	//键是否md5
	private boolean keyMd5 = true;
	//超时时间（秒）
	private long timeout = 10 * 60;
	
	
	@Override
	public Object getReturnValue(Object target, String methodName, Object[] args, Class<?> returnType) {
		String key = buildKey(target, methodName, args);
		redisKey.set(key);
		Object obj = hpValueOperations.get(key, returnType);
		return obj;
	}
	
	@Override
	public void onAfter(Object target, String methodName, Object[] args, Object result) {
		//设置redis
		String key = redisKey.get();
		hpValueOperations.set(key, result, timeout, TimeUnit.SECONDS);
		redisKey.remove();
	}

	/**
	 * build key
	 * @param target
	 * @param methodName
	 * @param args
	 * @return
	 */
	private String buildKey(Object target, String methodName, Object[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append(target.getClass().getName());
		if (StringUtils.isNotEmpty(methodName)) {
			sb.append("_").append(methodName);
		}
		if (ArrayUtils.isNotEmpty(args)) {
			for (Object o : args) {
				if (o == null) {
					continue;
				}
				sb.append("_").append(o.toString());
			}
		}
		log.info("buildKey with target={}, methodName={}, args={}. with key={}", target, methodName, args, sb);
		if (keyMd5) {
			return keyPrefix + MD5Util.getMD5(sb.toString());
		} else {
			return keyPrefix + sb.toString();
		}
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public void setKeyMd5(boolean keyMd5) {
		this.keyMd5 = keyMd5;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
