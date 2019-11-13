/**
 * 
 */
package com.hp.core.elasticsearch.index;

import java.util.List;

import com.hp.core.elasticsearch.bean.IndexInfo;

/**
 * @author huangping
 * Mar 15, 2019
 */
public interface IESIndexService {

	/**
	 * 获取索引信息
	 * @return
	 */
	public IndexInfo getIndexInfo();
	
	/**
	 * 重建索引
	 */
	public void reBuildIndex();
	
	/**
	 * 根据id，批量更新
	 * @param ids
	 */
	public void updateByIds(List<Integer> ids);

	/**
	 * 根据id，插入
	 * @param ids
	 */
	public void insertByIds(List<Integer> ids);
	
	/**
	 * 根据id，删除
	 * @param ids
	 */
	public void deleteByIds(List<Integer> ids);
}
