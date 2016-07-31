/**
 * 
 */
package com.hp.core.netty.client;

import com.hp.core.netty.bean.Request;
import com.hp.core.netty.bean.Response;

/**
 * 客户端接口
 * @author huangping
 * 2016年7月24日 上午1:30:41
 */
public interface Client {

	/**
	 * 连接服务端
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	void connect(String host, int port) throws Exception;
	
	/**
	 * 发送请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Response send(Request request) throws Exception;
	
	/**
	 * 关闭连接
	 */
	void close();
}