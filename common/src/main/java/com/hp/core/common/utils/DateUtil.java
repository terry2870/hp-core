package com.hp.core.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间转换帮助类
 *
 */
public class DateUtil {

	static Logger log = LoggerFactory.getLogger(DateUtil.class);

	// 基础(通用)的FORMAT类型,
	public static final String NORMAL_FORMAT = "yyyy-MM-dd";

	// 完整格式的时间
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 默认格式的时间
	public static final String DEFAULT_DATE_TIME_FORMAT = DATE_TIME_FORMAT;
	
	//一天的秒数
	public static final int ONE_DAY_SECONDS = 86400;

	/**
	 * 判断当前时间是不是在22点到第二天8点之间
	 * 
	 * @return
	 */
	public static boolean isNotDisturbTime() {
		String time = DateUtil.getToday("HH:mm:ss");
		return time.compareTo("22:00:00") > 0 || time.compareTo("08:00:00") < 0;
		// return time.compareTo("18:00:00") > 0 || time.compareTo("08:00:00") <
		// 0;
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentDateString() {
		return getToday(NORMAL_FORMAT);
	}
	
	/**
	 * 获取当前时间格式
	 * @return
	 */
	public static String getCurrentDateTimeString() {
		return getToday(DATE_TIME_FORMAT);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间, 并且将当前时间转换称INT类型
	 *
	 * @return 当前时间的INT类型
	 */
	public static int getCurrentTimeSeconds() {
		long longTime = getCurrentTimeMilliSeconds();
		return (int) (longTime / 1000);
	}
	
	/**
	 * 获取当前时间的毫秒数
	 * @return
	 */
	public static long getCurrentTimeMilliSeconds() {
		return System.currentTimeMillis();
	}

	/**
	 * int 转 date
	 * @param time
	 * @return
	 */
	public static Date int2Date(Integer time) {
		if (time == null || time == 0) {
			return null;
		}
		return new Date((long) time * 1000);
	}
	
	/**
	 * 将通过getTime获得的int型的时间，转换成format类型的时间，如"2015-08-09"
	 *
	 * @param time
	 *            int型的时间戳
	 * @param format
	 *            需要转化的类型 “yyyy-MM-dd”
	 * @return String "2015-08-09"
	 */
	public static String int2DateStr(Integer time, String... format) {
		if (time == null || time.intValue() == 0) {
			return "";
		}
		Date date = new Date((long) time * 1000);
		return dateToString(date, format);
	}
	
	/**
	 * 将通过getTime获得的long型的时间，转换成format类型的时间，如"2015-08-09"
	 *
	 * @param time
	 *            long型的时间戳
	 * @param format
	 *            需要转化的类型 “yyyy-MM-dd”
	 * @return String "2015-08-09"
	 */
	public static String long2DateStr(Long time, String... format) {
		if (time == null || time.longValue() == 0) {
			return "";
		}
		Date date = new Date(time.longValue());
		return dateToString(date, format);
	}

	/**
	 * 获取字符串格式的时间秒数
	 * 
	 * @param time
	 * @return
	 */
	public static int string2Int(String time, String... format) {
		if (StringUtils.isEmpty(time)) {
			return 0;
		}
		long l = string2Long(time, format);
		return (int) (l / 1000);
	}
	
	/**
	 * string格式转为时间戳（毫秒）
	 * @param time
	 * @param format
	 * @return
	 */
	public static long string2Long(String time, String... format) {
		if (StringUtils.isEmpty(time)) {
			return 0;
		}
		Date d = string2Date(time, format);
		if(d == null){
			return 0L;
		}
		return d.getTime();

	}

	/**
	 * 根据字符串和格式获取date对象
	 */
	public static Date string2Date(String time, String... format) {
		if (StringUtils.isEmpty(time) || "0000-00-00".equals(time)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormatter(format));
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			log.error("string2Date error. with time={}, format={}", time, format);
		}
		return date;

	}

	/**
	 * 
	 * 返回string格式的当前时间
	 * @param format
	 * @return
	 */
	public static String getToday(String... format) {
		return dateToString(getCurrentDate(), format);
	}
	
	/**
	 * 返回string格式的当前时间
	 * @param format
	 * @return
	 */
	public static String getTodayString(String... format) {
		return getToday(format);
	}
	
	/**
	 * 返回int格式的当前时间
	 * @param format
	 * @return
	 */
	public static int getTodayInt(String... format) {
		return string2Int(dateToString(getCurrentDate(), format), format);
	}
	
	/**
	 * 返回date类型的当前时间
	 * @return
	 */
	public static Date getTodayDate() {
		return getCurrentDate();
	}

	/**
	 * ͳDate型日期转换为String型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String... format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormatter(format));
		return sdf.format(date);
	}
	
	/**
	 * 对日期进行加减
	 * @param date 被转换的日期
	 * @param type 转换类型(y-年,M-月,d-日, H-小时, m-分钟, s-秒)
	 * @param offset 转换的单位
	 * @param simpleDateFormat 日期格式
	 * @return
	 */
	public static String dateAdd(String date, String type, int offset, String... simpleDateFormat) {
		Date d = dateAdd(string2Date(date, simpleDateFormat), type, offset);
		return dateToString(d, simpleDateFormat);
	}
	
	/**
	 * 对日期进行加减
	 * @param date 被转换的日期
	 * @param type 转换类型(y-年,M-月,d-日, H-小时, m-分钟, s-秒)
	 * @param offset 转换的单位
	 * @return
	 */
	public static Date dateAdd(Date date, String type, int offset) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(type)) {
			return date;
		}
		if (offset == 0) {
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("y")) {
			cal.add(Calendar.YEAR, offset);
		} else if (type.equals("M")) {
			cal.add(Calendar.MONTH, offset);
		} else if (type.equals("d")) {
			cal.add(Calendar.DATE, offset);
		} else if (type.equals("H")) {
			cal.add(Calendar.HOUR, offset);
		} else if (type.equals("m")) {
			cal.add(Calendar.MINUTE, offset);
		} else if (type.equals("s")) {
			cal.add(Calendar.SECOND, offset);
		}
		return cal.getTime();
	}
	
	/**
	 * 对日期进行加减
	 * @param date 被转换的日期
	 * @param type 转换类型(y-年,M-月,d-日, H-小时, m-分钟, s-秒)
	 * @param offset 转换的单位
	 * @return
	 */
	public static int dateAdd(int date, String type, int offset) {
		Date d = dateAdd(new Date((long)date * 1000), type, offset);
		return (int) (d.getTime() / 1000);
	}
	
	private static String getDateFormatter(String... format) {
		return ArrayUtils.isEmpty(format) ? DEFAULT_DATE_TIME_FORMAT : format[0];
	}
	
	/**
	 * 转换为可视化时间
	 * @param time
	 * @return
	 */
	public static String getDisplayTime(Integer time) {
		if (time == null || time.intValue() == 0) {
			return "";
		}
		Date begin = new Date(((long) time) * 1000);
		Date end = new Date();
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long dayDiff = between / (24 * 3600);
		long hourDiff = between % (24 * 3600) / 3600;
		long minuteDiff = between % 3600 / 60;
		long secondDiff = between % 60 / 60;

		// 1天<=时间, 显示 [06.23.2016格式]
		if (dayDiff >= 1) {
			return int2DateStr(time, "dd.MM.yyyy");
		}
		// 1小时<时间<24小时, 显示 [n小时前]
		else if (hourDiff < 24 && hourDiff > 1) {
			return hourDiff + "小时前";
		}
		// 1分钟<时间<60分钟, 显示 [n分钟前]
		else if (minuteDiff < 60 && minuteDiff > 1) {
			return minuteDiff + "分钟前";
		}
		// 时间<=1分钟 , 显示 [刚刚]
		else if (secondDiff < 1) {
			return "刚刚";
		}

		return null;
	}
	
	/**
	 * 获取UTC时间
	 * @return
	 */
	public static int getUTCTimeForInt() {
		Calendar cal = Calendar.getInstance();
		int offset = cal.get(Calendar.ZONE_OFFSET);
	   	//获得夏令时  时差
	   	int dstoff = cal.get(Calendar.DST_OFFSET);
	   	cal.add(Calendar.MILLISECOND, - (offset + dstoff));
	   	return (int) (cal.getTimeInMillis() / 1000);
	}

}
