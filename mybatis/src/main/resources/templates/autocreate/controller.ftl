package ${package};

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ${responsePackage};
import ${pageRequestPackage};
import ${pageResponsePackage};
import ${freeMarkerUtilPackage};
import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${servicePackage}.I${modelName}Service;

/**
 * 控制器
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
@RestController
@RequestMapping("/${modelName}Controller")
public class ${modelName}Controller {

	private static Logger log = LoggerFactory.getLogger(${modelName}Controller.class);

	@Autowired
	private I${modelName}Service ${modelNameFirstLow}Service;

	/**
	 * 进入${tableComment}页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/${modelNameFirstLow}List")
	public ModelAndView ${modelNameFirstLow}List(Integer menuId, String iframeId) {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		return new ModelAndView("other/${modelNameFirstLow}List", map);
	}
	
	/**
	 * 查询列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/queryAll${modelName}")
	public Response<PageResponse<${modelName}ResponseBO>> queryAll${modelName}(${modelName}RequestBO request, PageRequest pageRequest) {
		log.info("enter queryAll${modelName} with request={}, page={}", request, pageRequest);
		PageResponse<${modelName}ResponseBO> list = ${modelNameFirstLow}Service.query${modelName}PageList(request, pageRequest);
		log.info("queryAll${modelName} success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return new Response<>(list);
	}

	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping("/save${modelName}")
	public Response<Object> save${modelName}(${modelName}RequestBO request) {
		log.info("enter save${modelName} with request={}", request);
		${modelNameFirstLow}Service.save${modelName}(request);
		log.info("save${modelName} success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete${modelName}")
	public Response<Object> delete${modelName}(Integer id) {
		log.info("enter delete${modelName} with id={}", id);
		${modelNameFirstLow}Service.delete${modelName}(id);
		log.info("delete${modelName} success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询
	 * @param id
	 * @return
	 */
	@RequestMapping("/query${modelName}ById")
	public Response<${modelName}ResponseBO> query${modelName}ById(Integer id) {
		log.info("enter query${modelName}ById with id={}", id);
		${modelName}ResponseBO bo = ${modelNameFirstLow}Service.query${modelName}ById(id);
		log.info("query${modelName}ById success. with id={}", id);
		return new Response<>(bo);
	}
}
