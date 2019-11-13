/**
 * 
 */
package com.hp.core.common.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


/**
 * 分批次执行某一任务
 * @author ping.huang
 * 2016年12月23日
 */
public class BatchOpera {

	/**
	 * 对一个list进行分批次操作
	 * @param list
	 * @param callback
	 * @param params
	 * @return
	 */
	public static <T, R> List<R> batch(List<T> list, Callback<T, R> callback, Object... params) {
		return batch(list, 1000, callback, params);
	}
	
	/**
	 * 对一个list进行分批次操作
	 * @param <T>			list传入的对象
	 * @param <R>			返回值里面的list对象
	 * @param list			传入list
	 * @param maxSize		最大一次提交数量
	 * @param callback		回调函数
	 * @param params		额外参数
	 * @return
	 */
	public static <T, R> List<R> batch(List<T> list, int maxSize, Callback<T, R> callback, Object... params) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<R> resultList = new ArrayList<>(), l = null;
		int size = list.size();
		if (size <= maxSize) {
			l = callback.callback(list, 0, params);
			if (CollectionUtils.isNotEmpty(l)) {
				resultList.addAll(l);
			}
		} else {
			int lastPageSize = size % maxSize;
			int pageTotal = size / maxSize;
			if (lastPageSize > 0) {
				pageTotal += 1;
			}

			for (int i = 0; i < pageTotal; i++) {
				int begin = i * maxSize;
				int end = (i + 1) * maxSize < size ? (i + 1) * maxSize : size;
				l = callback.callback(list.subList(begin, end), i, params);
				if (CollectionUtils.isNotEmpty(l)) {
					resultList.addAll(l);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 回调函数
	 * @author huangping
	 * Apr 26, 2019
	 * @param <T>		请求的list对象
	 * @param <R>		返回的list对象
	 */
	public static interface Callback<T, R> {
		
		/**
		 * 回调方法
		 * @param list
		 * @param currentPage	当前页数（从0开始）
		 * @param params
		 * @return
		 */
		public List<R> callback(List<T> list, int currentPage, Object... params);
	}
}
