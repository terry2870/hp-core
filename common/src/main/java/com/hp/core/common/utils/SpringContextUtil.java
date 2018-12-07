package com.hp.core.common.utils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * spring上下文对象，用来获取bean
 * @author huangping <br />
 *         2012-12-1
 */
@Component("SpringContextUtil")
public class SpringContextUtil implements ApplicationContextAware {
	
	private static Logger log = LoggerFactory.getLogger(SpringContextUtil.class);

	private static ApplicationContext applicationContext;

	@Override
	public synchronized void setApplicationContext(ApplicationContext ctx) throws BeansException {
		SpringContextUtil.applicationContext = ctx;
	}
	
	/**
	 * 动态注册bean到spring容器中
	 * @param beanName
	 * @param className
	 */
	public static void registerBean(String beanName, Class<?> className) throws BeanDefinitionStoreException {
		registerBean(beanName, className, null);
	}
	
	/**
	 * 动态注册bean到spring容器中
	 * @param beanName
	 * @param className
	 * @param property
	 */
	public static void registerBean(String beanName, Class<?> className, Map<String, Object> property) throws BeanDefinitionStoreException {
		if (containsBean(beanName)) {
			log.error("registerBean error. with beanName is exists. with beanName={}", beanName);
			throw new BeanDefinitionStoreException("【"+ beanName +"】，spring bean名称重复");
		}
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(className);
		if (MapUtils.isNotEmpty(property)) {
			for (Entry<String, Object> entry : property.entrySet()) {
				builder.addPropertyValue(entry.getKey(), entry.getValue());
			}
		}
		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
	}
	
	/**
	 * 获取BeanDefinitionRegistry
	 * @return
	 */
	public static BeanDefinitionRegistry getBeanDefinitionRegistry() {
		Assert.notNull(applicationContext, "spring初始化失败");
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		return (BeanDefinitionRegistry) ctx.getBeanFactory();
	}
	
	/**
	 * 根据bean的名称，获取bean对象
	 * @param beanName bean名称
	 * @return 返回获取到的对象
	 */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	/**
	 * 根据bean的名称和class类型，获取bean对象
	 * @param beanName bean名称
	 * @param clazz 对象的class
	 * @return 返回获取到的对象
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}

	/**
	 * 根据class类型，获取bean对象
	 * @param clazz 对象的class
	 * @return 返回获取到的对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 查询spring工厂中是否包含该名称的bean对象
	 * @param beanName bean名称
	 * @return spring工厂中是否包含该名称的bean对象
	 */
	public static boolean containsBean(String beanName) {
		return applicationContext.containsBean(beanName);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。<br />
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * @param beanName bean名称
	 * @return 返回该对象是否是singleton
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String beanName) {
		return applicationContext.isSingleton(beanName);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * @param beanName bean名称
	 * @return 返回该对象的别名
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String beanName) {
		return applicationContext.getAliases(beanName);
	}

	/**
	 * 根据类型，查询bean
	 * @param type
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return applicationContext.getBeansOfType(type);
	}
	
	/**
	 * 根据注解类型，获取bean
	 * @param annotationType
	 * @return
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return applicationContext.getBeansWithAnnotation(annotationType);
	}
	
}
