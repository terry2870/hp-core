/**
 * 
 */
package com.hp.core.netty.constants;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.hp.core.netty.bean.Response;

/**
 * @author huangping
 * 2016年8月7日 下午11:14:13
 */
public class NettyConstants {

	
	/**
	 * 用来存放服务端返回的数据
	 */
	public static ConcurrentHashMap<String, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<String, BlockingQueue<Response>>();
}
