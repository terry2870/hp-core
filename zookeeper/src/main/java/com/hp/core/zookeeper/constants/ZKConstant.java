/**
 * 
 */
package com.hp.core.zookeeper.constants;

/**
 * @author ping.huang
 * 2016年10月9日
 */
public class ZKConstant {

	
	//连接zk超时时间
	public static final int ZK_SESSION_TIMEOUT = 5000;

	
	public static final String ZK_REGISTRY_PATH = "/registry";
	public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}
