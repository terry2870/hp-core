/**
 * 
 */
package com.hp.core.zookeeper.bean;

import java.util.List;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author ping.huang 2016年12月11日
 */
public class ZKNode extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2971597390743055242L;

	/**
	 * 节点路径
	 */
	private String path;
	/**
	 * 节点的值
	 */
	private String value;

	/**
	 * 节点版本
	 */
	private Integer version;

	/**
	 * 子节点列表
	 */
	private List<ZKNode> nodes;
	
	

	/**
	 * @param path
	 * @param value
	 * @param version
	 */
	public ZKNode(String path, String value, Integer version) {
		super();
		this.path = path;
		this.value = value;
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<ZKNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ZKNode> nodes) {
		this.nodes = nodes;
	}
}
