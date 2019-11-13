/**
 * 
 */
package com.hp.core.elasticsearch.factory;
/**
 * @author huangping
 * Mar 21, 2019
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.annotations.Document;

import com.hp.core.common.exceptions.CommonException;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.constant.SearchIndexConstant;

public class IndexInfoFactory {

	private static Logger log = LoggerFactory.getLogger(IndexInfoFactory.class);
	
	/**
	 * 私有构造函数
	 */
	private IndexInfoFactory() {}
	
	/**
	 * 实例
	 * 保证单例
	 */
	private static IndexInfoFactory instance = new IndexInfoFactory();
	
	/**
	 * 获取实例
	 * @return
	 */
	public static IndexInfoFactory getInstance() {
		return instance;
	}
	
	/**
	 * 存放所有类，对应的索引信息
	 * key		索引实现类的className
	 * value	索引信息
	 */
	private static Map<String, IndexInfo> classIndexInfoMap = new ConcurrentHashMap<>();
	
	/**
	 * 存放所有类，对应的索引信息
	 * key		索引名称
	 * value	索引信息
	 */
	private static Map<String, IndexInfo> indexInfoMap = new ConcurrentHashMap<>();
	
	/**
	 * 存放所有的索引信息
	 */
	private static List<IndexInfo> indexInfoList = Collections.synchronizedList(new ArrayList<>());
		
	/**
	 * 根据索引名称，获取索引信息
	 * @param indexName
	 * @return
	 */
	public IndexInfo getIndexInfoByIndexName(String indexName) {
		return indexInfoMap.get(indexName);
	}
	
	/**
	 * 根据类的泛型，获取索引信息
	 * 这里约定只取类的第二个泛型
	 * @param className
	 * @return
	 */
	public IndexInfo getIndexInfoByActualTypeArguments(Class<?> className) {
		if (className == null) {
			return null;
		}
		String key = className.getName();
		IndexInfo indexInfo = classIndexInfoMap.get(key);
		if (indexInfo != null) {
			return indexInfo;
		}
		
		//锁定该对象，防止并发
		synchronized (className) {
			//双重校验
			//类似单例里面的双重校验锁
			indexInfo = classIndexInfoMap.get(key);
			if (indexInfo != null) {
				return indexInfo;
			}
			
			Type type = className.getGenericSuperclass();
			Type[] genericType = ((ParameterizedType) type).getActualTypeArguments();
			
			//取第二个泛型对象
			Class<?> dalModelClass = (Class<?>) genericType[0];
			Class<?> mappingClass = (Class<?>) genericType[1];
			indexInfo = getIndexInfo(dalModelClass, mappingClass);
			classIndexInfoMap.put(key, indexInfo);
			indexInfoMap.putIfAbsent(indexInfo.getIndexName(), indexInfo);
			
			if (!contants(indexInfo)) {
				indexInfoList.add(indexInfo);
			}
			return indexInfo;
		}
	}
	
	/**
	 * 检查是否已经有了
	 * @param indexInfo
	 * @return
	 */
	private boolean contants(IndexInfo indexInfo) {
		if (indexInfo == null) {
			return false;
		}
		for (IndexInfo index : indexInfoList) {
			if (index.getIndexName().contentEquals(indexInfo.getIndexName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取索引信息
	 * @param dalModelClassName
	 * @param mappingClassName
	 * @return
	 */
	private IndexInfo getIndexInfo(Class<?> dalModelClassName, Class<?> mappingClassName) {
		if (mappingClassName == null) {
			log.warn("getIndexInfo error. with mappingClassName is null");
			throw new CommonException(500, "mappingClassName is null");
		}
		
		if (dalModelClassName == null) {
			log.warn("getIndexInfo error. with dalModelClassName is null");
			throw new CommonException(500, "dalModelClassName is null");
		}
		
		Document document = mappingClassName.getAnnotation(Document.class);
		if (document == null) {
			log.warn("getIndexInfo error. class must be with annotation with Document");
			throw new CommonException(500, "class must be with annotation with Document");
		}
		return new IndexInfo(document.indexName(), SearchIndexConstant.getType(document.indexName()), SearchIndexConstant.getAlias(document.indexName()), dalModelClassName, mappingClassName);
	}
	
}
