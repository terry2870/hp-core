/**
 * 
 */
package com.hp.core.elasticsearch.init;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.hp.core.elasticsearch.annotation.SearchIndex;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.factory.AutoRebuildIndexTask;
import com.hp.core.elasticsearch.factory.IndexInfoFactory;
import com.hp.core.task.enums.TaskModeEnum;
import com.hp.core.task.taskjob.JobBean;
import com.hp.core.task.taskjob.TaskJob;

/**
 * 获取索引注解
 * @author huangping
 * Mar 21, 2019
 */
@Component
public class InitIndexProcessor implements BeanPostProcessor {

	/**
	 * 任务名称的前缀
	 */
	private static final String TASK_JOB_NAME = "ELASTICSEARCH_JOB_";
	
	@Autowired
	private TaskJob taskJob;
	@Value("${yohobuy.tools.elasticsearch.autoindex:false}")
	private boolean autoIndex;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		SearchIndex searchIndex = bean.getClass().getAnnotation(SearchIndex.class);
		if (searchIndex != null) {
			//从类的泛型上，获取索引信息
			IndexInfo indexInfo = IndexInfoFactory.getInstance().getIndexInfoByActualTypeArguments(bean.getClass());
			
			//再从注解里面获取索引信息填进去
			indexInfo.setAutoRebuild(searchIndex.autoRebuild());
			indexInfo.setRebuildCron(searchIndex.rebuildCron());
			indexInfo.setIndexServiceBeanName(beanName);
			
			if (autoIndex) {
				//添加到自动重建索引的任务
				addTask(indexInfo);
			}
		}
		
		return bean;
	}
	
	/**
	 * 添加自动重建索引到任务
	 * @param indexInfo
	 */
	private void addTask(IndexInfo indexInfo) {
		if (indexInfo == null || !indexInfo.isAutoRebuild() || StringUtils.isEmpty(indexInfo.getRebuildCron())) {
			return;
		}
		JobBean job = new JobBean(TASK_JOB_NAME + indexInfo.getIndexName(), indexInfo.getRebuildCron(), TaskModeEnum.LOOP, new AutoRebuildIndexTask(indexInfo.getIndexName()));
		
		taskJob.addOrUpdateJob(job);
	}
}
