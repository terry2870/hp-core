/**
 * 
 */
package com.hp.core.zookeeper.helper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ping.huang
 * 2016年10月9日
 */
public class ZookeeperHelper {

	private static Logger log = LoggerFactory.getLogger(ZookeeperHelper.class);
	
	/**
	 * 获取zk连接
	 * @param address
	 * @param sessionTimeout
	 * @return
	 * @throws Exception
	 */
	public static ZooKeeper getConnection(String address, int sessionTimeout) throws Exception {
		log.info("getConnection start with address={}", address);
		CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(address, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown();
				}
			}
		});
		latch.await();
		log.info("getConnection success with address={}", address);
		return zk;
	}
	
	/**
	 * 创建节点
	 * @param zk
	 * @param path
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String createNode(ZooKeeper zk, String path, String data, CreateMode createMode) throws Exception {
		if (data == null) {
			data = "";
		}
		Stat stat = zk.exists(path, false);
		if (stat == null) {
			//节点不存在，则新建
			zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
		} else {
			//节点已存在，则直接设置值
			//zk.setData(path, data.getBytes(), stat.getVersion() + 1);
		}
		
		log.info("create zookeeper node ({} => {})", path, data);
		return path;
	}
	
	public static void main(String[] args) {
		try {
			ZooKeeper zk = getConnection("192.168.102.205:2181", 5000);
			//createNode(zk, "/test", "12345", CreateMode.PERSISTENT);
			createNode(zk, "/test2/hp", "", CreateMode.EPHEMERAL);
			System.in.read();
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
