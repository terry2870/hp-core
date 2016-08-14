/**
 * 
 */
package com.hp.core.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * @author huangping 2016年8月14日 下午2:47:05
 */
public class ProxyTest {

	public static void main(String[] args) {
		Object[] elements = new Object[1];
		//RpcProxyFactory
		UUID.randomUUID();
		// fill elements with proxies for the integers 1 ... 1000
		for (int i = 0; i < elements.length; i++) {
			Integer value = i + 1;
			Class<?>[] interfaces = value.getClass().getInterfaces();
			InvocationHandler handler = new TraceHandler(value);
			Object proxy = Proxy.newProxyInstance(null, interfaces, handler);
			elements[i] = proxy;
		}

		// construct a random integer
		Integer key = new Random().nextInt(elements.length) + 1;

		// search for the key
		int result = Arrays.binarySearch(elements, key);

		// print match if found
		if (result >= 0)
			System.out.println(elements[result]);
	}

	static class TraceHandler implements InvocationHandler {
		/**
		 * Constructs a TraceHandler
		 * 
		 * @param t
		 *            the implicit parameter of the method call
		 */
		public TraceHandler(Object t) {
			target = t;
		}

		public Object invoke(Object proxy, Method m, Object[] args) throws Throwable// 此方法在代理类中
		{ // 的方法被调用时均会被调用
			// print implicit argument
			System.out.print(target);
			// print method name
			System.out.print("." + m.getName() + "(");
			// print explicit arguments
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					System.out.print(args[i]);
					if (i < args.length - 1)
						System.out.print(", ");
				}
			}
			System.out.println(")");

			// invoke actual method
			// return new Integer("123");
			return m.invoke(target, args);
		}

		private Object target;
	}
}
