/**
 * 
 */
package com.hp.core.test.limiterTest;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.redis.HPValueOperations;
import com.hp.core.test.bean.UserBean;
import com.hp.core.test.service.HelloService;

/**
 * @author ping.huang
 * 2017年5月11日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring-*.xml"})
public class AOPTest {

	static Logger log = LoggerFactory.getLogger(AOPTest.class);
	
	@Autowired
	HelloService helloService;
	
	ExecutorService exe = Executors.newFixedThreadPool(10);
	
	@Autowired
	HPValueOperations hpValueOperations;
	
	@Test
	public void redisTest() {
		UserBean user1 = new UserBean();
		user1.setId(1);
		user1.setName("名称1");
		
		hpValueOperations.setIfAbsent("user", user1, 10, TimeUnit.MINUTES);
		log.info("set sucecess");
		
		UserBean user2 = hpValueOperations.get("user", UserBean.class);
		log.info("key={}", user2);
	}
	
	@Test
	public void test1() {
		for (int i = 0; i < 2; i++) {
			exe.execute(new R(i));
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class R implements Runnable {
		private int i;
		
		public R(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				/*String str = helloService.hello("啊所发生的_" + i);
				log.info("helloService= " + str);
				String s = helloService.h("啊所1212121_" + i);
				log.info("helloService12121= " + s);*/
				UserBean u = new UserBean();
				u.setId(i);
				u.setName("hp" + i);
				UserBean user = helloService.getUser(u);
				System.out.println(user);
			} catch (Exception e) {
				log.error("123:", e);
			}
			
		}
		
		
	}
}
