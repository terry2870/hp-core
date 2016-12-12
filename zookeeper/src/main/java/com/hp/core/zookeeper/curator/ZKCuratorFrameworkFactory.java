/**
 * 
 */
package com.hp.core.zookeeper.curator;

import java.io.Closeable;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.hp.core.zookeeper.bean.ZKConfig;
import com.hp.core.zookeeper.curator.impl.DefaultZKClusterClient;
import com.hp.core.zookeeper.exception.ZKConnectException;

/**
 * @author ping.huang 2016年12月11日
 */
public class ZKCuratorFrameworkFactory implements Closeable {

	static Logger log = LoggerFactory.getLogger(ZKCuratorFrameworkFactory.class);

	private ConcurrentMap<String, ZKClusterClient> clientFactory = Maps.newConcurrentMap();

	// 饿汉模式，加载类，就初始化实例
	private static ZKCuratorFrameworkFactory instance = new ZKCuratorFrameworkFactory();

	private AtomicBoolean inited = new AtomicBoolean(false);

	// can not support new
	private ZKCuratorFrameworkFactory() {
	}

	// 使用此方法，保证单例模式
	public static ZKCuratorFrameworkFactory getInstance() {
		return instance;
	}

	/**
	 * 获取zk客户端实现
	 * @param name
	 * @return
	 */
	public ZKClusterClient getZKClusterClient(String name) {
		ZKClusterClient client = clientFactory.get(name);
		if (null == client) {
			throw new IllegalArgumentException("name may be wrong name is：" + name);
		}
		if (!client.isConnected()) {
			throw new ZKConnectException("with zk server connection loss,please check!!!!");
		}
		return client;
	}

	/**
	 * 初始化一些数据
	 * 
	 * @param config
	 */
	public void init(ZKConfig config) {
		if (inited.compareAndSet(false, true)) {
			// 检查参数
			validate(config);
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(config.getRetrySleepTimeMs(), config.getRetryTimes());
			CuratorFramework newClient = CuratorFrameworkFactory.newClient(config.getConnectString(), config.getSessionTimeoutMs(), config.getConnectionTimeoutMs(), retryPolicy);

			final CountDownLatch downLactch = new CountDownLatch(1);
			// 利用CountDownLatch 等待连接建立
			newClient.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				@Override
				public void stateChanged(CuratorFramework client, ConnectionState newState) {
					if (newState == ConnectionState.CONNECTED || newState == ConnectionState.RECONNECTED) {
						downLactch.countDown();
						log.info("with zk server connection is ok!!!!");
					}
				}
			});
			try {
				// 等待连接建立
				downLactch.await(config.getConnectionTimeoutMs(), TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				log.error("connection the url:{} failed at connectionTimeoutMs:{}", config.getConnectString(), config.getConnectionTimeoutMs());
				String message = MessageFormat.format("connection the url:{0} failed at connectionTimeoutMs:{1}", config.getConnectString(), config.getConnectionTimeoutMs());
				throw new ZKConnectException(message, e);
			}
			newClient.start();
			ZKClusterClient client = new DefaultZKClusterClient(newClient);
			client.setConnected(true);
			
			//新建根节点
			if (!client.isExsit(config.getBasePath())) {
				client.createNode(config.getBasePath(), null, CreateMode.PERSISTENT);
			}
			
			clientFactory.put(config.getName(), client);
		}
	}

	/**
	 * 参数合法性校验
	 * 
	 * @param config
	 */
	private void validate(ZKConfig config) {
		if (config == null) {
			throw new IllegalArgumentException("config can't be null");
		}
		if (StringUtils.isBlank(config.getConnectString()) || StringUtils.isBlank(config.getName())) {
			throw new IllegalArgumentException("name or url can't be null");
		}
		if (config.getConnectionTimeoutMs() == 0 || config.getSessionTimeoutMs() == 0) {
			throw new IllegalArgumentException("connectionTimeoutMs or sessionTimeoutMs can't be 0");
		}
	}

	@Override
	public void close() throws IOException {
		if (MapUtils.isNotEmpty(clientFactory)) {
			for (ZKClusterClient client : clientFactory.values()) {
				client.shutdown();
			}
		}
	}
}
