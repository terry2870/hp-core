/**
 * 
 */
package com.hp.core.common.beans;

import java.math.BigDecimal;

/**
 * @author huangping
 * 2018年8月29日
 */
public class PositionInfoBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2659837486293760402L;

	/**
	 * 经度
	 */
	private BigDecimal longitude;

	/**
	 * 维度
	 */
	private BigDecimal latitude;
	
	/**
	 * 位置
	 */
	private String address;
	
	/**
	 * 经度（弧度）
	 */
	private double radLongitude;
	
	/**
	 * 维度（弧度）
	 */
	private double radLatitude;

	public PositionInfoBean() {}
	
	public PositionInfoBean(BigDecimal latitude, BigDecimal longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public PositionInfoBean(BigDecimal latitude, BigDecimal longitude, String address) {
		this(latitude, longitude);
		this.address = address;
	}
	
	public PositionInfoBean(double latitude, double longitude) {
		this(new BigDecimal(latitude), new BigDecimal(longitude));
	}
	
	public PositionInfoBean(double latitude, double longitude, String address) {
		this(new BigDecimal(latitude), new BigDecimal(longitude), address);
	}

	public BigDecimal getLongitude() {
		return longitude;
	}
	
	public double getLongitudeDouble() {
		return longitude == null ? 0D : longitude.doubleValue();
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}
	
	public double getLatitudeDouble() {
		return latitude == null ? 0D : latitude.doubleValue();
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRadLongitude() {
		return radLongitude;
	}

	public void setRadLongitude(double radLongitude) {
		this.radLongitude = radLongitude;
	}

	public double getRadLatitude() {
		return radLatitude;
	}

	public void setRadLatitude(double radLatitude) {
		this.radLatitude = radLatitude;
	}
}
