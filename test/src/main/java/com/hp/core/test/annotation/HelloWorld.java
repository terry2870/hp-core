/**
 * 
 */
package com.hp.core.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangping 2016年8月14日 上午2:08:33
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
@Target(ElementType.TYPE)
public @interface HelloWorld {

	public String name() default "";
}
