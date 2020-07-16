/**
 * 
 */
package com.hp.core.jdbc.daoimpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.core.common.utils.SpringContextUtil;

/**
 * @author huangping
 * Jul 14, 2020
 */
public interface JdbcTemplateService {

	/**
	 * 获取JdbcTemplate
	 * @return
	 */
	public default JdbcTemplate getJdbcTemplate() {
		return SpringContextUtil.getBean("dynamicJdbcTemplate", JdbcTemplate.class);
	}
}
