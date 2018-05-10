package com.hp.core.common.utils;

import java.util.Random;

/**
 * 取随机数的工具
 * @author huangping<br />
 * 2013-2-20
 */
public class RandomUtil {

	/**
	 * 取随机数(0-1)
	 * 
	 * @return
	 */
	public static double getRandom() {
		Random rd = new Random();
		return rd.nextDouble();
	}

	/**
	 * 取min 到 max 之间的随机整数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandom(long min, long max) {
		if (min < 0 || max <= 0) {
			return 0;
		}
		if ((max - min) < 0) {
			return 0;
		}
		if (max == min) {
			return min;
		}
		max++;
		Random rd = new Random();
		long tmp = rd.nextInt((int) (max - min));
		return tmp + min;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandom(1, 2));
		}
	}

	/**
	 * 取指定长度的随机数
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandom(int size) {
		if (size <= 0) {
			return null;
		}
		double tmp = getRandom();
		tmp = Math.pow(10, size) * tmp;
		String result = String.valueOf((int) tmp);
		while (result.length() < size) {
			result = "0" + result;
		}
		return result;
	}
}
