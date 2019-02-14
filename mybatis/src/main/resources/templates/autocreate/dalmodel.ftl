package ${package};

import javax.persistence.Id;

import ${baseBeanPackage};

/**
 * ${tableComment}
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
public class ${modelName} extends BaseBean {

	private static final long serialVersionUID = 1L;

	<#list columnList as column>
	/**
	 * ${column.columnComment}
	 */
	<#if primaryKey = column.columnName>
	@Id
	</#if>
	private ${column.javaType} ${column.fieldName};

	</#list>
	<#list columnList as column>
	public ${column.javaType} get${column.fieldNameFirstUpper}() {
		return ${column.fieldName};
	}

	public ${modelName} set${column.fieldNameFirstUpper}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
		return this;
	}

	</#list>
}
