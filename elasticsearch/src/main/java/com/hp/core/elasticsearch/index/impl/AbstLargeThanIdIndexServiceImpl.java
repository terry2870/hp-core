package com.hp.core.elasticsearch.index.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.elasticsearch.bean.IndexInfo;

/**
 * 使用查询大于某id的再pagel imit分页方式，来创建索引
 * @author huangping
 * Jul 7, 2020
 * @param <DB_MODEL>
 * @param <ES_MODEL>
 */
public abstract class AbstLargeThanIdIndexServiceImpl<DB_MODEL, ES_MODEL> extends AbstSimpleIndexServiceImpl<DB_MODEL, ES_MODEL> {
    private static Logger log = LoggerFactory.getLogger(AbstLargeThanIdIndexServiceImpl.class);

    /**
     * 获取比这个id大的数据
     * @param builders
     * @param page
     * @param largeThanId
     * @return
     */
    protected List<DB_MODEL> getDataListFromDB(SQLBuilders builders, PageModel page, Integer largeThanId) {
        return baseMapper.selectListByLargeThanId(largeThanId, builders);
    }

    /**
     * 获取主键名
     *
     * @return
     */
    protected Integer getDbPrimaryKey(DB_MODEL t) {
        try {
            String value = BeanUtils.getProperty(t, "id");
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void insertIntoES(IndexInfo indexInfo, IndexCoordinates newIndexCoordinates) {
    	SQLBuilders builders = getSQLBuilders();

        int size = (int) getSize();
        int currentPage = 1;

        //永远都是查第一页
        PageModel page = PageModel.of(currentPage, size);
        List<DB_MODEL> list;
        List<IndexQuery> queries;
        Integer largeThanId = 0;
        while (true){
            log.info("insert to es data indexName={}. {}/{}", indexInfo.getIndexName(), currentPage, largeThanId);

            list = getDataListFromDB(builders, page, largeThanId);

            //页数自加
            currentPage++;
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            //更新largeThanId
            largeThanId = refreshLargeThanId(list);
            if (largeThanId.equals(0)) {
                log.error("insert to es data with getting largeThanId fail indexName={}");
                break;
            }

            //组装成索引对象
            queries = getIndexQueryDataListByDataList(list);

            if (CollectionUtils.isEmpty(queries)) {
                continue;
            }

            //批量插入到新索引
            elasticsearchRestTemplate.bulkIndex(queries, newIndexCoordinates);
        };
    }

    private Integer refreshLargeThanId(List<DB_MODEL> list) {
    	DB_MODEL t;
        int largeThanId;
        for (int i = list.size() - 1; i >= 0; i--) {
            t = list.get(i);
            if (t == null) {
                continue;
            }
            largeThanId = getDbPrimaryKey(t);
            if (largeThanId > 0) {
                return largeThanId;
            }
        }

        return 0;
    }
}
