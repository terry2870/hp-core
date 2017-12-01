/**
 * 
 */
package com.hp.core.netty.bean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * netty异步转同步实现
 * 
 * @author ping.huang 2017年11月21日
 */
public class SyncFutureBean<T> implements Future<T> {

	// 因为请求和响应是一一对应的，因此初始化CountDownLatch值为1。
	private CountDownLatch latch = new CountDownLatch(1);

	// 需要响应线程设置的响应结果
	private T response;

	// Futrue的请求时间，用于计算Future是否超时
	private long beginTime = System.currentTimeMillis();

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return response != null;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		latch.await();
		return this.response;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (latch.await(timeout, unit)) {
			return this.response;
		}
		return null;
	}

	/**
	 * 用于设置响应结果，并且做countDown操作，通知请求线程
	 * @param response
	 */
	public void setResponse(T response) {
		this.response = response;
		latch.countDown();
	}

	public long getBeginTime() {
		return beginTime;
	}

}
