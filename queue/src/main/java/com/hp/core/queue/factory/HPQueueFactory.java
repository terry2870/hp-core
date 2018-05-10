/**
 * 
 */
package com.hp.core.queue.factory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ping.huang
 * 2017年3月30日
 */
public class HPQueueFactory {
	
	static Logger log = LoggerFactory.getLogger(HPQueueFactory.class);
	
	private int queueMaxSize = 5000;
	
	//存放所有的队列
	private Map<String, BlockingQueue<Object>> queueMap = new ConcurrentHashMap<>();

	//保证单例
	private static HPQueueFactory instance = new HPQueueFactory();
	
	private HPQueueFactory() {}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static HPQueueFactory getInstance() {
		return instance;
	}
	
	/**
	 * 获取队列
	 * @param topic
	 * @return
	 */
	public BlockingQueue<Object> getQueue(String topic) {
		BlockingQueue<Object> queue = queueMap.get(topic);
		if (queue != null) {
			return queue;
		}
		queue = new LinkedBlockingQueue<>(queueMaxSize);
		queueMap.put(topic, queue);
		return queue;
	}

	public Map<String, BlockingQueue<Object>> getQueueMap() {
		return queueMap;
	}

}
