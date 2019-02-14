/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import java.util.HashMap;
import java.util.Map;

import com.hp.core.freemarker.utils.FreeMarkerUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月1日
 */
public class CreateService {

	/**
	 * 生成接口文件
	 * @param table
	 */
	public static void createInterface(TableBean table) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + "." + CreateFile.SERVICE_DIR_NAME;
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = "I" + table.getModelName() + "Service.java";
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("modelName", table.getModelName());
		map.put("tableComment", table.getTableComment());
		map.put("dalModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME + "." + CreateFile.MODEL_DIR_NAME);
		map.put("requestModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.REQUEST_DIR_NAME);
		map.put("responseModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.RESPONSE_DIR_NAME);
		map.put("pageRequestPackage", CreateFile.BASE_PAGE_REQUEST_PACKAGE);
		map.put("pageResponsePackage", CreateFile.BASE_PAGE_RESPONSE_PACKAGE);
		FreeMarkerUtil.createFile("autocreate/service.ftl", filePath + "/" + fileName, map);
	}
	
	/**
	 * 生成接口实现类
	 * @param table
	 */
	public static void createInterfaceImpl(TableBean table) {
		String servicePackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + "." + CreateFile.SERVICE_DIR_NAME;
		String packageUrl = servicePackage + "." + CreateFile.IMPL_DIR_NAME;
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = table.getModelName() + "ServiceImpl.java";
		String daoPackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME;
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("modelName", table.getModelName());
		map.put("tableComment", table.getTableComment());
		map.put("servicePackage", servicePackage);
		map.put("convertPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.COMMON_DIR_NAME + "." + CreateFile.CONVERT_DIR_NAME);
		map.put("daoPackage", daoPackage);
		map.put("dalModelPackage", daoPackage + "." + CreateFile.MODEL_DIR_NAME);
		map.put("dalModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME + "." + CreateFile.MODEL_DIR_NAME);
		map.put("requestModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.REQUEST_DIR_NAME);
		map.put("responseModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.RESPONSE_DIR_NAME);
		map.put("pageModelPackage", CreateFile.BASE_PAGE_MODEL_PACKAGE);
		map.put("pageRequestPackage", CreateFile.BASE_PAGE_REQUEST_PACKAGE);
		map.put("pageResponsePackage", CreateFile.BASE_PAGE_RESPONSE_PACKAGE);
		FreeMarkerUtil.createFile("autocreate/serviceimpl.ftl", filePath + "/" + fileName, map);
	}
}
