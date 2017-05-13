/**
 * 
 */
package com.hp.core.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author ping.huang
 * 2017年5月12日
 */
public class HPValueOperations<V> implements ValueOperations<String, V> {

	private HPRedisTemplate hpRedisTemplate;
	
	private HPRedisTemplate hpRedisTemplateReadOnly;
	
	private ValueOperations<String, String> valueOperations;

	private ValueOperations<String, String> valueOperationsReadOnly;

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(String key, V value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#set(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public void set(String key, V value, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#setIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Boolean setIfAbsent(String key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#multiSet(java.util.Map)
	 */
	@Override
	public void multiSet(Map<? extends String, ? extends V> map) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#multiSetIfAbsent(java.util.Map)
	 */
	@Override
	public Boolean multiSetIfAbsent(Map<? extends String, ? extends V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#getAndSet(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V getAndSet(String key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#multiGet(java.util.Collection)
	 */
	@Override
	public List<V> multiGet(Collection<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#increment(java.lang.Object, long)
	 */
	@Override
	public Long increment(String key, long delta) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#increment(java.lang.Object, double)
	 */
	@Override
	public Double increment(String key, double delta) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#append(java.lang.Object, java.lang.String)
	 */
	@Override
	public Integer append(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#get(java.lang.Object, long, long)
	 */
	@Override
	public String get(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#set(java.lang.Object, java.lang.Object, long)
	 */
	@Override
	public void set(String key, V value, long offset) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#size(java.lang.Object)
	 */
	@Override
	public Long size(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#setBit(java.lang.Object, long, boolean)
	 */
	@Override
	public Boolean setBit(String key, long offset, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#getBit(java.lang.Object, long)
	 */
	@Override
	public Boolean getBit(String key, long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.core.ValueOperations#getOperations()
	 */
	@Override
	public RedisOperations<String, V> getOperations() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
