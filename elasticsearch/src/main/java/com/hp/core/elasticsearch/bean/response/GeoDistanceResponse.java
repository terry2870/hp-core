/**
 * 
 */
package com.hp.core.elasticsearch.bean.response;

import java.math.BigDecimal;

import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.ScriptedField;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * Mar 18, 2019
 */
public class GeoDistanceResponse extends BaseBean {

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
	 * 城市id
	 */
	private Integer cityId;
	
	/**
	 * 距离
	 */
	@ScriptedField
	private Double distance;
	
	/**
	 * 格式化的距离
	 */
	private String distanceFormat;
	
	/**
	 * 经纬度
	 * 格式（lat,lon）
	 */
	@GeoPointField
	private String point;

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

	public String getDistanceFormat() {
		return distanceFormat;
	}

	public void setDistanceFormat(String distanceFormat) {
		this.distanceFormat = distanceFormat;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
}
