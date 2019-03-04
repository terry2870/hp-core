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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.database.datasource.DynamicDatasource;
import com.hp.core.mybatis.autocreate.helper.AutoCreateBean;
import com.hp.core.mybatis.autocreate.helper.TableBean;
import com.hp.core.mybatis.autocreate.helper.TableBeanHelper;

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
	
	public static String FTL_FILE_PATH = "src/main/resources/templates/other";

	public static String PROJECT_PACKAGE = "com.hp.core.test";

	public static String MAPPING_DIR = "src/main/resources/META-INF/mybatis";
	
	public static String WEB_DIR_NAME = "start";
	
	//service所在module名称
	public static String SERVICE_MAVEN_MODULE = "";
	//controller锁在module名称
	public static String CONTROLLER_MAVEN_MODULE = "";
	
	public static final String BASE_BEAN_PACKAGE = "com.hp.core.common.beans.BaseBean";
	public static final String BASE_REQUEST_BO_PACKAGE = "com.hp.core.common.beans.BaseRequestBO";
	public static final String BASE_RESPONSE_BO_PACKAGE = "com.hp.core.common.beans.BaseResponseBO";
	public static final String BASE_MAPPER_PACKAGE = "com.hp.core.mybatis.mapper.BaseMapper";
	public static final String RESPONSE_PACKAGE = "com.hp.core.common.beans.Response";
	public static final String BASE_PAGE_REQUEST_PACKAGE = "com.hp.core.common.beans.page.PageRequest";
	public static final String BASE_PAGE_RESPONSE_PACKAGE = "com.hp.core.common.beans.page.PageResponse";
	public static final String BASE_PAGE_MODEL_PACKAGE = "com.hp.core.common.beans.page.PageModel";
	public static final String DAL_DIR_NAME = "dal";
	public static final String COMMON_DIR_NAME = "common";
	public static final String MODEL_DIR_NAME = "model";
	public static final String SERVICE_DIR_NAME = "service";
	public static final String CONTROLLER_DIR_NAME = "controller";
	public static final String REQUEST_DIR_NAME = "request";
	public static final String RESPONSE_DIR_NAME = "response";
	public static final String IMPL_DIR_NAME = "impl";
	public static final String CONVERT_DIR_NAME = "convert";
	public static final String AUTHER_NAME = "huangping";
		
	public static List<String> tableList = new ArrayList<>();
	
	public static void main(AutoCreateBean bean) {
		if (CollectionUtils.isEmpty(bean.getTableNameList())) {
			log.error("auto create error. with table is empty.");
			return;
		}
		MAIN_PATH_DIR = bean.getMainPathDir();
		JAVA_DIR = bean.getJavaDir();
		PROJECT_PACKAGE = bean.getProjectPackage();
		SERVICE_MAVEN_MODULE = bean.getServiceMavenModule();
		CONTROLLER_MAVEN_MODULE = bean.getControllerMavenModule();
		MAPPING_DIR = bean.getMappingDir();
		WEB_DIR_NAME = bean.getWebDir();
		try {
			DataSource datasource = SpringContextUtil.getBean(DynamicDatasource.class);
			Connection conn = datasource.getConnection();
			TableBean table = null;
			
			for (String tableName : bean.getTableNameList()) {
				table = TableBeanHelper.getTableInfoByTableName(conn, tableName);
								
				//生成model
				CreateDalModel.create(table);
				
				//生成dao
				CreateDAO.create(table);
				
				//生成mapper文件
				CreateMapper.create(table);
				
				if (bean.isCreateService()) {
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
				}
				
				if (bean.isCreateController()) {
					//生成controller
					CreateController.create(table);
				}
				
				if (bean.isCreateFtl()) {
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
