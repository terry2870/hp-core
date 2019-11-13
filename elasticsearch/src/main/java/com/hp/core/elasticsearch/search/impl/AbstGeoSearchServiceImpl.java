/**
 * 
 */
package com.hp.core.elasticsearch.search.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import com.hp.core.common.constants.ElasticsearchConstant;
import com.hp.core.common.utils.DistanceUtil;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.elasticsearch.bean.request.GeoDistanceRequest;
import com.hp.core.elasticsearch.bean.response.GeoDistanceResponse;

/**
 * 带有经纬度，距离搜索接口
 * @author huangping
 * Mar 18, 2019
 * @param <REQUEST>		请求的对象
 * @param <RESPONSE>	返回的对象
 */
public abstract class AbstGeoSearchServiceImpl<REQUEST extends GeoDistanceRequest, RESPONSE extends GeoDistanceResponse> extends AbstGeneralSearchServiceImpl<REQUEST, RESPONSE> {
	
	/*@Override
	public ScriptField withScriptField(REQUEST request) {
		if (request.getLongitude() == null || request.getLongitude().intValue() == 0 || request.getLatitude() == null || request.getLatitude().intValue() == 0) {
			return null;
		}
		// 有传入经纬度，计算距离
		Map<String, Object> params = new HashMap<>();
		params.put("lat", request.getLatitude().doubleValue());
		params.put("lon", request.getLongitude().doubleValue());

		return new ScriptField(CommonConstant.GEO_DISTANCE_NAME, new Script(Script.DEFAULT_SCRIPT_TYPE, "painless", "doc['point'].planeDistance(params.lat,params.lon)", params));
	}*/

	@Override
	public void dealResponseList(REQUEST request, List<RESPONSE> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		if (request.getLongitude() == null || request.getLongitude().intValue() == 0 || request.getLatitude() == null || request.getLatitude().intValue() == 0) {
			return;
		}
		
		//遍历，如果有返回距离，则距离格式化一下
		for (RESPONSE resp : list) {
			if (resp.getLatitude() == null || resp.getLatitude() == null) {
				continue;
			}
			resp.setDistance(GeoDistance.PLANE.calculate(request.getLatitude().doubleValue(), request.getLongitude().doubleValue(), resp.getLatitude().doubleValue(), resp.getLongitude().doubleValue(), DistanceUnit.METERS));
			resp.setDistanceFormat(DistanceUtil.changeDistanceNameForFresh(resp.getDistance()));
		}
	}
	
	@Override
	public void addQuery(REQUEST request, BoolQueryBuilder query, BoolQueryBuilder filter, NativeSearchQueryBuilder searchQuery) {
		if (request.getLongitude() != null && request.getLatitude() != null && request.getDistance() != null) {
			//距离查询
			filter.filter(QueryBuilders.geoDistanceQuery("point")//查询字段
					.point(request.getLatitude().doubleValue(), request.getLongitude().doubleValue())//设置经纬度
					.distance(request.getDistance().doubleValue(), DistanceUnit.METERS)//设置距离和单位（米）
					.geoDistance(GeoDistance.PLANE));
		}
		
		//城市
		if (request.getCityId() != null && request.getCityId().intValue() != 0) {
			filter.must(QueryBuilders.termQuery("cityId", request.getCityId().intValue()));
		}
		
	}
	
	/**
	 * 排序
	 * @param request
	 * @param searchQuery
	 * @param sortList
	 */
	@Override
	protected void withSort(REQUEST request, NativeSearchQueryBuilder searchQuery, List<OrderBy> sortList) {
		if (CollectionUtils.isEmpty(sortList)) {
			searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
			return;
		}
		
		boolean haveSortOrder = false;
		for (OrderBy orderBy : sortList) {
			if (ElasticsearchConstant.GEO_DISTANCE_NAME.equalsIgnoreCase(orderBy.getFieldName())) {
				//带距离
				if (request.getLongitude() != null && request.getLatitude() != null) {
					//距离排序
					searchQuery.withSort(SortBuilders.geoDistanceSort("point", request.getLatitude().doubleValue(), request.getLongitude().doubleValue())
							.unit(DistanceUnit.METERS)
							.order(SortOrder.fromString(orderBy.getDirection().toString()))
							);
				}
			} else if (ElasticsearchConstant.SORT_ORDER_SCORE.equals(orderBy.getFieldName())) {
				//按照分值排序
				searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
				haveSortOrder = true;
			} else {
				//其他字段排序
				searchQuery.withSort(SortBuilders.fieldSort(orderBy.getFieldName())
						.order(SortOrder.fromString(orderBy.getDirection().toString()))
						);
			}
		}
		
		if (!haveSortOrder) {
			//没有传入分值排序，默认加上
			searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
		}
	}

}
