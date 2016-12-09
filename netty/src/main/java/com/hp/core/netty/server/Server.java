/**
 * 
 */
package com.hp.core.netty.server;

/**
 * @author huangping 2016年7月24日 下午10:02:59
 */
public interface Server {

	/**
	 * 启动
	 * @throws Exception
	 */
	Server start() throws Exception;
	
	/**
	 * 停止监听
	 */
	Server stop();
}
