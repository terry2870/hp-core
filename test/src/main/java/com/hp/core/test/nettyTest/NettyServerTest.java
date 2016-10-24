/**
 * 
 */
package com.hp.core.test.nettyTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.server.NettyServer;
import com.hp.core.netty.server.NettyServerChannelInitialier.NettyProcess;

/**
 * @author huangping
 * 2016年7月24日 下午3:11:25
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class NettyServerTest {

	static Logger log = LoggerFactory.getLogger(NettyServerTest.class);
	

	public static void main(String[] args) {
		log.info("server");
		System.out.println("===== " + System.getProperty("line.separator"));
		try {
			new NettyServer(9999, new NettyProcess() {
				
				@Override
				public NettyResponse process(NettyRequest request) {
					return new NettyResponse(request.getMessageId(), "收到了【"+ request.getData() +"】");
				}
			}).start();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
