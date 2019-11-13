/**
 * 
 */
package com.hp.core.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangping
 * Mar 21, 2019
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchIndex {
	
	/**
	 * 是否自动重建索引
	 * @return
	 */
	boolean autoRebuild() default true;
	
	/**
	 * 重建索引cron表达式
	 * @return
	 */
	String rebuildCron() default "";
}
