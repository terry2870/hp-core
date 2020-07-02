/**
 * 
 */
package com.hp.core.test;

/**
 * @author huangping Jun 28, 2020
 */
public class SearchMaxNum {

	public static int patitionMaxNum(int number) {
		char[] c = (number + "").toCharArray();
		for (int i = 0; i < c.length; i++) {
			// 找到最大数字的位置
			int max = i;
			for (int j = i; j < c.length; j++) {
				if (c[j] > c[max]) {
					max = j;
				}
			}
			// 判断最大值的位置是不是i;是则不换，否则换并return
			if (max != i) {
				char[] result = swap(c, i, max);
				String string = result[0] + "";
				for (int j = 1; j < result.length; j++) {
					string += result[j];
				}
				return Integer.valueOf(string);
			}
		}
		return number;

	}

	/**
	 * 交换
	 * @param c
	 * @param i
	 * @param j
	 * @return
	 */
	public static char[] swap(char[] c, int i, int j) {
		char tmp = c[i];
		c[i] = c[j];
		c[j] = tmp;
		return c;
	}

	public static void main(String[] args) {
		int data = 1234;// 1234
		System.out.println(patitionMaxNum(data));

	}
}
