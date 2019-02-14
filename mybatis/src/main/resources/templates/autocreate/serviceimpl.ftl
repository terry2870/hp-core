package ${package};

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${convertPackage}.${modelName}Convert;
import ${daoPackage}.I${modelName}DAO;
import ${dalModelPackage}.${modelName};
import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${servicePackage}.I${modelName}Service;
import ${pageModelPackage};
import ${pageRequestPackage};
import ${pageResponsePackage};

/**
 * ${tableComment}业务操作接口实现
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
@Service
public class ${modelName}ServiceImpl implements I${modelName}Service {

	private static Logger log = LoggerFactory.getLogger(${modelName}ServiceImpl.class);

	@Autowired
	private I${modelName}DAO ${modelNameFirstLow}DAO;

	@Override
	public void save${modelName}(${modelName}RequestBO request) {
		log.info("save${modelName} with request={}", request);
		${modelName} dal = ${modelName}Convert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			// 新增
			${modelNameFirstLow}DAO.insertSelective(dal);
		} else {
			// 修改
			${modelNameFirstLow}DAO.updateByPrimaryKeySelective(dal);
		}
		log.info("save${modelName} success with request={}", request);
	}

	@Override
	public PageResponse<${modelName}ResponseBO> query${modelName}PageList(${modelName}RequestBO request, PageRequest pageRequest) {
		log.info("query${modelName}PageList with request={}", request);
		${modelName} dal = ${modelName}Convert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		// 查询总数
		int total = ${modelNameFirstLow}DAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("query${modelName}PageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<${modelName}ResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		// 查询列表
		List<${modelName}> list = ${modelNameFirstLow}DAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("query${modelName}PageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<${modelName}ResponseBO> respList = new ArrayList<>();
		for (${modelName} a : list) {
			respList.add(${modelName}Convert.dal2BOResponse(a));
		}
		log.info("query${modelName}PageList success. with request={}", request);
		resp.setRows(respList);
		return resp;
	}

	@Override
	public void delete${modelName}(Integer id) {
		log.info("delete${modelName} with id={}", id);
		${modelNameFirstLow}DAO.deleteByPrimaryKey(id);
		log.info("delete${modelName} success with id={}", id);
	}

	@Override
	public ${modelName}ResponseBO query${modelName}ById(Integer id) {
		log.info("query${modelName}ById with id={}", id);
		${modelName} dal = ${modelNameFirstLow}DAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("query${modelName}ById error. with result is null. with id={}", id);
			return null;
		}
		return ${modelName}Convert.dal2BOResponse(dal);
	}
}
