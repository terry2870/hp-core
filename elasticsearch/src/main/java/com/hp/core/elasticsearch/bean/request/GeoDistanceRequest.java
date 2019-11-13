/**
 * 
 */
package com.hp.core.elasticsearch.bean.request;

import java.math.BigDecimal;

/**
 * @author huangping
 * Mar 18, 2019
 */
public class GeoDistanceRequest extends BaseSearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9012030209692185192L;

	/**
	 * GPS经度
	 */
	private BigDecimal longitude;
	
	/**
	 * GPS纬度
	 */
	private BigDecimal latitude;
	
	/**
	 * 距离
	 */
	private Double distance;
	
	/**
	 * 城市id
	 */
	private Integer cityId;

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
