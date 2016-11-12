/**
 * 
 */
package com.hp.core.netty.client2;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;

/**
 * 客户端接口
 * @author huangping
 * 2016年7月24日 上午1:30:41
 */
public interface Client {
	
	/**
	 * 初始化客户端
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * 发送请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	NettyResponse send(NettyRequest request) throws Exception;
	
	/**
	 * 关闭连接
	 */
	void close() throws Exception;
}
