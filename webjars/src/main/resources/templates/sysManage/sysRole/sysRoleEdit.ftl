<form name="sysRoleEditForm" id="sysRoleEditForm" method="post" class="edit_form">
	<input type="hidden" name="id" id="id" value="${id}" />
	<table align="center">
		<tr>
			<td width="30%" align="right">角色名称：</td>
			<td width="70%">
				<input name="roleName" id="roleName" class="easyui-textbox" data-options="
					required : true,
					prompt : '角色名称'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">角色描述：</td>
			<td>
				<input name="roleInfo" id="roleInfo" class="easyui-textbox" data-options="
					prompt : '角色描述',
					height : 70,
					multiline : true
				" />
			</td>
		</tr>
		<tr>
			<td align="right">角色状态：</td>
			<td>
				<input name="status" id="status" />
			</td>
		</tr>
	</table>
</form>
<script>
	$(function() {
		
		let id = ${id};
		
		//状态
		$("#sysRoleEditForm #status").combobox({
			url : "${request.contextPath}/NoFilterController",
			queryParams : {
				method : 'getEnumForSelect',
				className : 'com.hp.core.common.enums.StatusEnum'
			},
			prompt : '角色状态',
			panelHeight : 100,
			editable : false,
			required : true,
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			}
		});
		
		//初始化页面
		$.post("${request.contextPath}/SysRoleController/querySysRoleById", {
			id : id
		}, function(data) {
			$("#sysRoleEditForm").form("load", data.data);
		});
		
		
	});
</script>

