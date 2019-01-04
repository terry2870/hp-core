/**
 * 
 */
package com.hp.core.mybatis.autocreate.helper;
/**
 * @author huangping
 * 2018年5月31日
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.utils.NameDefineUtil;
import com.hp.core.database.datasource.AbstDatabase;
import com.hp.core.database.enums.DatabaseTypeEnum;

public class TableBeanHelper {

	private static Logger log = LoggerFactory.getLogger(TableBeanHelper.class);
	
	/**
	 * 根据表名，获取表，字段相关信息
	 * @param conn
	 * @param tableName
	 * @param database
	 * @return
	 */
	public static TableBean getTableInfoByTableName(Connection conn, String tableName) {
		TableBean table = new TableBean();
		table.setTableName(tableName);
		table.setModelName(NameDefineUtil.underlineToCamelCase(tableName));
		table.setModelNameFirstLow(NameDefineUtil.underlineToCamelCase(tableName, false));
		
		try {
			AbstDatabase database = DatabaseTypeEnum.getDatabaseByDatabaseType(conn.getMetaData().getDatabaseProductName());
			String catalog = conn.getCatalog();
			String schema = conn.getSchema();
			DatabaseMetaData dbmd = conn.getMetaData();
			
			//获取表信息
			ResultSet rs = dbmd.getTables(catalog, schema, tableName, new String[] {"TABLE"});
			if (rs.next()) {
				table.setTableComment(rs.getString("REMARKS"));
			}
			
			//获取主键信息
			rs = dbmd.getPrimaryKeys(catalog, schema, tableName);
			if (rs.next()) {
				table.setPrimaryKey(NameDefineUtil.underlineToCamelCase(rs.getString("COLUMN_NAME"), false));
				table.setPrimaryKeyFirstUpper(NameDefineUtil.underlineToCamelCase(rs.getString("COLUMN_NAME")));
			}
			
			List<ColumnBean> columnList = new ArrayList<>();
			ColumnBean column = null;
			//获取字段信息
			rs = dbmd.getColumns(catalog, schema, tableName, "%");
			while (rs.next()) {
				column = new ColumnBean();
				column.setColumnName(rs.getString("COLUMN_NAME"));
				column.setFieldName(NameDefineUtil.underlineToCamelCase(column.getColumnName(), false));
				column.setFieldNameFirstUpper(NameDefineUtil.underlineToCamelCase(column.getColumnName()));
				column.setConstraintType("");
				column.setJdbcType(rs.getString("TYPE_NAME"));
				column.setColumnComment(rs.getString("REMARKS"));
				column.setJavaType(database.dbTypeToJavaType(column.getJdbcType()));
				columnList.add(column);
			}
			table.setColumnList(columnList);
		} catch (SQLException e) {
			log.error("", e);
		}
		return table;
	}
}
