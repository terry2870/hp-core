/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月4日
 */
public class CreateController {

	/**
	 * 生成controller文件
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.CONTROLLER_MAVEN_MODULE + ".controller";
		String servicePackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + ".service";
		String requestModelPackage = CreateFile.PROJECT_PACKAGE + ".model.request";
		String responseModelPackage = CreateFile.PROJECT_PACKAGE + ".model.response";
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.CONTROLLER_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = table.getModelName() + "Controller";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("import org.slf4j.Logger;\r\n");
		sb.append("import org.slf4j.LoggerFactory;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RestController;\r\n");
		sb.append("\r\n");
		sb.append("import ").append(requestModelPackage).append(".").append(table.getModelName()).append("RequestBO;\r\n");
		sb.append("import ").append(responseModelPackage).append(".").append(table.getModelName()).append("ResponseBO;\r\n");
		sb.append("import ").append(servicePackage).append(".I").append(table.getModelName()).append("Service;\r\n");
		sb.append("import ").append(CreateFile.BASE_RESPONSE_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_REQUEST_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.BASE_PAGE_RESPONSE_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * ").append(table.getTableComment()).append("控制器\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("@RestController\r\n");
		sb.append("@RequestMapping(\"/").append(table.getModelName()).append("Controller\")\r\n");
		sb.append("public class ").append(table.getModelName()).append("Controller {\r\n");
		sb.append("\r\n");
		sb.append("	static Logger log = LoggerFactory.getLogger(").append(table.getModelName()).append("Controller.class);\r\n");
		sb.append("\r\n");
		sb.append("	@Autowired\r\n");
		sb.append("	private I").append(table.getModelName()).append("Service ").append(table.getModelNameFirstLow()).append("Service;\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 查询").append(table.getTableComment()).append("列表\r\n");
		sb.append("	 * @param request\r\n");
		sb.append("	 * @param pageRequest\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	@RequestMapping(\"/queryAll").append(table.getModelName()).append(".do\")\r\n");
		sb.append("	public Response<PageResponse<").append(table.getModelName()).append("ResponseBO>> queryAll").append(table.getModelName()).append("(").append(table.getModelName()).append("RequestBO request, PageRequest pageRequest) {\r\n");
		sb.append("		log.info(\"queryAll").append(table.getModelName()).append(" with request={}, page={}\", request, pageRequest);\r\n");
		sb.append("		PageResponse<").append(table.getModelName()).append("ResponseBO> list = ").append(table.getModelNameFirstLow()).append("Service.query").append(table.getModelName()).append("PageList(request, pageRequest);\r\n");
		sb.append("		log.info(\"queryAll").append(table.getModelName()).append(" success. with request={}, page={}\", request, pageRequest);\r\n");
		sb.append("		if (list == null) {\r\n");
		sb.append("			return new Response<>(new PageResponse<>());\r\n");
		sb.append("		}\r\n");
		sb.append("		return new Response<>(list);\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 保存").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param request\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	@RequestMapping(\"/save").append(table.getModelName()).append(".do\")\r\n");
		sb.append("	public Response<Object> save").append(table.getModelName()).append("(").append(table.getModelName()).append("RequestBO request) {\r\n");
		sb.append("		log.info(\"save").append(table.getModelName()).append(" with request={}\", request);\r\n");
		sb.append("		").append(table.getModelNameFirstLow()).append("Service.save").append(table.getModelName()).append("(request);\r\n");
		sb.append("		log.info(\"save").append(table.getModelName()).append(" success. with request={}\", request);\r\n");
		sb.append("		return new Response<>();\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 删除").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param id\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	@RequestMapping(\"/delete").append(table.getModelName()).append(".do\")\r\n");
		sb.append("	public Response<Object> delete").append(table.getModelName()).append("(Integer id) {\r\n");
		sb.append("		log.info(\"delete").append(table.getModelName()).append(" with id={}\", id);\r\n");
		sb.append("		").append(table.getModelNameFirstLow()).append("Service.delete").append(table.getModelName()).append("(id);\r\n");
		sb.append("		log.info(\"delete").append(table.getModelName()).append(" success. with id={}\", id);\r\n");
		sb.append("		return new Response<>();\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * 根据id，查询").append(table.getTableComment()).append("\r\n");
		sb.append("	 * @param id\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	@RequestMapping(\"/query").append(table.getModelName()).append("ById.do\")\r\n");
		sb.append("	public Response<").append(table.getModelName()).append("ResponseBO> query").append(table.getModelName()).append("ById(Integer id) {\r\n");
		sb.append("		log.info(\"query").append(table.getModelName()).append("ById with id={}\", id);\r\n");
		sb.append("		").append(table.getModelName()).append("ResponseBO bo = ").append(table.getModelNameFirstLow()).append("Service.query").append(table.getModelName()).append("ById(id);\r\n");
		sb.append("		log.info(\"query").append(table.getModelName()).append("ById success. with id={}\", id);\r\n");
		sb.append("		return new Response<>(bo);\r\n");
		sb.append("	}\r\n");
		sb.append("}\r\n");
		CreateFile.saveFile(filePath + "/" + fileName + ".java", sb.toString());
	}
}
