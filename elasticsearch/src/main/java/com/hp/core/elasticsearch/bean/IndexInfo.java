/**
 * 
 */
package com.hp.core.elasticsearch.bean;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * Mar 15, 2019
 */
public class IndexInfo extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521394943297059128L;

	/**
	 * 索引前缀
	 */
	private String indexName;
	
	/**
	 * 类型名
	 */
	private String type;
	
	/**
	 * 索引别名
	 */
	private String alias;
	
	/**
	 * 对应es的mapping
	 */
	private Class<?> mappingClass;
	
	/**
	 * 对应数据库对象的class
	 */
	private Class<?> dalModelClass;
	
	/**
	 * 是否自动重建索引
	 */
	private boolean autoRebuild;
	
	/**
	 * 重建索引的cron表达式
	 */
	private String rebuildCron;
	
	/**
	 * 索引的实现类
	 */
	private String indexServiceBeanName;
	
	public IndexInfo() {}
	
	public IndexInfo(String indexName) {
		this.indexName = indexName;
	}
	
	public IndexInfo(String indexName, String type) {
		this(indexName);
		this.type = type;
	}
	
	public IndexInfo(String indexName, String type, String alias) {
		this(indexName, type);
		this.alias = alias;
	}
	
	public IndexInfo(String indexName, String type, String alias, Class<?> dalModelClass) {
		this(indexName, type, alias);
		this.dalModelClass = dalModelClass;
	}
	
	public IndexInfo(String indexName, String type, String alias, Class<?> dalModelClass, Class<?> mappingClass) {
		this(indexName, type, alias, dalModelClass);
		this.mappingClass = mappingClass;
	}
	
	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return StringUtils.isEmpty(this.indexName) || StringUtils.isEmpty(this.alias);
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Class<?> getMappingClass() {
		return mappingClass;
	}

	public void setMappingClass(Class<?> mappingClass) {
		this.mappingClass = mappingClass;
	}

	public String getRebuildCron() {
		return rebuildCron;
	}

	public void setRebuildCron(String rebuildCron) {
		this.rebuildCron = rebuildCron;
	}

	public boolean isAutoRebuild() {
		return autoRebuild;
	}

	public void setAutoRebuild(boolean autoRebuild) {
		this.autoRebuild = autoRebuild;
	}

	public String getIndexServiceBeanName() {
		return indexServiceBeanName;
	}

	public void setIndexServiceBeanName(String indexServiceBeanName) {
		this.indexServiceBeanName = indexServiceBeanName;
	}

	public Class<?> getDalModelClass() {
		return dalModelClass;
	}

	public void setDalModelClass(Class<?> dalModelClass) {
		this.dalModelClass = dalModelClass;
	}
}
