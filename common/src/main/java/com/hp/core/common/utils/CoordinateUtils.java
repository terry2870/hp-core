package com.hp.core.common.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.PositionInfoBean;

/**
 * Created by frank.sang on 2018/1/31.
 */
public class CoordinateUtils {
	public static double pi = 3.1415926535897932384626;
	// a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
	public static double a = 6378245.0;
	// ee: 椭球的偏心率。
	public static double ee = 0.00669342162296594323;
	
	public static final String GPS = "gps";
	public static final String BAIDU = "baidu";
	public static final String GOOGLE = "google";
	public static final String GAODE = "gaode";
	public static final String QQ = "qq";
	public static final String MAPBOX = "mapbox";
	
	/**
	 * GPS转百度坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean GPS2Baidu(PositionInfoBean position) {
		return transform(position, GPS, BAIDU);
	}
	
	/**
	 * 百度转GPS坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean baidu2GPS(PositionInfoBean position) {
		return transform(position, BAIDU, GPS);
	}
	
	/**
	 * 百度转高德坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean baidu2Gaode(PositionInfoBean position) {
		return transform(position, BAIDU, GAODE);
	}
	
	/**
	 * 高德转百度坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean gaode2Baidu(PositionInfoBean position) {
		return transform(position, GAODE, BAIDU);
	}
	
	/**
	 * 百度转QQ坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean baidu2QQ(PositionInfoBean position) {
		return transform(position, BAIDU, QQ);
	}
	
	/**
	 * qq转百度坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean qq2Baidu(PositionInfoBean position) {
		return transform(position, QQ, BAIDU);
	}
	
	/**
	 * qq转GPS坐标
	 * @param position
	 * @return
	 */
	public static PositionInfoBean qq2GPS(PositionInfoBean position) {
		return transform(position, QQ, GPS);
	}
	
	/**
	 * 坐标转换
	 * @param position
	 * @param tansFrom
	 * @param transTo
	 * @return
	 */
	private static PositionInfoBean transform(PositionInfoBean position, String tansFrom, String transTo) {
		return transform(position.getLatitudeDouble(), position.getLongitudeDouble(), tansFrom, transTo);
	}
	
	/**
	 * 判断坐标是否是国外
	 * @param position
	 * @return
	 */
	public static boolean outOfChina(PositionInfoBean position) {
		return outOfChina(position.getLatitudeDouble(), position.getLongitudeDouble());
	}
	
	/**
	 * 判断坐标是否是国外
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347) {
			return true;
		}

		if (lat < 0.8293 || lat > 55.8271) {
			return true;
		}
		return false;
	}
	
	/**
	 * 坐标转换
	 * @param lat
	 * @param lon
	 * @param tansFrom
	 * @param transTo
	 * @return
	 */
	public static PositionInfoBean transform(double lat, double lon, String tansFrom, String transTo) {
		if(!isValidPos(new BigDecimal(lat), new BigDecimal(lon))){
			return new PositionInfoBean(lat, lon);
		}

		if (outOfChina(lat, lon)) {
			return new PositionInfoBean(lat, lon);
		}

		if (StringUtils.isBlank(tansFrom) || StringUtils.isBlank(transTo)) {
			return new PositionInfoBean(lat, lon);
		}
		
		if (tansFrom.equals(transTo)) {
			return new PositionInfoBean(lat, lon);
		}

		// 百度转国标"google", "gaode"
		if (isBd09(tansFrom) && isGcj02(transTo)) {
			return bd09_To_Gcj02(lat, lon);
		}

		// 百度转gps
		if (isBd09(tansFrom) && isWgs84(transTo)) {
			return bd09_To_Gps84(lat, lon);
		}

		// 国标转百度
		if (isGcj02(tansFrom) && isBd09(transTo)) {
			return gcj02_To_Bd09(lat, lon);
		}

		// GCJ-02 -> WGS-84
		if (isGcj02(tansFrom) && isWgs84(transTo)) {
			return gcj_To_Gps84(lat, lon);
		}

		// WGS-84 -> BD-09
		if (isWgs84(tansFrom) && isBd09(transTo)) {
			return gps84_To_Bd09(lat, lon);
		}

		// WGS-84 -> GCJ-02
		if (isWgs84(tansFrom) && isGcj02(transTo)) {
			return gps84_To_Gcj02(lat, lon);
		}

		return new PositionInfoBean(lat, lon);
	}

	/**
	 * 是否合法的坐标
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static boolean isValidPos(BigDecimal lat, BigDecimal lon) {
		if (BigDecimal.ZERO.equals(lat) || BigDecimal.ZERO.equals(lon)) {
			return false;
		}
		return lat.abs().compareTo(new BigDecimal(90)) <= 0
				&& lon.abs().compareTo(new BigDecimal(180)) <= 0;
	}


	/**
	 * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	private static PositionInfoBean gps84_To_Gcj02(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new PositionInfoBean(lat, lon);
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new PositionInfoBean(mgLat, mgLon);
	}

	/**
	 * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
	 */
	private static PositionInfoBean gcj_To_Gps84(double lat, double lon) {
		PositionInfoBean gps = transform(lat, lon);
		double lontitude = lon * 2 - gps.getLongitude().doubleValue();
		double latitude = lat * 2 - gps.getLatitude().doubleValue();
		return new PositionInfoBean(latitude, lontitude);
	}

	/**
	 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
	 *
	 * @param gg_lat
	 * @param gg_lon
	 */
	private static PositionInfoBean gcj02_To_Bd09(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new PositionInfoBean(bd_lat, bd_lon);
	}

	/**
	 * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
	 * bd_lat * @param bd_lon * @return
	 */
	private static PositionInfoBean bd09_To_Gcj02(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new PositionInfoBean(gg_lat, gg_lon);
	}

	/**
	 * (BD-09)-->84
	 *
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	private static PositionInfoBean bd09_To_Gps84(double bd_lat, double bd_lon) {
		PositionInfoBean gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
		PositionInfoBean map84 = gcj_To_Gps84(gcj02.getLatitude().doubleValue(), gcj02.getLongitude().doubleValue());
		return map84;

	}

	/**
	 * 84-->(BD-09)
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	private static PositionInfoBean gps84_To_Bd09(double lat, double lon) {
		PositionInfoBean gcj02 = gps84_To_Gcj02(lat, lon);
		PositionInfoBean map84 = gcj02_To_Bd09(gcj02.getLatitude().doubleValue(), gcj02.getLongitude().doubleValue());
		return map84;

	}

	private static PositionInfoBean transform(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new PositionInfoBean(lat, lon);
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new PositionInfoBean(mgLat, mgLon);
	}

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}

	private static boolean isBd09(String type) {
		return BAIDU.equals(type);
	}

	private static boolean isGcj02(String type) {
		return GOOGLE.equals(type) || GAODE.equals(type) || QQ.equals(type);
	}

	private static boolean isWgs84(String type) {
		return GPS.equals(type) || MAPBOX.equals(type);
	}

	public static void main(String[] args) {

//		// 北斗芯片获取的经纬度为WGS84地理坐标 31.426896,119.496145
//		PositionInfoBean gps = new PositionInfoBean(31.426896, 119.496145);
//		System.out.println("gps :" + gps);
//		PositionInfoBean gcj = gps84_To_Gcj02(gps.getLatitude().doubleValue(), gps.getLongitude().doubleValue());
//		System.out.println("gcj :" + gcj);
//		PositionInfoBean star = gcj_To_Gps84(gcj.getLatitude().doubleValue(), gcj.getLongitude().doubleValue());
//		System.out.println("star:" + star);
//		PositionInfoBean bd = gcj02_To_Bd09(gcj.getLatitude().doubleValue(), gcj.getLongitude().doubleValue());
//		System.out.println("bd  :" + bd);
//		PositionInfoBean gcj2 = bd09_To_Gcj02(bd.getLatitude().doubleValue(), bd.getLongitude().doubleValue());
//		System.out.println("gcj :" + gcj2);
		
		
		System.out.println(GPS2Baidu(new PositionInfoBean(23.78391, 114.639989)));
		
	}
}
