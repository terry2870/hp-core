/**
 * 
 */
package com.hp.core.database.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hp.core.database.enums.QueryTypeEnum;

/**
 * @author huangping
 * 2018年6月5日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface QueryType {

	/**
	 * 查询方式
	 * @return
	 */
	QueryTypeEnum value() default QueryTypeEnum.EQUALS;
}
