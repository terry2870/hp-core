/**
 * 
 */
package com.hp.core.test.service;

import com.hp.core.test.bean.UserBean;

/**
 * @author huangping
 * 2016年8月14日 下午10:38:45
 */
public interface HelloService {

	String hello(String name);
	
	String h(String name);
	
	UserBean getUser(UserBean u);
}
