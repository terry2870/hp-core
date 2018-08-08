/**
 * 
 */
package com.hp.core.webjars.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author huangping
 * 2018年8月8日
 */
//@Configuration
//@EnableWebMvc
public class WellComFileWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		super.addViewControllers(registry);
	}
}
