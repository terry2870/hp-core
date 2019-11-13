/**
 * 
 */
package com.hp.core.elasticsearch.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.index.IESIndexService;

/**
 * 自动重建索引的任务
 * @author huangping
 * Mar 21, 2019
 */
public class AutoRebuildIndexTask implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(AutoRebuildIndexTask.class);
	
	private String indexName;
	
	public AutoRebuildIndexTask(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public void run() {
		log.info("reIndex start. with indexName={}", indexName);
		if (StringUtils.isEmpty(indexName)) {
			log.warn("reIndex error. with indexName is empty.");
			throw new CommonException(500, "索引不能为空");
		}
		
		IndexInfo indexInfo = IndexInfoFactory.getInstance().getIndexInfoByIndexName(indexName);
		if (indexInfo == null) {
			log.warn("reIndex error. with indexName is error.");
			throw new CommonException(500, "索引名称错误");
		}
		
		IESIndexService service = SpringContextUtil.getBean(indexInfo.getIndexServiceBeanName(), IESIndexService.class);
		
		service.reBuildIndex();
	}

}
