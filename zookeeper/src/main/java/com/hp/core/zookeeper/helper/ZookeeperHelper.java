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
	 * @return
	 */
	public static ZooKeeper getConnection(String address) throws Exception {
		log.info("getConnection start with address={}", address);
		CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(address, 5000, new Watcher() {
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
	public static String createNode(ZooKeeper zk, String path, String data) throws Exception {
		byte[] bytes = data.getBytes();
		String result = zk.create(path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		log.info("create zookeeper node ({} => {})", path, data);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			ZooKeeper zk = getConnection("192.168.102.211:2181");
			createNode(zk, "/a/yh_test2", "12345");
			Thread.sleep(10000);
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
