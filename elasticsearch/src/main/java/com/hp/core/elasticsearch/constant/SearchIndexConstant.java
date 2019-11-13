/**
 * 
 */
package com.hp.core.elasticsearch.constant;
/**
 * @author huangping
 * Mar 14, 2019
 */
public class SearchIndexConstant {

	/**
	 * 索引别名的后缀
	 */
	private static final String INDEX_ALIAS_SUFFIX = "_alias";
	
	/**
	 * 索引type的后缀
	 */
	private static final String INDEX_TYPE_SUFFIX = "_type";
	
	/**
	 * 获取别名
	 * @param indexName
	 * @return
	 */
	public static String getAlias(String indexName) {
		return indexName + INDEX_ALIAS_SUFFIX;
	}
	
	/**
	 * 获取索引type
	 * @param indexName
	 * @return
	 */
	public static String getType(String indexName) {
		return indexName + INDEX_TYPE_SUFFIX;
	}

	/**
	 * index的settings中排除的部分
	 * 根据index/_settings返回的结果中包含如下部分，但是重建时不需要
	 */
	public static final String[] INDEX_SETTINGS_EXCEPT_KEYS = new String[]{"index.provided_name", "index.uuid", "index.version.created", "index.creation_date"};
}
