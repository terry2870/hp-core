/**
 * 
 */
package com.hp.core.common.beans;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 描述：
 * 
 * @author ping.huang
 * 2016年3月31日
 */
public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4976516540408695147L;
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
