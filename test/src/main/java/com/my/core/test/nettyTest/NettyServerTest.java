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

import com.hp.core.netty.server.Server;

/**
 * @author huangping
 * 2016年7月24日 下午3:11:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class NettyServerTest {

	static Logger log = LoggerFactory.getLogger(NettyServerTest.class);
	
	@Resource
	Server server;
	
	@Test
	public void testClient() {
		log.info("server");
		
		try {
			server.start(9999);
			Thread.sleep(10000000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}