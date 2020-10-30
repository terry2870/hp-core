/**
 * 
 */
package com.hp.core.elasticsearch.index.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate.ClientCallback;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.DateUtil;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.factory.IndexInfoFactory;
import com.hp.core.elasticsearch.index.IESIndexService;
import com.hp.core.mybatis.mapper.BaseMapper;

/**
 * 普遍的索引实现
 * @author huangping
 * Mar 15, 2019
 * @param <DB_MODEL>	数据库对象
 * @param <ES_MODEL>	ES里面对象
 */
public abstract class AbstSimpleIndexServiceImpl<DB_MODEL, ES_MODEL> implements IESIndexService {

	private static Logger log = LoggerFactory.getLogger(AbstSimpleIndexServiceImpl.class);
	
	@Autowired
	protected BaseMapper<DB_MODEL, Integer> baseMapper;
	@Autowired
	protected ElasticsearchRestTemplate elasticsearchRestTemplate;
	@Autowired
	protected ElasticsearchRepository<ES_MODEL, Integer> elasticsearchRepository;
	
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
	public abstract List<ES_MODEL> getESModelFromDBModel(List<DB_MODEL> list);
	
	/**
	 * 获取es的id字段
	 * @param e
	 * @return
	 */
	public abstract String getId(ES_MODEL e);
	
	/**
	 * 插入数据到新的索引
	 * @param indexInfo
	 * @param newIndexCoordinates
	 */
	public abstract void insertIntoES(IndexInfo indexInfo, IndexCoordinates newIndexCoordinates);
	
	/**
	 * 根据id，批量查询
	 * @param ids
	 * @return
	 */
	public List<DB_MODEL> getByIds(List<Integer> ids) {
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
		
		//从数据库查询最新数据
		List<DB_MODEL> list = getByIds(ids);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		IndexInfo indexInfo = getIndexInfo();
		
		List<UpdateQuery> queries = getUpdateQueryDataListByDataList(list, indexInfo);
		if (CollectionUtils.isEmpty(queries)) {
			return;
		}

		elasticsearchRestTemplate.bulkUpdate(queries, getIndexCoordinatesByIndexName(indexInfo.getAlias()));
	}

	@Override
	public void insertByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		List<DB_MODEL> list = getByIds(ids);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		IndexInfo indexInfo = getIndexInfo();

		List<IndexQuery> queries = getIndexQueryDataListByDataList(list);
		if (CollectionUtils.isEmpty(queries)) {
			return;
		}
		elasticsearchRestTemplate.bulkIndex(queries, getIndexCoordinatesByIndexName(indexInfo.getAlias()));
	}
	
	@Override
	public void deleteByIds(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		IndexInfo indexInfo = getIndexInfo();
		
		IndexCoordinates indexCoordinates = getIndexCoordinatesByIndexName(indexInfo.getAlias());
		for (Integer id : ids) {
			elasticsearchRestTemplate.delete(id.toString(), indexCoordinates);
		}
	}

	/**
	 * 获取es的list对象
	 * @param list
	 * @param indexInfo
	 * @return
	 */
	private List<UpdateQuery> getUpdateQueryDataListByDataList(List<DB_MODEL> list, IndexInfo indexInfo) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		//转换为es对象
		List<ES_MODEL> l = getESModelFromDBModel(list);
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}
		
		List<UpdateQuery> queries = new ArrayList<>();
		UpdateQuery updateQuery = null;
		for (ES_MODEL e : l) {
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
	private UpdateQuery getUpdateQuery(ES_MODEL e, IndexInfo indexInfo) {
		if (e == null) {
			return null;
		}
		
		return UpdateQuery.builder(getId(e))
				.withDocAsUpsert(true)
				.withDocument(Document.parse(JSON.toJSONString(e)))
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
	protected SQLBuilders getSQLBuilders() {
		return SQLBuilders.create();
	}
	
	/**
	 * 每次从数据库获取的最大条数
	 * @return
	 */
	protected int getSize() {
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
		IndexCoordinates newIndexCoordinates = createNewIndex(indexInfo);
		if (newIndexCoordinates == null) {
			throw new CommonException(500, "reBuildIndex error.");
		}
		IndexOperations newIndexOperations = elasticsearchRestTemplate.indexOps(newIndexCoordinates);
		try {
			//插入新数据到新的索引
			insertIntoES(indexInfo, newIndexCoordinates);
			log.info("insertIntoES success. with indexInfo={}", indexInfo);
			
			//获取关联该别名的索引
			List<String> oldIndexList = getIndexByAlias(indexInfo.getAlias());
			
			//为新索引添加该别名
			boolean result = addAlias(newIndexOperations, indexInfo.getAlias());
			
			if (!result) {
				//如果添加索引别名失败，则删除新建的索引
				log.error("reBuildIndex error. with indexInfo={}", indexInfo);
				deleteIndex(newIndexOperations);
			} else {
				//成功的话，删除旧的索引
				if (CollectionUtils.isNotEmpty(oldIndexList)) {
					for (String oldIndex : oldIndexList) {
						deleteIndex(elasticsearchRestTemplate.indexOps(IndexCoordinates.of(oldIndex)));
					}
				}
				log.info("reBuildIndex success. with indexInfo={}, newIndexCoordinates={}", indexInfo, newIndexCoordinates);
			}
		} catch (Exception e) {
			log.error("reBuildIndex error. with indexInfo={}, newIndexCoordinates={}", indexInfo, newIndexCoordinates, e);
			//删除新建的索引
			deleteIndex(newIndexOperations);
			throw e;
		}
		long etime = DateUtil.getCurrentTimeMilliSeconds();
		log.info("reBuildIndex success. width newIndexCoordinates={}, cost time={} seconds", newIndexCoordinates, (etime - stime) / 1000);
	}
	
	/**
	 * 创建新的索引
	 * @param indexInfo
	 * @return
	 */
	private IndexCoordinates createNewIndex(IndexInfo indexInfo) {
		//创建新的索引
		String newIndexName = indexInfo.getIndexName() + "_index_" + DateUtil.getToday("yyyyMMddHHmmss");
		IndexCoordinates newIndex = IndexCoordinates.of(newIndexName);
		IndexOperations newIndexOperations = elasticsearchRestTemplate.indexOps(newIndex);
		try {
			//Map<String, Object> setting = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexInfo.getIndexName())).getSettings();
			//Map<String, Object> setting = elasticsearchRestTemplate.indexOps(indexInfo.getMappingClass()).getSettings();
			//Map<String, Object> setting = indexInfo.getIndexOperations(elasticsearchRestTemplate).getSettings();
			//Document newDocument = Document.from(setting);
			/*Map setting1 = new TreeMap();
			for(Object e : setting.entrySet()){
				if(!(e instanceof Map.Entry)){
					continue;
				}

				Map.Entry e1 = (Map.Entry)e;

				if(!ArrayUtils.contains(SearchIndexConstant.INDEX_SETTINGS_EXCEPT_KEYS, e1.getKey())){
					setting1.put(e1.getKey(), e1.getValue());
				}
			}*/
			boolean result1 = newIndexOperations.create();
			Document newDocument = newIndexOperations.createMapping(indexInfo.getMappingClass());
			boolean result2 = newIndexOperations.putMapping(newDocument);
			//boolean result2 = elasticsearchRestTemplate.putMapping(newIndexName, indexInfo.getType(), elasticsearchTemplate.getMapping(indexInfo.getMappingClass()));
			if (result1 && result2) {
				log.info("createNewIndex success with indexInfo={}, indexName={}", indexInfo, newIndexName);
				return newIndex;
			}
		} catch (Exception e) {
			log.error("createNewIndex error. with indexInfo={}", indexInfo, e);
			deleteIndex(newIndexOperations);
		}
		return null;
	}
	
	/**
	 * 获取es的list对象
	 * @param list
	 * @return
	 */
	protected List<IndexQuery> getIndexQueryDataListByDataList(List<DB_MODEL> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<IndexQuery> queries = new ArrayList<>();
		IndexQuery indexQuery = null;
		
		//转换为es对象
		List<ES_MODEL> l = getESModelFromDBModel(list);
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}
		
		for (ES_MODEL e : l) {
			if (e == null) {
				continue;
			}
			indexQuery = getIndexQuery(e);
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
	 * @return
	 */
	private IndexQuery getIndexQuery(ES_MODEL e) {
		if (e == null) {
			return null;
		}
		return new IndexQueryBuilder()
				.withId(getId(e))
				.withObject(e)
				.build();
	}
	
	/**
	 * 查询关联该别名的索引名称
	 * @param alias
	 * @return
	 */
	private List<String> getIndexNameByAlias(String alias) {
		return elasticsearchRestTemplate.execute(new ClientCallback<List<String>>() {
			@Override
			public List<String> doWithClient(RestHighLevelClient client) throws IOException {
				RestClient restClient = client.getLowLevelClient();
				Response response = restClient.performRequest(new Request("GET", '/' + alias + "/_alias/*"));
				String aliasResponse = EntityUtils.toString(response.getEntity());
				if (StringUtils.isEmpty(aliasResponse)) {
					return null;
				}
				
				JSONObject root = JSON.parseObject(aliasResponse);
				List<String> list = new ArrayList<>(root.size());
				for (Entry<String, Object> entry : root.entrySet()) {
					list.add(entry.getKey());
				}
				
				return list;
			}
		});
	}
	
	/**
	 * 查询关联该别名的所有索引
	 * @param alias
	 * @return
	 */
	private List<String> getIndexByAlias(String alias) {
		return getIndexNameByAlias(alias);
	}
	
	
	
	/**
	 * 添加新索引到该别名
	 * @param newIndexOperations
	 * @param alias
	 * @return
	 */
	private boolean addAlias(IndexOperations newIndexOperations, String alias) {
		if (newIndexOperations == null || StringUtils.isEmpty(alias)) {
			log.warn("addAlias error. with indexName or alias is empty.. with newIndexOperations={}, alias={}", newIndexOperations, alias);
			return false;
		}
		AliasQuery aliasQuery = new AliasQuery(alias);
		return newIndexOperations.addAlias(aliasQuery);
	}
	
	/**
	 * 删除索引
	 * @param newIndexOperations
	 */
	private void deleteIndex(IndexOperations newIndexOperations) {
		if (!newIndexOperations.exists()) {
			return;
		}
		newIndexOperations.delete();
	}
	
	private IndexCoordinates getIndexCoordinatesByIndexName(String indexName) {
		return IndexCoordinates.of(indexName);
	}

}
