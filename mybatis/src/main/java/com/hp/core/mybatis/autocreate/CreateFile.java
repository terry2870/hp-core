/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.core.mybatis.datasource.DynamicDatasource;

/**
 * @author huangping
 * 2018年5月30日
 */
public class CreateFile {

	//文件生成的主路径
	public static String MAIN_PATH_DIR = "./";
	
	//java文件存放主目录
	public static String JAVA_DIR = "src/main/java";

	//DAO生成的包地址
	public static String DAO_PACKAGE = "com.hp.core.test.dal";
	
	//model生成的包地址
	public static String MODEL_PACKAGE = DAO_PACKAGE + ".model";

	public static String MAPPING_DIR = "src/main/resources/META-INF/mybatis";
	
	public static final String BASE_BEAN_PACKAGE = "com.hp.core.common.beans.BaseBean";
	public static final String BASE_MAPPER_PACKAGE = "com.hp.core.mybatis.mapper.BaseMapper";
	
	public static List<String> tableList = new ArrayList<>();
	
	public static void main(String[] args) {
		String[] tableArr = args[0].split(",");
		if (args.length > 1) {
			MAIN_PATH_DIR = args[1];
		}
		if (args.length > 2) {
			JAVA_DIR = args[2];
		}
		if (args.length > 3) {
			DAO_PACKAGE = args[3];
		}
		if (args.length > 4) {
			MODEL_PACKAGE = args[4];
		}
		if (args.length > 5) {
			MAPPING_DIR = args[5];
		}
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/spring-*.xml")) {
			DataSource datasource = context.getBean(DynamicDatasource.class);
			JdbcTemplate jdbc = new JdbcTemplate(datasource);
			TableBean table = null;
			for (String tableName : tableArr) {
				table = TableBeanHelper.getTableBeanByTableName(jdbc, tableName);
				
				//生成model
				CreateModel.create(table);
				
				//生成dao
				CreateDAO.create(table);
				
				//生成mapper文件
				CreateMapper.create(table);
			}
		}
	}
	
	/**
	 * 生成文件
	 * @param fileName
	 * @param data
	 */
	public static void saveFile(String fileName, String data) {
		try {
			FileUtils.writeStringToFile(new File(fileName), data, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
