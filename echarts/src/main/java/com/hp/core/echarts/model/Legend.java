/**
 * 
 */
package com.hp.core.echarts.model;

import java.util.Collection;

import com.hp.core.echarts.enums.LegendTypeEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Legend extends AbstractData<LegendData, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5779895953240736603L;

	/**
	 * 图例的类型
	 * 默认值plain
	 * 可选（plain，scroll）
	 */
	private LegendTypeEnum type;
	
	/**
	 * 组件 ID
	 */
	private String id;
	
	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 图例的数据数组
	 * 继承父类
	 */
	//private Collection<LegendData> data;

	public Legend() {}
	
	public Legend(Collection<LegendData> data) {
		super.setData(data);
	}
	
	public Legend(String... datas) {
		initData();
		addData2(datas);
	}

	public LegendTypeEnum getType() {
		return type;
	}

	public Legend setType(LegendTypeEnum type) {
		this.type = type;
		return this;
	}

	public String getId() {
		return id;
	}

	public Legend setId(String id) {
		this.id = id;
		return this;
	}

	public Boolean getShow() {
		return show;
	}

	public Legend setShow(Boolean show) {
		this.show = show;
		return this;
	}

	@Override
	public LegendData convert(String v) {
		return new LegendData(v);
	}
}
