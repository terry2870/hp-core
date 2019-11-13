/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Feature extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2602009221487854158L;

	/**
	 * 保存为图片
	 */
	private SaveAsImage saveAsImage = new SaveAsImage();
	
	/**
	 * 配置项还原
	 */
	private Restore restore = new Restore();
	
	/**
	 * 数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新
	 */
	private DataView dataView = new DataView();
	
	/**
	 * 数据区域缩放。目前只支持直角坐标系的缩放
	 */
	private DataZoom dataZoom = new DataZoom();
	
	/**
	 * 动态类型切换
	 */
	private MagicType magicType = new MagicType();

	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}

	public Feature setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
		return this;
	}

	public Restore getRestore() {
		return restore;
	}

	public Feature setRestore(Restore restore) {
		this.restore = restore;
		return this;
	}

	public DataView getDataView() {
		return dataView;
	}

	public Feature setDataView(DataView dataView) {
		this.dataView = dataView;
		return this;
	}

	public DataZoom getDataZoom() {
		return dataZoom;
	}

	public Feature setDataZoom(DataZoom dataZoom) {
		this.dataZoom = dataZoom;
		return this;
	}

	public MagicType getMagicType() {
		return magicType;
	}

	public Feature setMagicType(MagicType magicType) {
		this.magicType = magicType;
		return this;
	}
}
