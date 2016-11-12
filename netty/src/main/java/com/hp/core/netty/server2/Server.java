/**
 * 
 */
package com.hp.core.netty.server2;

/**
 * @author huangping 2016年7月24日 下午10:02:59
 */
public interface Server {

	/**
	 * 初始化
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * 停止监听
	 */
	void stop();
}
