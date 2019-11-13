/**
 * 
 */
package com.hp.core.common.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.PositionInfoBean;

/**
 * @author huangping 2018年8月29日
 */
public class DistanceUtil {

	private static Logger log = LoggerFactory.getLogger(DistanceUtil.class);

	/**
	 * 地球半径（米）
	 */
	private static final double EARTHS_RADIUS_M = 6371.01 * 1000;

	/**
	 * 最小维度
	 */
	private static final double MIN_LAT = -90d;
	/**
	 * 最大维度
	 */
	private static final double MAX_LAT = 90d;
	/**
	 * 最小经度
	 */
	private static final double MIN_LON = -180d;
	/**
	 * 最大经度
	 */
	private static final double MAX_LON = 180d;

	/**
	 * 计算两点之间距离
	 * 
	 * @param position1
	 * @param position2
	 * @return
	 */
	public static Double getDistance(PositionInfoBean position1, PositionInfoBean position2) {
		if (!checkPosition(position1) || !checkPosition(position2)) {
			return null;
		}
		
		//如果两个经纬度一样，就返回0
		if (position1.getLongitude().equals(position2.getLongitude()) && position1.getLatitude().equals(position2.getLatitude())) {
			return 0D;
		}
		
		//度数转弧度
		if (position1.getRadLongitude() == 0) {
			position1.setRadLongitude(Math.toRadians(position1.getLongitude().doubleValue()));
		}
		if (position1.getRadLatitude() == 0) {
			position1.setRadLatitude(Math.toRadians(position1.getLatitude().doubleValue()));
		}
		if (position2.getRadLongitude() == 0) {
			position2.setRadLongitude(Math.toRadians(position2.getLongitude().doubleValue()));
		}
		if (position2.getRadLatitude() == 0) {
			position2.setRadLatitude(Math.toRadians(position2.getLatitude().doubleValue()));
		}
		
		Double result = Math.acos(Math.sin(position1.getRadLatitude()) * Math.sin(position2.getRadLatitude()) +
                Math.cos(position1.getRadLatitude()) * Math.cos(position2.getRadLatitude()) *
                        Math.cos(position1.getRadLongitude() - position2.getRadLongitude())) * EARTHS_RADIUS_M;
		return NumberUtils.isCreatable(result.toString()) ? result : 0D;
	}

	/**
	 * 检查位置信息
	 * 
	 * @param position
	 * @return
	 */
	private static boolean checkPosition(PositionInfoBean position) {
		if (position == null || position.getLongitude() == null || position.getLongitude().intValue() == 0 || position.getLatitude() == null || position.getLatitude().intValue() == 0) {
			log.warn("checkBounds1 error. with position is error. with position={}", position);
			return false;
		}
		if (position.getLatitude().doubleValue() < MIN_LAT || position.getLatitude().doubleValue() > MAX_LAT || position.getLongitude().doubleValue() < MIN_LON || position.getLongitude().doubleValue() > MAX_LON) {
			log.warn("checkBounds2 error. with position is error. with position={}", position);
			return false;
		}
		return true;
	}

	/**
	 * 距离显示转换
	 * @param dis
	 * @return
	 */
	public static String changeDistanceName(Double dis) {
		return changeDistanceNameForFresh(dis);
	}
	
	/**
	 * 距离显示转换
	 * @param dis
	 * @return
	 */
	public static String changeDistanceNameForFresh(Double dis) {
		if (dis == null) {
			return "";
		}
		double d = dis.doubleValue();
		if (d < 1000) {
			return (int) d + "m";
		} else if (d >= 1000 && d < 100000) {
			String str = StringUtil.retain2Decimal(d / 1000, "#.0");
			return str + "km";
		} else if (d >= 100000 && d < 500000) {
			return (int) (d / 1000) + "km";
		} else {
			return "far away";
		}
	}
	
	public static void main(String[] args) {
		PositionInfoBean position1 = new PositionInfoBean(31.9453770392, 118.7264807057);
		PositionInfoBean position2 = new PositionInfoBean(31.9433770392, 118.7261807057);
		System.out.println(getDistance(position1, position2));
		
		
		Double d = 123232323.23232;
		System.out.println(NumberUtils.isCreatable(d.toString()));
	}

}
