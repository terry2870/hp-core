/**
 * 
 */
package com.hp.core.mybatis.autocreate;
/**
 * @author huangping
 * 2018年5月31日
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.NameDefineUtil;

public class TableBeanHelper {

	/**
	 * 根据表名，获取表的属性
	 * @param jdbc
	 * @param tableName
	 * @return
	 */
	public static TableBean getTableBeanByTableName(JdbcTemplate jdbc, String tableName) {
		TableBean table = new TableBean();
		table.setTableName(tableName);
		table.setModelName(NameDefineUtil.underlineToCamelCase(tableName));
		table.setModelNameFirstLow(NameDefineUtil.underlineToCamelCase(tableName, false));
		List<ColumnBean> columnList = null;
		ColumnBean column = null;
		List<Map<String, Object>> list = jdbc.queryForList("select LCASE(table_name) table_name, LCASE(column_name) column_name, is_nullable, data_type, column_key, column_comment from Information_schema.columns where table_name=?", new Object[] {tableName});
		columnList = new ArrayList<>(list.size());
		for (Map<String, Object> map : list) {
			column = new ColumnBean();
			column.setColumnName((String) map.get("column_name"));
			column.setFieldName(NameDefineUtil.underlineToCamelCase(column.getColumnName(), false));
			column.setFieldNameFirstUpper(NameDefineUtil.underlineToCamelCase(column.getColumnName()));
			column.setConstraintType((String) map.get("column_key"));
			column.setDataType((String) map.get("data_type"));
			column.setColumnComment((String) map.get("column_comment"));
			if ("PRI".equalsIgnoreCase(column.getConstraintType())) {
				table.setPrimaryKey(column.getColumnName());
			}
			columnList.add(column);
		}
		table.setColumnList(columnList);
		if (StringUtils.isEmpty(table.getPrimaryKey())) {
			//没有主键，抛异常
			throw new CommonException(500, "表没有主键。表名【"+ tableName +"】");
		}
		return table;
	}
}
