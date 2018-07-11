<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="t" uri="/my-tags"%>
<!DOCTYPE html>
<form name="${modelNameFirstLow}EditForm" id="${modelNameFirstLow}EditForm" method="post">
	<input type="hidden" name="id" id="id" value="<t:write name='id' />" />
	<table class="table_style" align="center">
	<#list columnList as column>
		<tr>
			<td <#if column_index == 0>width="30%"</#if>align="right">${column.columnComment}：</td>
			<td<#if column_index == 0> width="70%"</#if>><input name="${column.fieldName}" id="${column.fieldName}" class="easyui-textbox" data-options="prompt:'${column.columnComment}',width:200" /></td>
		</tr>
	</#list>
	</table>
</form>
<script>
	$(function() {
		
		$("#${modelNameFirstLow}EditForm").form();
		var id = "<t:write name='id' />";
		if (id > 0) {
			$.post("<t:path/>/${modelName}Controller/query${modelName}ById.do", {
				id : id
			}, function(data) {
				$("#${modelNameFirstLow}EditForm").form("load", data.data);
			});
		}
		
		
	});
</script>

