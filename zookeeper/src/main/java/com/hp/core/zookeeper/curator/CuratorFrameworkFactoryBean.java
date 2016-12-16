/**
 * 
 */
package com.hp.core.zookeeper.curator;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author ping.huang 2016年12月11日
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, Closeable {

	static Logger log = LoggerFactory.getLogger(CuratorFrameworkFactoryBean.class);
	
	private CuratorFramework curator;

	private String connectString;
	private RetryPolicy retryPolicy;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;
	private String namespace;

	public void init() throws Exception {
		if (StringUtils.isBlank(connectString)) {
			return;
		}

		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString(connectString);
		if (retryPolicy == null) {
			retryPolicy = new ExponentialBackoffRetry(1000, 3);
		}
		builder.retryPolicy(retryPolicy);

		if (sessionTimeoutMs != 0) {
			builder.sessionTimeoutMs(sessionTimeoutMs);
		}
		if (connectionTimeoutMs != 0) {
			builder.connectionTimeoutMs(connectionTimeoutMs);
		}
		if (StringUtils.isNotBlank(namespace)) {
			builder.namespace(namespace);
		}

		curator = builder.build();
		curator.start();
		log.info("start zookeeper client success. connected to {}", connectString);
	}

	@Override
	public CuratorFramework getObject() throws Exception {
		return curator;
	}

	@Override
	public Class<?> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void close() throws IOException {
		if (curator != null) {
			curator.close();
		}
	}

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
