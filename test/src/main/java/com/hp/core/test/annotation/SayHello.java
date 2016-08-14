/**
 * 
 */
package com.hp.core.test.annotation;

import org.springframework.stereotype.Component;

/**
 * @author huangping 2016年8月14日 上午2:09:08
 */
@Component
public class SayHello {

	
	public void sayHello(String name) {
		System.out.println(name + "say hello world!");
	}
}
