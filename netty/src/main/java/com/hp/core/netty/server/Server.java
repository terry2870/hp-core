/**
 * 
 */
package com.hp.core.netty.server;

/**
 * @author huangping 2016年7月24日 下午10:02:59
 */
public interface Server {

	/**
	 * 启动监听
	 * @param port
	 * @throws Exception
	 */
	void start(int port) throws Exception;

	
	/**
	 * 停止监听
	 */
	void stop();
}
