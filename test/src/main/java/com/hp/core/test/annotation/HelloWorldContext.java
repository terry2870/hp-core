/**
 * 
 */
package com.hp.core.test.annotation;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.hp.tools.common.utils.SpringContextUtil;


/**
 * @author huangping
 * 2016年8月14日 上午11:51:04
 */
@Component
public class HelloWorldContext implements BeanPostProcessor {

	static Logger log = LoggerFactory.getLogger(HelloWorldContext.class);
	
//	@Resource
//	SayHello sayHello;
	
	public void init() {
//		log.info("sayHello={}", sayHello);
		Map<String, Object> map = SpringContextUtil.getBeansWithAnnotation(HelloWorld.class);
		Object obj = null;
		HelloWorld h = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			obj = entry.getValue();
			h = obj.getClass().getAnnotation(HelloWorld.class);
			log.info("obj={}", obj);
			log.info("h={}", h);
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = AopUtils.getTargetClass(bean);
		if (clazz.isAnnotationPresent(HelloWorld.class)) {
			HelloWorld hh = (HelloWorld) bean.getClass().getAnnotation(HelloWorld.class);
			log.info("hh={}", hh);
		}
		return bean;
	}
}
