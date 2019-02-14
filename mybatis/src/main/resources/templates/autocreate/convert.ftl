package ${package};

import org.springframework.beans.BeanUtils;

import ${dalModelPackage}.${modelName};
import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;

/**
 * ${tableComment}对象转换类
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */

public class ${modelName}Convert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static ${modelName} boRequest2Dal(${modelName}RequestBO bo) {
		if (bo == null) {
			return null;
		}
		${modelName} dal = new ${modelName}();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static ${modelName}ResponseBO dal2BOResponse(${modelName} dal) {
		if (dal == null) {
			return null;
		}
		${modelName}ResponseBO bo = new ${modelName}ResponseBO();
		BeanUtils.copyProperties(dal, bo);
		return bo;
	}
}
