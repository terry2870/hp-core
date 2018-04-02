/**
 * 
 */
package com.hp.core.mybatis.datasource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.fastjson.JSON;
import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;
import com.hp.core.mybatis.enums.PoolConnectionFactoryEnum;

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource implements InitializingBean {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	
	//存放所有的数据源的动态数据源的key
	private static Map<String, List<String>> datasourceKey = new HashMap<>();
	
	private static final String MASTER_DS_KEY_PREX = "master_";
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
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
			//解析文件，设置db
			String jsonTxt = FileUtils.readFileToString(this.resources[0].getFile(), "UTF-8");
			List<DatasourceConfigBean> datasourceList = JSON.parseArray(jsonTxt, DatasourceConfigBean.class);
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			DynamicDatasourceBean dynamicDatasourceBean = null;
			for (DatasourceConfigBean b : datasourceList) {
				dynamicDatasourceBean = PoolConnectionFactoryEnum.getPoolConnectionFactory(b.getPoolName()).getDatasource(b);
				
				//设置master
				for (int i = 0; i < dynamicDatasourceBean.getMasterDatasource().size(); i++) {
					targetDataSources.put(MASTER_DS_KEY_PREX + b.getDatabaseName() + "_" + i, dynamicDatasourceBean.getMasterDatasource().get(i));
				}
				
				//设置slave
				if (CollectionUtils.isNotEmpty(dynamicDatasourceBean.getSlaveDatasource())) {
					for (int i = 0; i < dynamicDatasourceBean.getSlaveDatasource().size(); i++) {
						targetDataSources.put(SLAVE_DS_KEY_PREX + b.getDatabaseName() + "_" + i, dynamicDatasourceBean.getSlaveDatasource().get(i));
					}
				}
			}
			
			
			
			super.setTargetDataSources(targetDataSources);
			super.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}


	public void setResources(Resource... resources) {
		this.resources = resources;
	}

}
