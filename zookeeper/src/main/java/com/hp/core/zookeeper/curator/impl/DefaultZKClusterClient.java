/**
 * 
 */
package com.hp.core.zookeeper.curator.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hp.core.common.constants.DefaultConstant;
import com.hp.core.zookeeper.bean.ZKNode;
import com.hp.core.zookeeper.curator.ZKClusterClient;
import com.hp.core.zookeeper.exception.ZKConnectException;
import com.hp.core.zookeeper.exception.ZKOperateException;

/**
 * @author ping.huang 2016年12月11日
 */
public class DefaultZKClusterClient implements ZKClusterClient {

	static Logger log = LoggerFactory.getLogger(DefaultZKClusterClient.class);

	private volatile boolean isConnected = false;

	private static Charset charset = DefaultConstant.DEFAULT_CHARSET;

	private CuratorFramework client;

	public DefaultZKClusterClient(CuratorFramework client) {
		this.client = client;
	}

	@Override
	public void createNode(String path, String value, CreateMode createMode) {
		log.info("createNode with path={}, value={}, createMode={}", path, value, createMode);
		Preconditions.checkNotNull(path, "path can't be null");
		Preconditions.checkNotNull(value, "value can't be null");
		checkConnection();
		try {
			client.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, value.getBytes(charset));
			log.info("createNode success with path={}, value={}, createMode={}", path, value, createMode);
		} catch (Exception e) {
			log.error("create path failed:{},value:{}", path, value, e);
			throw new ZKOperateException("create path failed:" + path, e);
		}
	}

	@Override
	public String getNodeValue(String path) {
		log.info("getNodeValue with path={}", path);
		Preconditions.checkNotNull(path, "path can't be null");
		checkConnection();
		try {
			byte[] bytes = client.getData().forPath(path);
			log.info("getNodeValue success with path={}", path);
			return new String(bytes, charset);
		} catch (Exception e) {
			log.error("getNodeValue path failed:{}", path, e);
			throw new ZKOperateException("getNodeValue path failed:" + path, e);
		}
	}

	@Override
	public List<ZKNode> getChildNodes(String path) {
		log.info("getChildNodes with path={}", path);
		Preconditions.checkNotNull(path, "path can't be null");
		checkConnection();
		try {
			List<String> paths = client.getChildren().forPath(path);
			if (CollectionUtils.isEmpty(paths)) {
				return Lists.newArrayList();
			}
			List<ZKNode> nodes = new ArrayList<>(paths.size());
			for (String vpath : paths) {
				byte[] bytes = client.getData().forPath(ZKPaths.makePath(path, vpath));
				ZKNode node = new ZKNode(vpath, new String(bytes, charset), 0);
				nodes.add(node);
			}
			log.info("getChildNodes success with path={}", path);
			return nodes;
		} catch (Exception e) {
			log.error("getChildNodes path failed:{}", path, e);
			throw new ZKOperateException("getChildNodes path failed:" + path, e);
		}
	}

	@Override
	public void deleteNode(String path) {
		log.info("deleteNode with path={}", path);
		Preconditions.checkNotNull(path, "path can't be null");
		checkConnection();
		try {
			client.delete().deletingChildrenIfNeeded().inBackground().forPath(path);
			log.info("deleteNode success with path={}", path);
		} catch (Exception e) {
			log.error("deleteNode path failed:{}", path, e);
			throw new ZKOperateException("deleteNode path failed:" + path, e);
		}
	}

	@Override
	public void setNodeValue(String path, String value) {
		log.info("setNodeValue with path={}, value={}", path, value);
		Preconditions.checkNotNull(path, "path can't be null");
		Preconditions.checkNotNull(value, "value can't be null");
		checkConnection();
		try {
			client.setData().forPath(path, value.getBytes(charset));
			log.info("setNodeValue success with path={}, value={}", path, value);
		} catch (Exception e) {
			log.error("setNodeValue path failed:{},value:{}", path, value, e);
			throw new ZKOperateException("setNodeValue path failed:" + path, e);
		}
	}

	@Override
	public boolean isExsit(String path) {
		log.info("check isExsit with path={}", path);
		Preconditions.checkNotNull(path, "path can't be null");
		checkConnection();
		Stat stat = null;
		try {
			stat = client.checkExists().forPath(path);
			log.info("check isExsit success with path={}", path);
			return null != stat;
		} catch (Exception e) {
			log.error("isExsit path failed:{},value:{}", path, e);
			throw new ZKOperateException("isExsit path failed:" + path, e);
		}
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	@Override
	public void shutdown() {
		log.info("shutdown zk client");
		isConnected = false;
		if (client != null) {
			client.close();
		}
		client = null;
	}

	@Override
	public CuratorFramework getCuratorFramework() {
		return client;
	}

	private void checkConnection() {
		if (!isConnected()) {
			log.error("with zk server connection loss,please check");
			throw new ZKConnectException("with zk server connection loss,please check!!!!");
		}
	}

}
