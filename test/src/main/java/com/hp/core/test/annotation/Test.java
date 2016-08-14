/**
 * 
 */
package com.hp.core.test.annotation;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.netty.server.NettyServer;
import com.hp.tools.common.utils.SpringContextUtil;

/**
 * @author huangping
 * 2016年8月14日 上午11:48:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class Test {

	
	@org.junit.Test
	public void test() {
		System.out.println("start");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
