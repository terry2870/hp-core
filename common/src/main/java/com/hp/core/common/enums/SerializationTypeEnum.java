/**
 * 
 */
package com.hp.core.common.enums;

/**
 * @author ping.huang
 * 2016年10月15日
 */
public enum SerializationTypeEnum {

	
	PROTOSTUFF(1, "Protostuff"),
	LINEBASED(2, "LineBased");
	
	private int type;
	private String name;
	
	private SerializationTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}
}
