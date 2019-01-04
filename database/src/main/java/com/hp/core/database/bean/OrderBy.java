/**
 * 
 */
package com.hp.core.database.bean;

import com.hp.core.database.enums.DirectionEnum;

/**
 * @author huangping
 * 2019年1月2日
 */
public class OrderBy {

	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 正序还是倒序
	 */
	private DirectionEnum direction;
	
	public OrderBy() {}
	
	public OrderBy(String fieldName, DirectionEnum direction) {
		this.fieldName = fieldName;
		this.direction = direction;
	}
	
	/**
	 * 构造
	 * @param fieldName
	 * @return
	 */
	public static OrderBy of(String fieldName) {
		return of(fieldName, DirectionEnum.ASC);
	}
	
	/**
	 * 构造
	 * @param fieldName
	 * @param direction
	 * @return
	 */
	public static OrderBy of(String fieldName, DirectionEnum direction) {
		return new OrderBy(fieldName, direction);
	}
	
	/**
	 * 构造
	 * @param fieldName
	 * @param direction
	 * @return
	 */
	public static OrderBy of(String fieldName, String direction) {
		return new OrderBy(fieldName, DirectionEnum.getDirectionEnumByText(direction));
	}

	public DirectionEnum getDirection() {
		return direction;
	}

	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public String toString() {
		return this.fieldName + " " + this.direction.toString();
	}
}
