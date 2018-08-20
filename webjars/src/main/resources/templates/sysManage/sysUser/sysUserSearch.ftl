<input class="easyui-textbox" id="loginName" data-options="prompt:'登录名'" />
<input class="easyui-textbox" id="userName" data-options="prompt:'用户名'" />
<input type="text" id="status" />
<a id="sysUserSearchBtn">搜索</a>
<a id="sysUserAddBtn">新增</a>
<script>
	$(function() {
	
		//状态
		$("#sysUserListToolbar #status").myCombobox({
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
	
		//支持回车键
		$("#sysUserListToolbar").keydown(function(e) {
			if (e.keyCode == 13) {
				$("#sysUserListToolbar #sysUserSearchBtn").click();
			}
		});
		
		//搜索按钮
		$("#sysUserSearchBtn").linkbutton({
			iconCls : "icon-search",
			onClick : function() {
				$("#sysUserListTable").myDatagrid("load", {
					loginName : $("#sysUserListToolbar #loginName").val(),
					userName : $("#sysUserListToolbar #userName").val(),
					status : $("#sysUserListToolbar #status").myCombobox("getValue")
				});
			}
		});
		
		//新增按钮
		$("#sysUserAddBtn").linkbutton({
			iconCls : "icon-add",
			onClick : function() {
				editSysUser(0);
			}
		});
	});
</script>