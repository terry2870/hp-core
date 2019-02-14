package ${package};

import ${importPackage};

/**
 * ${tableComment}
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
public class ${className} extends ${baseExtendBeanName} {

	private static final long serialVersionUID = 1L;

	<#list columnList as column>
	/**
	 * ${column.columnComment}
	 */
	private ${column.javaType} ${column.fieldName};

	</#list>
	<#list columnList as column>
	public ${column.javaType} get${column.fieldNameFirstUpper}() {
		return ${column.fieldName};
	}

	public ${className} set${column.fieldNameFirstUpper}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
		return this;
	}

	</#list>
}
