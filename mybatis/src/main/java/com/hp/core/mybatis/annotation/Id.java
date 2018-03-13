/**
 * 
 */
package com.hp.core.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定数据库的主键字段
 * @author huangping
 * 2018年1月26日
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {

	/**
	 * 主键的数据库字段名
	 * @return
	 */
	String value();
}
