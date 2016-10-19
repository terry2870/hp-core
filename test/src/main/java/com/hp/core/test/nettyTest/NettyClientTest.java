/**
 * 
 */
package com.hp.core.test.nettyTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.client.Client;
import com.hp.tools.common.utils.DateUtil;
import com.hp.tools.common.utils.RandomUtil;

/**
 * @author huangping
 * 2016年7月24日 下午3:11:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class NettyClientTest {

	static Logger log = LoggerFactory.getLogger(NettyClientTest.class);
	
	@Resource
	Client client;
	
	@Test
	public void testClient() {
		log.info("client");
		ExecutorService exe = Executors.newFixedThreadPool(10);
		try {
			client.connect("127.0.0.1", 9999);
			for (int i = 0; i < 2; i++) {
				String name = "";
				while (name.length() < 2000) {
					name += DateUtil.getCurrentTimeSeconds();
				}
				name += "_" + i;
				exe.execute(new Run(new User(i, name)));
			}
			
			Thread.sleep(100000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class Run implements Runnable {

		private User user;
		
		public Run(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			NettyRequest req = new NettyRequest(DateUtil.getCurrentTimeSeconds() + "_" + RandomUtil.getRandom(5), user, User.class);
			try {
				log.info("客户端发送消息：" + req);
				NettyResponse resp = client.send(req);
				log.info("客户端收到返回：" + resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class User {
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
