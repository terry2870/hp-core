/**
 * 
 */
package com.hp.core.echarts.model;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.hp.core.common.beans.BaseBean;

/**
 * 抽象数据对象
 * T		data的数据类型
 * V		data里面的数据字段类型
 * @author huangping
 * 2018年10月10日
 */
public abstract class AbstractData<T, V> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7184374137097916807L;
	
	private Collection<T> data;

	/**
	 * 初始化
	 */
	public void initData() {
		if (this.data == null) {
			this.data = new ArrayList<>();
		}
	}
	
	/**
	 * 添加数据
	 * @param datas
	 */
	@SuppressWarnings("unchecked")
	public void addData(T... datas) {
		initData();
		this.data.addAll(Lists.newArrayList(datas));
	}
	
	/**
	 * 添加数据
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	public void addData2(V... values) {
		initData();
		for (V v : values) {
			this.data.add(convert(v));
		}
	}
	
	/**
	 * 转换为需要的类型
	 * @param v
	 * @return
	 */
	public abstract T convert(V v);

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}
	
}
