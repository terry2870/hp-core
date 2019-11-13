/**
 * 
 */
package com.hp.core.elasticsearch.index.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

import com.alibaba.fastjson.JSON;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.DateUtil;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.constant.SearchIndexConstant;
import com.hp.core.elasticsearch.factory.IndexInfoFactory;
import com.hp.core.elasticsearch.index.IESIndexService;
import com.hp.core.mybatis.mapper.BaseMapper;

/**
 * 普遍的索引实现
 * @author huangping
 * Mar 15, 2019
 * @param <T>	数据库对象
 * @param <E>	ES里面对象
 */
public abstract class AbstSimpleIndexServiceImpl<T, E> implements IESIndexService {

	private static Logger log = LoggerFactory.getLogger(AbstSimpleIndexServiceImpl.class);
	
	@Autowired
	protected BaseMapper<T> baseMapper;
	
	@Autowired
	protected ElasticsearchTemplate elasticsearchTemplate;
	
	/**
	 * 根据数据库对象，获取ES对象
	 * @param t
	 * @return
	 */
	//public abstract E getESModelFromDBModel(T t);
	
	/**
	 * 根据数据库对象，获取ES对象
	 * @param list
	 * @return
	 */
	public abstract List<E> getESModelFromDBModel(List<T> list);
	
	/**
	 * 获取es的id字段
	 * @param e
	 * @return
	 */
	public abstract String getId(E e);
	
	/**
	 * 插入数据到新的索引
	 * @param indexInfo
	 * @param newIndexName
	 */
	public abstract void insertIntoES(IndexInfo indexInfo, String newIndexName);
	
	/**
	 * 根据id，批量查询
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return null;
		}
		return baseMapper.selectByPrimaryKeys(ids);
	}
	
	@Override
	public void updateByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		
		List<T> list = getByIds(ids);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		IndexInfo indexInfo = getIndexInfo();
		
		List<UpdateQuery> queries = getUpdateQueryDataListByDataList(list, indexInfo);
		if (CollectionUtils.isEmpty(queries)) {
			return;
		}

		elasticsearchTemplate.bulkUpdate(queries);
	}

	@Override
	public void insertByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		List<T> list = getByIds(ids);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		IndexInfo indexInfo = getIndexInfo();

		List<IndexQuery> queries = getIndexQueryDataListByDataList(list, indexInfo.getAlias(), indexInfo.getType());
		if (CollectionUtils.isEmpty(queries)) {
			return;
		}

		elasticsearchTemplate.bulkIndex(queries);
	}
	
	@Override
	public void deleteByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		IndexInfo indexInfo = getIndexInfo();

		DeleteQuery deleteQuery = new DeleteQuery();
		deleteQuery.setIndex(indexInfo.getAlias());
		deleteQuery.setType(indexInfo.getType());
		deleteQuery.setQuery(QueryBuilders.termsQuery("id", ids));
		elasticsearchTemplate.delete(deleteQuery);
	}

	/**
	 * 获取es的list对象
	 * @param list
	 * @param indexInfo
	 * @return
	 */
	private List<UpdateQuery> getUpdateQueryDataListByDataList(List<T> list, IndexInfo indexInfo) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		//转换为es对象
		List<E> l = getESModelFromDBModel(list);
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}
		
		List<UpdateQuery> queries = new ArrayList<>();
		UpdateQuery updateQuery = null;
		for (E e : l) {
			updateQuery = getUpdateQuery(e, indexInfo);
			if (updateQuery == null) {
				continue;
			}
			queries.add(updateQuery);
		}
		return queries;
	}
	
	/**
	 * 获取getUpdateQuery
	 * @param e
	 * @param indexInfo
	 * @return
	 */
	private UpdateQuery getUpdateQuery(E e, IndexInfo indexInfo) {
		if (e == null) {
			return null;
		}
		IndexRequest indexRequest = new IndexRequest(indexInfo.getAlias(), indexInfo.getType(), getId(e));
		
		indexRequest.source(JSON.parseObject(JSON.toJSONString(e)));
		return new UpdateQueryBuilder()
				.withId(getId(e))
				.withIndexName(indexInfo.getAlias())
				.withType(indexInfo.getType())
				.withIndexRequest(indexRequest)
				.withClass(indexInfo.getMappingClass())
				.withDoUpsert(true)
				.build();
	}
	
	@Override
	public IndexInfo getIndexInfo() {
		return IndexInfoFactory.getInstance().getIndexInfoByActualTypeArguments(this.getClass());
	}
	
	/**
	 * 建索引时，查询最大最小id时，额外的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T getQueryParams() {
		IndexInfo indexInfo = getIndexInfo();
		try {
			return (T) (indexInfo.getDalModelClass().newInstance());
		} catch (Exception e) {
			log.error("getQueryParams error.", e);
		}
		return null;
	}
	
	/**
	 * 每次从数据库获取的最大条数
	 * @return
	 */
	protected long getSize() {
		return 5000;
	}
	
	@Override
	public void reBuildIndex() {
		long stime = DateUtil.getCurrentTimeMilliSeconds();
		//获取索引名称
		IndexInfo indexInfo = getIndexInfo();
		log.info("start reBuildIndex with indexInfo={}", indexInfo);
		if (indexInfo == null || indexInfo.isEmpty()) {
			log.warn("reBuildIndex error. with indexName is empty.");
			throw new CommonException(500, "索引为空");
		}
		
		//创建新索引
		String newIndexName = createNewIndex(indexInfo);
		if (StringUtils.isEmpty(newIndexName)) {
			throw new CommonException(500, "reBuildIndex error.");
		}
		
		try {
			//插入新数据到新的索引
			insertIntoES(indexInfo, newIndexName);
			log.info("insertIntoES success. with indexInfo={}", indexInfo);
			
			//获取关联该别名的索引
			Set<String> indexList = getIndexByAlias(indexInfo.getAlias());
			
			//为新索引添加该别名
			boolean result = addAlias(newIndexName, indexInfo.getAlias());
			
			if (!result) {
				//如果添加索引别名失败，则删除新建的索引
				log.error("reBuildIndex error. with indexInfo={}", indexInfo);
				deleteIndex(newIndexName);
			} else {
				//成功的话，删除旧的索引
				if (CollectionUtils.isNotEmpty(indexList)) {
					for (String index : indexList) {
						deleteIndex(index);
					}
				}
				log.info("reBuildIndex success. with indexInfo={}, indexName={}", indexInfo, newIndexName);
			}
		} catch (Exception e) {
			log.error("reBuildIndex error. with indexInfo={}, newIndexName={}", indexInfo, newIndexName, e);
			//删除新建的索引
			deleteIndex(newIndexName);
			throw e;
		}
		long etime = DateUtil.getCurrentTimeMilliSeconds();
		log.info("reBuildIndex success. width indexName={}, cost time={} seconds", newIndexName, (etime - stime) / 1000);
	}
	
	/**
	 * 创建新的索引
	 * @param indexInfo
	 * @return
	 */
	private String createNewIndex(IndexInfo indexInfo) {
		//创建新的索引
		String newIndexName = indexInfo.getIndexName() + "_index_" + DateUtil.getToday("yyyyMMddHHmmss");
		try {
			Map setting = elasticsearchTemplate.getSetting(indexInfo.getMappingClass());

			Map setting1 = new TreeMap();
			for(Object e : setting.entrySet()){
				if(!(e instanceof Map.Entry)){
					continue;
				}

				Map.Entry e1 = (Map.Entry)e;

				if(!ArrayUtils.contains(SearchIndexConstant.INDEX_SETTINGS_EXCEPT_KEYS, e1.getKey())){
					setting1.put(e1.getKey(), e1.getValue());
				}
			}

			boolean result1 = elasticsearchTemplate.createIndex(newIndexName, setting1);
			boolean result2 = elasticsearchTemplate.putMapping(newIndexName, indexInfo.getType(), elasticsearchTemplate.getMapping(indexInfo.getMappingClass()));
			if (result1 && result2) {
				log.info("createNewIndex success with indexInfo={}, indexName={}", indexInfo, newIndexName);
				return newIndexName;
			}
		} catch (Exception e) {
			log.error("createNewIndex error. with indexInfo={}", indexInfo, e);
			deleteIndex(newIndexName);
		}
		return null;
	}
	
	/**
	 * 获取es的list对象
	 * @param list
	 * @param newIndexName
	 * @param type
	 * @return
	 */
	protected List<IndexQuery> getIndexQueryDataListByDataList(List<T> list, String newIndexName, String type) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<IndexQuery> queries = new ArrayList<>();
		IndexQuery indexQuery = null;
		
		//转换为es对象
		List<E> l = getESModelFromDBModel(list);
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}
		
		for (E e : l) {
			if (e == null) {
				continue;
			}
			indexQuery = getIndexQuery(e, newIndexName, type);
			if (indexQuery == null) {
				continue;
			}
			queries.add(indexQuery);
		}
		return queries;
	}
	
	/**
	 * 获取IndexQuery
	 * @param e
	 * @param indexName
	 * @param type
	 * @return
	 */
	private IndexQuery getIndexQuery(E e, String indexName, String type) {
		if (e == null) {
			return null;
		}
		return new IndexQueryBuilder()
				.withId(getId(e))
				.withIndexName(indexName)
				.withObject(e)
				.withType(type)
				.build();
	}
	
	/**
	 * 查询关联该别名的所有索引
	 * @param alias
	 * @return
	 */
	private Set<String> getIndexByAlias(String alias) {
		ClusterAdminClient c = elasticsearchTemplate.getClient().admin().cluster();
		ActionFuture<ClusterStateResponse> a = c.state(Requests.clusterStateRequest().clear().metaData(true));
		ClusterStateResponse response = a.actionGet();
		MetaData md = response.getState().getMetaData();
		String realIndexName = null;
		Set<String> indexNameList = new HashSet<>();
		for (IndexMetaData imd : md) {
			realIndexName = imd.getIndex().getName();
			for (AliasMetaData amd : imd.getAliases().values().toArray(AliasMetaData.class)) {
				if (alias.equals(amd.getAlias())) {
					indexNameList.add(realIndexName);
				}
			}
			
		}
		return indexNameList;
	}
	
	/**
	 * 添加新索引到该别名
	 * @param indexName
	 * @param alias
	 * @return
	 */
	private boolean addAlias(String indexName, String alias) {
		if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(alias)) {
			log.warn("addAlias error. with indexName or alias is empty.. with indexName={}, alias={}", indexName, alias);
			return false;
		}
		AliasQuery aliasQuery = new AliasQuery();
		aliasQuery.setAliasName(alias);
		aliasQuery.setIndexName(indexName);
		return elasticsearchTemplate.addAlias(aliasQuery);
	}
	
	/**
	 * 删除索引
	 * @param indexName
	 */
	private void deleteIndex(String indexName) {
		if (!elasticsearchTemplate.indexExists(indexName)) {
			return;
		}
		elasticsearchTemplate.deleteIndex(indexName);
	}

}
