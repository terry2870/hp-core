/**
 * 
 */
package com.hp.core.zookeeper;

import java.util.Scanner;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author huangping 2016年7月23日 上午12:07:15
 */
public class ZKServer {

	private String groupNode = "sgroup";
	private String subNode = "sub";

	public void connectZookeeper(String address) throws Exception {
		ZooKeeper zk = new ZooKeeper("192.168.128.129:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// 不做处理
			}
		});
		// 在"/sgroup"下创建子节点
		// 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀
		// 将server的地址数据关联到新创建的子节点上
		if (zk.exists("/" + groupNode, false) == null) {
			zk.create("/" + groupNode, address.getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		String createdPath = zk.create("/" + groupNode + "/" + subNode, address.getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("create: " + createdPath);
	}

	/**
	 * server的工作逻辑写在这个方法中 此处不做任何处理, 只让server sleep
	 */
	public void handle() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		// 在参数中指定server的地址
		/*if (args.length == 0) {
			System.err.println("The first argument must be server address");
			System.exit(1);
		}*/

		ZKServer as = new ZKServer();
		
		try(Scanner sc = new Scanner(System.in)) {
			as.connectZookeeper(sc.nextLine());
		}
		as.handle();
	}
}
