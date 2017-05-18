/**
 * 
 */
package com.hp.core.test.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.test.annotation.RpcService;
import com.hp.core.test.bean.UserBean;
import com.hp.core.test.service.HelloService;

/**
 * @author huangping
 * 2016年8月14日 下午10:39:37
 */
@RpcService(HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService {

	static Logger log = LoggerFactory.getLogger(HelloServiceImpl.class);
	
	@Override
	public String hello(String name) {
		//log.info("get receive with name={}", name);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Hello! " + name;
	}

	@Override
	public String h(String name) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Hello1111111! " + name;
	}

	@Override
	public UserBean getUser(UserBean u) {
		UserBean user = new UserBean();
		user.setId(u.getId() + 100);
		user.setName(u.getName() + "1234");
		return user;
	}

}
