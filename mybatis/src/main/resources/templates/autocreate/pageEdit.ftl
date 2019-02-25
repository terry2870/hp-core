<!DOCTYPE html>
<form name="${modelNameFirstLow}EditForm" id="${modelNameFirstLow}EditForm" method="post" class="edit_form">
	<input type="hidden" name="id" id="id" value="${r'${id}'}" />
	<table class="table_style" align="center">
	<#list columnList as column>
		<tr>
			<td <#if column_index == 0>width="30%" </#if>align="right">${column.columnComment}：</td>
			<td<#if column_index == 0> width="70%"</#if>><input name="${column.fieldName}" id="${column.fieldName}" class="easyui-textbox" data-options="prompt:'${column.columnComment}',width:200" /></td>
		</tr>
	</#list>
	</table>
</form>
<script>
	$(function() {
		var id = "${r'${id}'}";
		if (id > 0) {
			$.post("${contextPath}/${modelName}Controller/query${modelName}ById", {
				id : id
			}, function(data) {
				$("#${modelNameFirstLow}EditForm").form("load", data.data);
			});
		}
		
		
	});
</script>

