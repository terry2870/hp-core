package com.hp.core.elasticsearch.index.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.hp.core.database.bean.PageModel;
import com.hp.core.elasticsearch.bean.IndexInfo;

/**
 * 使用查询大于某id的再pagel imit分页方式，来创建索引
 * Created by frank.sang on 2019/5/29.
 */
public abstract class AbstLargeThanIdIndexServiceImpl<T, E> extends AbstSimpleIndexServiceImpl<T, E> {
    private static Logger log = LoggerFactory.getLogger(AbstLargeThanIdIndexServiceImpl.class);

    /**
     * 获取
     *
     * @param param
     * @param page
     * @param largeThanId
     * @return
     */
    protected List<T> getDataListFromDB(T param, PageModel page, Long largeThanId) {
        return baseMapper.selectPageListByParamsAndLargeThanId(param, page, largeThanId);
    }

    /**
     * 获取主键名
     *
     * @return
     */
    protected Long getDbPrimaryKey(T t) {
        try {
            String value = BeanUtils.getProperty(t, "id");
            return Long.valueOf(value);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public void insertIntoES(IndexInfo indexInfo, String newIndexName) {
        T param = getQueryParams();

        int size = (int) getSize();
        int currentPage = 1;

        //永远都是查第一页
        PageModel page = PageModel.of(currentPage, size);
        List<T> list;
        List<IndexQuery> queries;
        Long largeThanId = 0L;
        while (true){
            log.info("insert to es data indexName={}. {}/{}", indexInfo.getIndexName(), currentPage, largeThanId);

            list = getDataListFromDB(param, page, largeThanId);

            //页数自加
            currentPage++;
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            //更新largeThanId
            largeThanId = refreshLargeThanId(list);
            if (largeThanId.equals(0L)) {
                log.error("insert to es data with getting largeThanId fail indexName={}");
                break;
            }

            //组装成索引对象
            queries = getIndexQueryDataListByDataList(list, newIndexName, indexInfo.getType());

            if (CollectionUtils.isEmpty(queries)) {
                continue;
            }

            //批量插入到新索引
            elasticsearchTemplate.bulkIndex(queries);
        };
    }

    private Long refreshLargeThanId(List<T> list) {
        T t;
        long largeThanId;
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

        return 0L;
    }
}
