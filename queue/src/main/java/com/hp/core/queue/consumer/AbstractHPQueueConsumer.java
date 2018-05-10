/**
 * 
 */
package com.hp.core.queue.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.queue.factory.HPQueueFactory;

/**
 * @author ping.huang
 * 2017年3月30日
 */
public abstract class AbstractHPQueueConsumer {

	static Logger log = LoggerFactory.getLogger(AbstractHPQueueConsumer.class);
	
	/**
	 * 获取topic
	 * @return
	 */
	public abstract String getTopic();
	
	/**
	 * 具体处理方法
	 * @param message
	 */
	public abstract void execute(Object message);
	
	/**
	 * 获取消费者数量（默认一个）
	 * @return
	 */
	public int getConsumerSize() {
		return 1;
	}
	
	/**
	 * 消费
	 */
	@PostConstruct
	public void init() {
		log.info("init HPQueueConsumer start. with topic={}", getTopic());
		if (StringUtils.isEmpty(getTopic())) {
			log.warn("init error. with topic is empty.");
			return;
		}
		ExecutorService exe = Executors.newFixedThreadPool(getConsumerSize());
		BlockingQueue<Object> queue = HPQueueFactory.getInstance().getQueue(getTopic());
		for (int i = 0; i < getConsumerSize(); i++) {
			exe.execute(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						try {
							execute(queue.take());
						} catch (InterruptedException e) {
							log.error("take message from {} error", getTopic(), e);
						}
					}
				}
			});
		}
		log.info("init HPQueueConsumer success. with topic={}", getTopic());
	}
	
}
