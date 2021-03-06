/**
 * 
 */
package com.hp.core.common.configurer;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.Lists;
import com.hp.core.common.utils.SpringContextUtil;

/**
 * springboot自定义拦截器
 * 并设置静态资源文件地址
 * @author huangping
 * 2018年9月17日
 */
@Configuration
public class HpCoreWebMvcConfigurer implements WebMvcConfigurer {

	private static final List<String> DEFAULT_STATIC_PATTERN = Lists.newArrayList("/error", "/static/**", "/html/**", "/js/**", "/css/**", "/images/**"
			, "/favicon.ico", "*.html", "*.js", "*.css", "*.jpg", "*.png", "*.gif");
	
	/**
	 * 拦截器的beanname
	 */
	@Value("#{'${hp.core.common.interceptor.beanname:}'.split(',')}")
	private List<String> interceptorBeanNameList;
	
	/**
	 * 静态资源文件
	 */
	@Value("#{'${hp.core.common.static.path.pattern:}'.split(',')}")
	private List<String> staticPatternList;
	
	/**
	 * 默认的拦截url
	 */
	@Value("${hp.core.common.interceptor.path.pattern:/**}")
	private String interceptorPathPatterns;
	
	/**
	 * 首页
	 */
	@Value("${hp.core.common.interceptor.welcome.file:}")
	private String welcomeFile;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		if (StringUtils.isEmpty(welcomeFile)) {
			return;
		}
		registry.addViewController("/").setViewName("forward:" + welcomeFile);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (CollectionUtils.isEmpty(interceptorBeanNameList) || (interceptorBeanNameList.size() == 1 && StringUtils.isEmpty(interceptorBeanNameList.get(0)))) {
			interceptorBeanNameList = Lists.newArrayList("threadProfileInterceptor");
		} else {
			interceptorBeanNameList.add("threadProfileInterceptor");
		}
		if (CollectionUtils.isEmpty(staticPatternList) || (staticPatternList.size() == 1 && StringUtils.isEmpty(staticPatternList.get(0)))) {
			//如果没有配置静态资源，则使用默认
			staticPatternList = DEFAULT_STATIC_PATTERN;
		} else {
			//如果配置了，则加上默认的几个
			staticPatternList.addAll(DEFAULT_STATIC_PATTERN);
		}
		HandlerInterceptor interceptor = null;
		for (String beanName : interceptorBeanNameList) {
			interceptor = SpringContextUtil.getBean(beanName, HandlerInterceptor.class);
			registry.addInterceptor(interceptor).addPathPatterns(interceptorPathPatterns).excludePathPatterns(staticPatternList);
		}
	}
}
