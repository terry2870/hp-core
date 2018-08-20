<input id="roleName" class="easyui-textbox" data-options="prompt:'角色名称',width:150" />
<input type="text" id="status" />
<a class="table_btn" id="sysRoleSearchBtn">搜索</a>
<a class="table_btn" id="sysRoleAddBtn" style="display:none">新增</a>

<script type="text/javascript">
	$(function(){
		
		//状态
		$("#sysRoleListToolbar #status").myCombobox({
			prompt : "状态",
			width : 150,
			url : "${request.contextPath}/NoFilterController",
			queryParams : {
				method : 'getEnumForSelect',
				className : 'com.hp.core.common.enums.StatusEnum'
			},
			value : 1,
			panelHeight : 100,
			editable : false,
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			}
		});
		
		//搜索按钮
		$("#sysRoleListToolbar #sysRoleSearchBtn").linkbutton({
			iconCls : "icon-search",
			onClick : function() {
				$("#sysRoleListTable").datagrid("load", {
					roleName : $("#sysRoleListToolbar #roleName").textbox("getValue"),
					status : $("#sysRoleListToolbar #status").combobox("getValue")
				});
			}
		});
		
		//新增按钮
		$("#sysRoleListToolbar #sysRoleAddBtn").linkbutton({
			iconCls : "icon-add",
			onClick : function() {
				editSysRole(0);
			}
		});
		
		//支持回车
		$("#sysRoleListToolbar").keydown(function(e) {
			if (e.keyCode == 13) {
				$("#sysRoleListToolbar #sysRoleSearchBtn").click();
			}
		});
	});
</script>

