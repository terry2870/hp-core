package ${package};

import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${pageRequestPackage};
import ${pageResponsePackage};

/**
 * ${tableComment}业务接口定义
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
public interface I${modelName}Service {

	/**
	 * 保存${tableComment}
	 * @param request
	 */
	public void save${modelName}(${modelName}RequestBO request);

	/**
	 * 查询${tableComment}列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<${modelName}ResponseBO> query${modelName}PageList(${modelName}RequestBO request, PageRequest pageRequest);

	/**
	 * 删除${tableComment}
	 * @param id
	 */
	public void delete${modelName}(Integer id);

	/**
	 * 根据id，查询${tableComment}
	 * @param id
	 * @return
	 */
	public ${modelName}ResponseBO query${modelName}ById(Integer id);

}
