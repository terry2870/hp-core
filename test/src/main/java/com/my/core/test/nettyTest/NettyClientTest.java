/**
 * 
 */
package com.my.core.test.nettyTest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.netty.bean.Request;
import com.hp.core.netty.client.Client;

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
		
		try {
			client.connect("127.0.0.1", 9999);
			client.send(new Request("123456", String.class));
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
