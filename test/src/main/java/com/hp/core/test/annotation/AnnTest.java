/**
 * 
 */
package com.hp.core.test.annotation;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangping 2016年8月14日 上午2:09:28
 */
public class AnnTest {
	
	static Logger log = LoggerFactory.getLogger(AnnTest.class);

	Ii i;
	
	public void parseMethod(Class<?> clazz) {
		Object obj;
		try {
			// 通过默认构造方法创建一个新的对象
			//obj = clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
			HelloWorld say = clazz.getAnnotation(HelloWorld.class);
			
//			for (Method method : clazz.getDeclaredMethods()) {
//				HelloWorld say = method.getAnnotation(HelloWorld.class);
//				String name = "";
//				if (say != null) {
//					name = say.name();
//					System.out.println(name);
//					method.invoke(obj, name);
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void aa(String str) {
		StackTraceElement[] temp=Thread.currentThread().getStackTrace();
		StackTraceElement a=(StackTraceElement)temp[1];
		log.info("----from--"+a.getMethodName()+"--method----------to use-refreshcart--------");
	}

	public static void main(String[] args) {
		AnnTest t = new AnnTest();
//		t.parseMethod(SayHello.class);
		t.aa("124");
		
		
	}
}
