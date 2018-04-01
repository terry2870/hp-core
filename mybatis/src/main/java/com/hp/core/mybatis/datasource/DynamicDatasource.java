/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.fastjson.JSON;
import com.hp.core.mybatis.bean.DatasourceBean;

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource implements InitializingBean {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	private Map<String, Object> configMap;
	private Resource[] resources = new Resource[0];

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		//设置targetDataSources 值
		if (ArrayUtils.isEmpty(this.resources)) {
			log.error("set DynamicDatasource error. with resource is empty.");
			return;
		}
		try {
			List<String> datasourceStrList = IOUtils.readLines(this.resources[0].getInputStream(), "UTF-8");
			List<DatasourceBean> datasourceList = JSON.parseArray(StringUtils.join(datasourceStrList, ""), DatasourceBean.class);
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			
			
			
			super.setTargetDataSources(targetDataSources);
			super.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}

	public void setResources(Resource... resources) {
		this.resources = resources;
	}

}
