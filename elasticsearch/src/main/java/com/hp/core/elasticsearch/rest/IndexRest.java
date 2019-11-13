/**
 * 
 */
package com.hp.core.elasticsearch.rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.hp.core.common.beans.Response;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.factory.AutoRebuildIndexTask;
import com.hp.core.elasticsearch.factory.IndexInfoFactory;
import com.hp.core.elasticsearch.index.IESIndexService;

/**
 * @author huangping
 * Mar 14, 2019
 */
@RestController
@RequestMapping("/index")
public class IndexRest {
	
	private static Logger log = LoggerFactory.getLogger(IndexRest.class);
	
	//定义线程池
	private static ExecutorService exe = Executors.newFixedThreadPool(3);
	
	/**
	 * 重建索引
	 * @param indexName
	 * @return
	 */
	@RequestMapping("/reindex")
	public Response<IndexInfo> reIndex(String indexName) {
		exe.execute(new AutoRebuildIndexTask(indexName));
		return new Response<>();
	}
	
	/**
	 * 根据id，更新
	 * @param indexName
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updateByIds")
	public Response<IndexInfo> updateByIds(String indexName, Integer[] ids) {
		IndexInfo indexInfo = IndexInfoFactory.getInstance().getIndexInfoByIndexName(indexName);
		if (indexInfo == null) {
			log.warn("updateByIds error. with indexName is error.");
			throw new CommonException(500, "索引名称错误");
		}
		
		IESIndexService service = SpringContextUtil.getBean(indexInfo.getIndexServiceBeanName(), IESIndexService.class);
		service.updateByIds(Lists.newArrayList(ids));
		return new Response<>();
	}

	/**
	 * 根据id，插入
	 * @param indexName
	 * @param ids
	 * @return
	 */
	@RequestMapping("/insertByIds")
	public Response<IndexInfo> insertByIds(String indexName, Integer[] ids) {
		IndexInfo indexInfo = IndexInfoFactory.getInstance().getIndexInfoByIndexName(indexName);
		if (indexInfo == null) {
			log.warn("insertByIds error. with indexName is error.");
			throw new CommonException(500, "索引名称错误");
		}

		IESIndexService service = SpringContextUtil.getBean(indexInfo.getIndexServiceBeanName(), IESIndexService.class);
		service.insertByIds(Lists.newArrayList(ids));
		return new Response<>();
	}
	
	/**
	 * 根据id，删除
	 * @param indexName
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	public Response<IndexInfo> deleteByIds(String indexName, Integer[] ids) {
		IndexInfo indexInfo = IndexInfoFactory.getInstance().getIndexInfoByIndexName(indexName);
		if (indexInfo == null) {
			log.warn("deleteByIds error. with indexName is error.");
			throw new CommonException(500, "索引名称错误");
		}

		IESIndexService service = SpringContextUtil.getBean(indexInfo.getIndexServiceBeanName(), IESIndexService.class);
		service.deleteByIds(Lists.newArrayList(ids));
		return new Response<>();
	}
}
