/**
 * 
 */
package com.hp.core.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.batch.BatchOpera;
import com.hp.core.common.batch.BatchOpera.Callback;
import com.hp.core.excel.bean.ExcelColumnBean;
import com.hp.core.excel.helper.ExcelHelper;

/**
 * @author huangping
 * May 25, 2020
 */
public class Tttt {

	
	public static void main(String[] args) {
		try {
			List<String> list = FileUtils.readLines(new File("/Users/huangping/Documents/test.txt"), "UTF-8");
			List<Object> dataList = new ArrayList<>();
			Map<String, Object> map = null;
			String[] arr = null;
			int i = 0;
			for (String str : list) {
				try {
					arr = StringUtils.splitByWholeSeparator(str, " |");
					map = new HashMap<>();
					map.put("mobile", getString(arr[0].trim()));
					map.put("sex", getString(arr[1].trim()));
					map.put("birthday", getString(arr[2].trim()));
					map.put("nickname", getString(arr[3].trim()));
					map.put("email", getString(arr[4].trim()));
					dataList.add(map);
					i++;
				} catch (Exception e) {
					System.out.println("error. with line=" + i);
					throw e;
				}
			}
			
			List<ExcelColumnBean> columns = new ArrayList<>();
			columns.add(new ExcelColumnBean("手机号码", "mobile"));
			columns.add(new ExcelColumnBean("性别", "sex"));
			columns.add(new ExcelColumnBean("出生日期", "birthday"));
			columns.add(new ExcelColumnBean("昵称", "nickname"));
			columns.add(new ExcelColumnBean("email", "email"));
			
			BatchOpera.batch(dataList, 100000, new Callback<Object, Object>() {

				@Override
				public List<Object> callback(List<Object> list, int currentPage, Object... params) {
					try {
						ExcelHelper.createExcel(new FileOutputStream("/Users/huangping/Documents/out_"+ currentPage +".xlsx"), columns, list);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getString(String str) {
		str = StringUtils.trimToEmpty(str);
		if (StringUtils.equalsIgnoreCase(str, "null")) {
			str = StringUtils.EMPTY;
		}
		
		//| 18701473171
		if (StringUtils.startsWith(str, "| ")) {
			str = StringUtils.replace(str, "| ", "");
		}
		return str;
	}
}
