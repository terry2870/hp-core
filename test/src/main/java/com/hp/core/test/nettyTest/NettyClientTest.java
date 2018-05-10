/**
 * 
 */
package com.hp.core.test.nettyTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.DateUtil;
import com.hp.core.netty.client.Client;
import com.hp.core.netty.client.NettyClient;

/**
 * @author huangping
 * 2016年7月24日 下午3:11:25
 */
public class NettyClientTest {

	static Logger log = LoggerFactory.getLogger(NettyClientTest.class);
	
	static Client client = new NettyClient("127.0.0.1", 9999, 2);
	
	public static void main(String[] args) {
		log.info("client");
		ExecutorService exe = Executors.newFixedThreadPool(100);
		
		try {
			client.init();
			for (int i = 0; i < 10; i++) {
				String name = "";
				name += DateUtil.getCurrentTimeSeconds();
				name += "_" + i;
				exe.execute(new Run(new User(i, name)));
			}
			
			System.in.read();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class Run implements Runnable {

		private User user;
		
		public Run(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			/*NettyRequest req = new NettyRequest(user, User.class);
			try {
				log.info("客户端发送消息：" + req);
				NettyResponse resp = client.send(req);
				log.info("客户端收到返回：" + resp);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		
	}
	
	public static class User extends BaseBean {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1229273332105532364L;
		/**
		 * @param id
		 * @param name
		 */
		public User(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		private int id;
		private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
