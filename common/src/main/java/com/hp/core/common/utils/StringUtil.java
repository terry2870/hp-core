/**
 * 
 */
package com.hp.core.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ping.huang 2016年7月30日
 */
public class StringUtil {

	static Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	private static final String DEFAULT_FORMATTER_NUM = "#.00";
	
	/**
	 * 驼峰转数据库字段名
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String toDBColumn(String fieldName) {
		return NameDefineUtil.camelCaseToUnderline(fieldName);
	}

	/**
	 * 保留指定位数的小数
	 * 
	 * @param d
	 * @param num
	 * @return
	 */
	public static String getNumber(double d, int num) {
		String str = "0.";
		for (int i = 0; i < num; i++) {
			str += "0";
		}
		DecimalFormat df = new DecimalFormat(str);
		return df.format(d);
	}

	/**
	 * 获取本机ip
	 * @return
	 */
	public static String fetchLocalIP() {
		String localIP = "127.0.0.1";
		DatagramSocket sock = null;
		try {
			SocketAddress socket_addr = new InetSocketAddress(InetAddress.getByName("1.2.3.4"), 1);
			sock = new DatagramSocket();
			sock.connect(socket_addr);

			localIP = sock.getLocalAddress().getHostAddress();
		} catch (Exception e) {
			log.error("fetchLocalIP error", e);
		} finally {
			sock.disconnect();
			sock.close();
			sock = null;
		}
		return localIP;
	}
	
	/**
     * 组装key
     * @param keyPattern
     * @param objects
     * @return
     */
    public static String getKey(String keyPattern, Object... objects) {
		if (ArrayUtils.isEmpty(objects) || objects[0] == null || "".equals(objects[0])) {
			return keyPattern;
		}
		Object[] obj = new Object[objects.length];
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null) {
				continue;
			}
			obj[i] = objects[i].toString();
		}
		return MessageFormat.format(keyPattern, obj);
	}
    
    /**
	 * 截取字符串
	 * 对表情，不会截一半的情况
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substring(String str, int start, int end) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.substring(str.offsetByCodePoints(0, start), str.offsetByCodePoints(0, end));
	}
	
	/**
	 * 千分位显示
	 * @param value
	 * @return
	 */
	public static String thousandCentimeter(Integer value) {
		if (value == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(value);
	}
	
	/**
	 * string 格式转为list
	 * @param uids
	 * @return
	 */
	public static Collection<Integer> string2Collection(String uids) {
		return string2Collection(uids, ",");
	}
	
	/**
	 * string 格式转为list
	 * @param uids
	 * @param separatorChars
	 * @return
	 */
	public static Collection<Integer> string2Collection(String uids, String separatorChars) {
		if (StringUtils.isEmpty(uids)) {
			return null;
		}
		String[] arr = StringUtils.split(uids, separatorChars);
		if (ArrayUtils.isEmpty(arr)) {
			return null;
		}
		List<Integer> uidList = new ArrayList<>(arr.length);
		for (String uid : arr) {
			uidList.add(Integer.valueOf(uid));
		}
		return uidList;
	}
	
	/**
	 * 抽奖
	 * @param arr
	 * @return	(-1:未中奖；大于0，则中奖对应索引)
	 */
	public static int goodLuck(List<Double> arr) {
		if (CollectionUtils.isEmpty(arr)) {
			return -1;
		}
		
		//随机一个 0-1之间数字
		double randomNum = RandomUtils.nextDouble(0, 1);
		//循环判断，该数字落在 哪个区间
		double start = 0, end = 0;
		for (int i = 0; i < arr.size(); i++) {
			end += arr.get(i);
			if (randomNum >= start && randomNum <= end) {
				//落在此区间，中此奖项
				return i;
			}
			
			//未落在区间，下一轮循环
			start += arr.get(i);
		}
		
		//未中奖
		return -1;
	}
	
	/**
	 * 截取字符串，（中文算两个字符）
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subTextString(String str, int len) {
		return subTextString(str, len, StringUtils.EMPTY);
	}
	
	/**
	 * 截取字符串，（中文算两个字符）
	 * @param str
	 * @param len
	 * @param endWith
	 * @return
	 */
	public static String subTextString(String str, int len, String endWith) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (str.length() < len / 2) {
			return str;
		}
		int count = 0;
		StringBuffer sb = new StringBuffer();
		String[] ss = str.split("");
		for (int i = 0; i < ss.length; i++) {
			try {
				count += ss[i].getBytes("GBK").length;
			} catch (UnsupportedEncodingException e) {
			}
			sb.append(ss[i]);
			if (count >= len) {
				break;
			}
		}
		// 不需要显示...的可以直接return sb.toString();
		return (sb.toString().length() < str.length()) ? sb.append(endWith).toString() : str;
	}
	
	/**
	 * 保留两个小数
	 * @param value
	 * @return
	 */
	public static String retain2Decimal(int value, String... format) {
		return retain2Decimal((float) value, format);
	}
	
	/**
	 * 保留两个小数
	 * @param value
	 * @return
	 */
	public static String retain2Decimal(long value, String... format) {
		return retain2Decimal((float) value, format);
	}
	
	/**
	 * 保留两个小数
	 * @param value
	 * @return
	 */
	public static String retain2Decimal(float value, String... format) {
		return retain2Decimal((double) value, format);
	}
	
	/**
	 * 保留两个小数
	 * @param value
	 * @return
	 */
	public static String retain2Decimal(double value, String... format) {
		DecimalFormat df = new DecimalFormat(getFormat(format));
		String str = df.format(value);
		if (str.startsWith(".")) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * 获取默认格式
	 * @param format
	 * @return
	 */
	private static String getFormat(String... format) {
		if (format == null || format.length == 0 || StringUtils.isEmpty(format[0])) {
			return DEFAULT_FORMATTER_NUM;
		}
		return format[0];
	}
}
