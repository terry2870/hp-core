/**
 * 
 */
package com.hp.core.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author huangping
 * Jun 28, 2020
 */
public class TimeDiff {

	
	public static int findMinDifference(List<String> timePoints) {
		//min是时间差值
		int min = Integer.MAX_VALUE;

		//重新定义一个list
		List<Integer> minute = new ArrayList<>();

		//把分钟化成整形秒数
		for (int i = 0; i < timePoints.size(); i++) {
			minute.add(Integer.valueOf(timePoints.get(i).substring(0, 2)) * 60 + Integer.valueOf(timePoints.get(i).substring(3, 5)));
		}

		//排序
		Collections.sort(minute);
		
		//特殊情况
		//还需要把第一个时间+24小时，放在末尾
		minute.add(minute.get(0) + 1440);

		//计算每个相邻两个时间之间的差，并取最小的保存在min中
		for (int i = 0; i < minute.size() - 1; i++) {
			min = Math.min(min, minute.get(i + 1) - minute.get(i));
		}

		return min;
	}
	
	public static void main(String[] args) {
		System.out.println(findMinDifference(Lists.newArrayList("23:58", "00:01", "10:20")));
	}
}
