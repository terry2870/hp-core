/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hp.core.mybatis.autocreate.helper.TableBean;
import com.hp.core.mybatis.autocreate.helper.TableBeanHelper;
import com.hp.core.mybatis.datasource.DynamicDatasource;

/**
 * @author huangping
 * 2018年5月30日
 */
public class CreateFile {
	
	private static Logger log = LoggerFactory.getLogger(CreateFile.class);

	//文件生成的主路径
	public static String MAIN_PATH_DIR = "./";
	
	//java文件存放主目录
	public static String JAVA_DIR = "src/main/java";
	
	public static String JSP_FILE_PATH = "src/main/webapp/jsp/newfile";

	public static String PROJECT_PACKAGE = "com.hp.core.test";

	public static String MAPPING_DIR = "src/main/resources/META-INF/mybatis";
	
	//service所在module名称
	public static String SERVICE_MAVEN_MODULE = "";
	//controller锁在module名称
	public static String CONTROLLER_MAVEN_MODULE = "";
	
	public static final String BASE_BEAN_PACKAGE = "com.hp.core.common.beans.BaseBean";
	public static final String BASE_REQUEST_BO_PACKAGE = "com.base.model.request.BaseRequestBO";
	public static final String BASE_MAPPER_PACKAGE = "com.hp.core.mybatis.mapper.BaseMapper";
	public static final String BASE_RESPONSE_PACKAGE = "com.hp.core.common.beans.Response";
	public static final String BASE_PAGE_REQUEST_PACKAGE = "com.hp.core.common.beans.page.PageRequest";
	public static final String BASE_PAGE_RESPONSE_PACKAGE = "com.hp.core.common.beans.page.PageResponse";
	public static final String BASE_PAGE_MODEL_PACKAGE = "com.hp.core.common.beans.page.PageModel";
	public static final String DAL_DIR_NAME = "dal";
	public static final String WEB_DIR_NAME = "web";
	public static final String COMMON_DIR_NAME = "common";
	public static final String MODEL_DIR_NAME = "model";
	public static final String AUTHER_NAME = "huangping";
	
	public static String is_create_JSP = "0";
	
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
			PROJECT_PACKAGE = args[3];
		}
		if (args.length > 4) {
			SERVICE_MAVEN_MODULE = args[4];
		}
		if (args.length > 5) {
			CONTROLLER_MAVEN_MODULE = args[5];
		}
		if (args.length > 6) {
			MAPPING_DIR = args[6];
		}
		if (args.length > 7) {
			is_create_JSP = args[7];
		}
		if (args.length > 8) {
			JSP_FILE_PATH = args[8];
		}
		try (
				ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/spring-*.xml");
		) {
			DataSource datasource = context.getBean(DynamicDatasource.class);
			Connection conn = datasource.getConnection();
			TableBean table = null;
			
			for (String tableName : tableArr) {
				table = TableBeanHelper.getTableInfoByTableName(conn, tableName);
				
				//生成model
				CreateDalModel.create(table);
				
				//生成dao
				CreateDAO.create(table);
				
				//生成mapper文件
				CreateMapper.create(table);
				
				//生成request model
				CreatModel.creat(table, "1");
				
				//生成response model
				CreatModel.creat(table, "2");
				
				//生成convert
				CreateConvert.create(table);
				
				//生成service
				CreateService.createInterface(table);
				
				//生成实现类
				CreateService.createInterfaceImpl(table);
				
				//生成controller
				CreateController.create(table);
				
				if ("1".equals(is_create_JSP)) {
					CreateJSP.createJSPList(table);
					CreateJSP.createJSPEdit(table);
					CreateJSP.createJSPSearch(table);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成文件
	 * @param fileName
	 * @param data
	 */
	public static void saveFile(String fileName, String data) {
		try {
			String path = fileName.substring(0,  fileName.lastIndexOf("/"));
			File pathFile = new File(path);
			//文件夹不存在，则创建文件夹
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			FileUtils.writeStringToFile(new File(fileName), data, "UTF-8");
			log.info("createFile with fileName={}", fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
