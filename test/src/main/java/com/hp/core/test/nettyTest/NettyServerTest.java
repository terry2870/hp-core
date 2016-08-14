/**
 * 
 */
package com.hp.core.test.nettyTest;

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
	public void testServer() {
		log.info("server");
		System.out.println("===== " + System.getProperty("line.separator"));
		try {
			server.start(9999);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
