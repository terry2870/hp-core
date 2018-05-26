/**
 * 
 */
package com.hp.core.test.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author huangping
 * 2018年5月21日
 */
public class SelectPro {

	public SelectPro(String str) {
		
	}
	
	public static String getSelectSQL(Map<String, Object> param) {
		String sql = "select * from test_table where id=" + param.get("id");
		return sql;
	}
}
