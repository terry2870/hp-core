/**
 * 
 */
package com.hp.core.test.limiterTest;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.test.service.HelloService;

/**
 * @author ping.huang
 * 2017年5月11日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/demo-spring-context.xml"})
public class AOPTest {

	static Logger log = LoggerFactory.getLogger(AOPTest.class);
	
	@Autowired
	HelloService helloService;
	
	ExecutorService exe = Executors.newFixedThreadPool(10);
	
	@Test
	public void test1() {
		for (int i = 0; i < 20; i++) {
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
				String str = helloService.hello("啊所发生的_" + i);
				log.info("helloService= " + str);
			} catch (Exception e) {
				log.error("123:", e);
			}
			
		}
		
		
	}
}
