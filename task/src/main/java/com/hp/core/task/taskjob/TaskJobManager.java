/**
 * 
 */
package com.hp.core.task.taskjob;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ping.huang
 * 2016年6月18日
 */
public class TaskJobManager {

	//所有的定时任务存放地
	public static Map<String, JobBean> jobMap = new ConcurrentHashMap<String, JobBean>();
	
	
}
