/**
 * 
 */
package com.hp.core.api.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @author huangping
 * 2016年7月24日 上午1:21:57
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 5567567268028639759L;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
