/**
 * 
 */
package com.my.core.test.annotation;

/**
 * @author huangping 2016年8月14日 上午2:09:08
 */
@HelloWorld(name = " 小明 ")
public class SayHello {

	
	public void sayHello(String name) {
		System.out.println(name + "say hello world!");
	}
}
