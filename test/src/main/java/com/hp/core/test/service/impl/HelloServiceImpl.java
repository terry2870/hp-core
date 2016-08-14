/**
 * 
 */
package com.hp.core.test.service.impl;

import com.hp.core.test.annotation.RpcService;
import com.hp.core.test.service.HelloService;

/**
 * @author huangping
 * 2016年8月14日 下午10:39:37
 */
@RpcService(HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello! " + name;
	}

}
