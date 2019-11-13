/**
 * 
 */
package com.hp.core.common.constants;

/**
 * @author huangping
 * Nov 11, 2019
 */
public class ElasticsearchConstant {

	//搜索引擎使用，按照分值排序
	public static final String SORT_ORDER_SCORE = "SORT_ORDER_SCORE";
	
	//搜索引擎，查询距离时，使用的字段
	public static final String GEO_DISTANCE_NAME = "distance";
	
	/**
	 * ES 分词规则
	 */
	public static final String ANALYZER_IK_MAX = "ik_max_word";
	public static final String ANALYZER_IK_SMART = "ik_smart";
	public static final String ANALYZER_STANDARD = "standard";
}
