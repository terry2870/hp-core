/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource implements InitializingBean {

	private Map<String, Object> configMap;

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		//设置targetDataSources 值
		
		
		
		
		super.afterPropertiesSet();
	}
	
	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}

}
