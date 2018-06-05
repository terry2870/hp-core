/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;
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
		StringBuilder sb = new StringBuilder();
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + ".service";
		String requestModelPackage = CreateFile.PROJECT_PACKAGE + ".model.request";
		String responseModelPackage = CreateFile.PROJECT_PACKAGE + ".model.response";
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = "I" + table.getModelName() + "Service";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("import ").append(requestModelPackage).append(".").append(table.getModelName()).append("RequestBO;\r\n");
		sb.append("import ").append(responseModelPackage).append(".").append(table.getModelName()).append("ResponseBO;\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_REQUEST_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_RESPONSE_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * ").append(table.getTableComment()).append("业务接口定义\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("public interface ").append(fileName).append(" {\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 保存").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param request\r\n");
		sb.append("	 */\r\n");
		sb.append("	public void save").append(table.getModelName()).append("(").append(table.getModelName()).append("RequestBO request);\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 查询").append(table.getTableComment()).append("列表\r\n");
		sb.append("	 * @param request\r\n");
		sb.append("	 * @param pageRequest\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	public PageResponse<").append(table.getModelName()).append("ResponseBO> query").append(table.getModelName()).append("PageList(").append(table.getModelName()).append("RequestBO request, PageRequest pageRequest);\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 删除").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param id\r\n");
		sb.append("	 */\r\n");
		sb.append("	public void delete").append(table.getModelName()).append("(Integer id);\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 根据id，查询").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param id\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	public ").append(table.getModelName()).append("ResponseBO query").append(table.getModelName()).append("ById(Integer id);\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		CreateFile.saveFile(filePath + "/" + fileName + ".java", sb.toString());
	}
	
	/**
	 * 生成接口实现类
	 * @param table
	 */
	public static void createInterfaceImpl(TableBean table) {
		StringBuilder sb = new StringBuilder();
		String convertPackage = CreateFile.PROJECT_PACKAGE + ".common.convert";
		String daoPackage = CreateFile.PROJECT_PACKAGE + ".dal";
		String dalModelPackage = daoPackage + ".model";
		String servicePackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + ".service";
		String packageUrl = servicePackage + ".impl";
		String requestModelPackage = CreateFile.PROJECT_PACKAGE + ".model.request";
		String responseModelPackage = CreateFile.PROJECT_PACKAGE + ".model.response";
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = table.getModelName() + "ServiceImpl";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("import java.util.ArrayList;\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("\r\n");
		sb.append("import org.slf4j.Logger;\r\n");
		sb.append("import org.slf4j.LoggerFactory;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.stereotype.Service;\r\n");
		sb.append("\r\n");
		sb.append("import ").append(convertPackage).append(".").append(table.getModelName()).append("Convert;\r\n");
		sb.append("import ").append(daoPackage).append(".I").append(table.getModelName()).append("DAO;\r\n");
		sb.append("import ").append(dalModelPackage).append(".").append(table.getModelName()).append(";\r\n");
		sb.append("import ").append(requestModelPackage).append(".").append(table.getModelName()).append("RequestBO;\r\n");
		sb.append("import ").append(responseModelPackage).append(".").append(table.getModelName()).append("ResponseBO;\r\n");
		sb.append("import ").append(servicePackage).append(".I").append(table.getModelName()).append("Service;\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_MODEL_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_REQUEST_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_RESPONSE_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * ").append(table.getTableComment()).append("业务操作接口实现\r\n");
		sb.append(" * @author huangping\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("@Service\r\n");
		sb.append("public class ").append(table.getModelName()).append("ServiceImpl implements I").append(table.getModelName()).append("Service {\r\n");
		sb.append("\r\n");
		sb.append("	private static Logger log = LoggerFactory.getLogger(").append(table.getModelName()).append("ServiceImpl.class);\r\n");
		sb.append("\r\n");
		sb.append("	@Autowired\r\n");
		sb.append("	private I").append(table.getModelName()).append("DAO ").append(table.getModelNameFirstLow()).append("DAO;\r\n");
		sb.append("\r\n");
		sb.append("	@Override\r\n");
		sb.append("	public void save").append(table.getModelName()).append("(").append(table.getModelName()).append("RequestBO request) {\r\n");
		sb.append("		log.info(\"save").append(table.getModelName()).append(" with request={}\", request);\r\n");
		sb.append("		").append(table.getModelName()).append(" dal = ").append(table.getModelName()).append("Convert.boRequest2Dal(request);\r\n");
		sb.append("		if (request.get").append(table.getPrimaryKeyFirstUpper()).append("() == null || request.get").append(table.getPrimaryKeyFirstUpper()).append("().intValue() == 0) {\r\n");
		sb.append("			//新增\r\n");
		sb.append("			").append(table.getModelNameFirstLow()).append("DAO.insert(dal);\r\n");
		sb.append("		} else {\r\n");
		sb.append("			//修改\r\n");
		sb.append("			").append(table.getModelNameFirstLow()).append("DAO.updateByPrimaryKey(dal);\r\n");
		sb.append("		}\r\n");
		sb.append("		log.info(\"save").append(table.getModelName()).append(" success with request={}\", request);\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	@Override\r\n");
		sb.append("	public PageResponse<").append(table.getModelName()).append("ResponseBO> query").append(table.getModelName()).append("PageList(").append(table.getModelName()).append("RequestBO request, PageRequest pageRequest) {\r\n");
		sb.append("		log.info(\"query").append(table.getModelName()).append("PageList with request={}\", request);\r\n");
		sb.append("		").append(table.getModelName()).append(" dal = ").append(table.getModelName()).append("Convert.boRequest2Dal(request);\r\n");
		sb.append("		PageModel page = pageRequest.toPageModel();\r\n");
		sb.append("\r\n");
		sb.append("		//查询总数\r\n");
		sb.append("		int total = ").append(table.getModelNameFirstLow()).append("DAO.selectCountByParams(dal);\r\n");
		sb.append("		if (total == 0) {\r\n");
		sb.append("			log.warn(\"query").append(table.getModelName()).append("PageList error. with total=0. with request={}\", request);\r\n");
		sb.append("			return null;\r\n");
		sb.append("		}\r\n");
		sb.append("\r\n");
		sb.append("		//查询列表\r\n");
		sb.append("		List<").append(table.getModelName()).append("> list = ").append(table.getModelNameFirstLow()).append("DAO.selectPageListByParams(dal, page);\r\n");
		sb.append("		List<").append(table.getModelName()).append("ResponseBO> respList = new ArrayList<>();\r\n");
		sb.append("		for (").append(table.getModelName()).append(" a : list) {\r\n");
		sb.append("			respList.add(").append(table.getModelName()).append("Convert.dal2BOResponse(a));\r\n");
		sb.append("		}\r\n");
		sb.append("		log.info(\"query").append(table.getModelName()).append("PageList success. with request={}\", request);\r\n");
		sb.append("		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	@Override\r\n");
		sb.append("	public void delete").append(table.getModelName()).append("(Integer id) {\r\n");
		sb.append("		log.info(\"delete").append(table.getModelName()).append(" with id={}\", id);\r\n");
		sb.append("		").append(table.getModelNameFirstLow()).append("DAO.deleteByPrimaryKey(id);\r\n");
		sb.append("		log.info(\"delete").append(table.getModelName()).append(" success with id={}\", id);\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	@Override\r\n");
		sb.append("	public ").append(table.getModelName()).append("ResponseBO query").append(table.getModelName()).append("ById(Integer id) {\r\n");
		sb.append("		log.info(\"query").append(table.getModelName()).append("ById with id={}\", id);\r\n");
		sb.append("		").append(table.getModelName()).append(" dal = ").append(table.getModelNameFirstLow()).append("DAO.selectByPrimaryKey(id);\r\n");
		sb.append("		if (dal == null) {\r\n");
		sb.append("			log.warn(\"query").append(table.getModelName()).append("ById error. with result is null. with id={}\", id);\r\n");
		sb.append("			return null;\r\n");
		sb.append("		}\r\n");
		sb.append("		return ").append(table.getModelName()).append("Convert.dal2BOResponse(dal);\r\n");
		sb.append("	}\r\n");
		sb.append("}\r\n");
		
		CreateFile.saveFile(filePath + "/" + fileName + ".java", sb.toString());
	}
}
