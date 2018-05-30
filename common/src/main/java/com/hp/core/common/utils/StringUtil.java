/**
 * 
 */
package com.hp.core.common.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ping.huang 2016年7月30日
 */
public class StringUtil {

	static Logger log = LoggerFactory.getLogger(StringUtil.class);
	
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
}
