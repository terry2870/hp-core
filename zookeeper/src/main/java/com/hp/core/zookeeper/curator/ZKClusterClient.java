/**
 * 
 */
package com.hp.core.zookeeper.curator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import com.hp.core.zookeeper.bean.ZKNode;

/**
 * 操作zk的客户端接口
 * 
 * @author ping.huang 2016年12月11日
 */
public interface ZKClusterClient {

	/**
	 * 创建节点
	 * @param path
	 * @param value
	 * @param createMode
	 */
	void createNode(String path, String value, CreateMode createMode);

	/**
	 * 获取指定节点的值
	 * @param path
	 * @return
	 */
	String getNodeValue(String path);

	/**
	 * 获取指定节点的所有子节点列表
	 * @param path
	 * @return
	 */
	List<ZKNode> getChildNodes(String path);

	/**
	 * 删除指定节点
	 * @param path
	 */
	void deleteNode(String path);

	/**
	 * 修改指定节点的值
	 * @param path
	 * @param value
	 */
	void setNodeValue(String path, String value);

	/**
	 * 节点是否存在,返回true表示存在
	 * @param path
	 * @return
	 */
	boolean isExsit(String path);

	/**
	 * 连接是否有效
	 * @return
	 */
	boolean isConnected();

	/**
	 * 设置zkclient的连接状态 
	 * @param isConnected
	 */
	void setConnected(boolean isConnected);

	/**
	 * 关闭zkclient等操作
	 */
	void shutdown();

	/**
	 * 获取操作的实际客户端
	 * @return
	 */
	CuratorFramework getCuratorFramework();
}
