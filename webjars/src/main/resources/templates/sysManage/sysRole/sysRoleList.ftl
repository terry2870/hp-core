<#include "/include/head.ftl">
<body>
	<table id="sysRoleListTable"></table>
	<div id="sysRoleListToolbar">
		<#include "/sysManage/sysRole/sysRoleSearch.ftl">
	</div>
<script>

	//新增或修改角色
	function editSysRole(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		var title = id === 0 ? "新增角色数据" : "修改角色数据";
		window.top.$(div).myDialog({
			width : 500,
			height : 400,
			title : title,
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleEdit&id=" + id,
			method : "post",
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "保存",
				id : "sysRoleSaveBtn",
				disabled : true,
				iconCls : "icon-save",
				handler : function() {
					$.saveDialog({
						dialogObject : window.top.$(div),
						formObject : window.top.$("#sysRoleEditForm"),
						url : "${request.contextPath}/SysRoleController/saveSysRole",
						reloadTableObject : $("#sysRoleListTable")
					});
				}
			}, {
				text : "关闭",
				iconCls : "icon-cancel",
				handler : function() {
					window.top.$(div).dialog("close");
				}
			}],
			onOpen : function() {
				window.top.showButtonList("${menuId}", div.parent());
			}
		});
	}
	
	//查看角色详情
	function viewSysRole(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		window.top.$(div).myDialog({
			width : 500,
			height : 400,
			title : "角色数据详细",
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleEdit&id=" + id,
			method : "post",
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "刷新",
				iconCls : "icon-reload",
				handler : function() {
					window.top.$(div).dialog("refresh");
				}
			}, {
				text : "关闭",
				iconCls : "icon-cancel",
				handler : function() {
					window.top.$(div).dialog("close");
				}
			}]
		});
	}
	
	//分配角色菜单
	function viewRoleMenu(roleId) {
		var div = $("<div>").appendTo($(window.top.document.body));
		window.top.$(div).myDialog({
			width : "20%",
			height : "90%",
			title : "分配菜单",
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleMenuEdit&roleId=" + roleId,
			method : "post",
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "保存",
				disabled : true,
				id : "sysRoleMenuSaveBtn",
				iconCls : "icon-save",
				handler : function() {
					window.top.saveRoleMenu(div);
				}
			}, {
				text : "关闭",
				iconCls : "icon-cancel",
				handler : function() {
					window.top.$(div).dialog("close");
				}
			}],
			onOpen : function() {
				window.top.showButtonList("${menuId}", div.parent());
			}
		});
	}

	$(function() {
		$("#sysRoleListTable").myDatagrid({
			title : "角色数据列表",
			emptyMsg : "没有数据",
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			toolbar : "#sysRoleListToolbar",
			url : "${request.contextPath}/SysRoleController/queryAllSysRole",
			queryParams : {
				status : 1
			},
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			},
			columns : [[{
				title : "角色名称",
				field : "roleName",
				width : "20%",
				align : "center"
			}, {
				title : "角色描述",
				field : "roleInfo",
				width : "30%",
				align : "center"
			}, {
				title : "状态",
				field : "status",
				width : "10%",
				align : "center",
				formatter : function(value, rowData, rowIndex) {
					return rowData.statusStr;
				}
			}, {
				title : "操作",
				field : "str",
				width : "40%",
				align : "center",
				formatter : function(value, rowData, rowIndex) {
					var str = "<a role='view' class='table_btn' style='display:none;' rowid='"+ rowData.id +"' id='sysRoleViewBtn_"+ rowData.id +"'>查看</a>";
					str += "<a role='edit' class='table_btn' style='display:none;' rowid='"+ rowData.id +"' id='sysRoleEditBtn_"+ rowData.id +"'>修改</a>";
					str += "<a role='del' class='table_btn' style='display:none;' rowid='"+ rowData.id +"' id='sysRoleDelBtn_"+ rowData.id +"'>删除</a>";
					str += "<a role='roleMenu' class='table_btn' style='display:none;' rowid='"+ rowData.id +"' id='sysRoleMenuViewBtn_"+ rowData.id +"'>分配菜单</a>";
					return str;
				}
			}]],
			cache : false,
			pagination : true,
			pageSize : 20,
			idField : "id",
			showFooter : true,
			singleSelect : true,
			onLoadSuccess : function(data) {
				//查看按钮
				$(this).myDatagrid("getPanel").find("a[role='view']").linkbutton({
					iconCls : "icon-search",
					onClick : function() {
						viewSysRole($(this).attr("rowid"));
					}
				});
				
				//修改按钮
				$(this).myDatagrid("getPanel").find("a[role='edit']").linkbutton({
					iconCls : "icon-edit",
					onClick : function() {
						editSysRole($(this).attr("rowid"));
					}
				});
				
				//删除按钮
				$(this).myDatagrid("getPanel").find("a[role='del']").linkbutton({
					iconCls : "icon-remove",
					onClick : function() {
						$.confirmDialog({
							url : "${request.contextPath}/SysRoleController/deleteSysRole?id=" + $(this).attr("rowid"),
							text : "删除角色",
							reloadTableObject : $("#sysRoleListTable")
						});
					}
				});
				
				//分配权限按钮
				$(this).myDatagrid("getPanel").find("a[role='roleMenu']").linkbutton({
					iconCls : "icon-remove",
					onClick : function() {
						viewRoleMenu($(this).attr("rowid"));
					}
				});
				window.top.showButtonList("${menuId}", $("body"), window.top.$("#${iframeId}").get(0).contentWindow);
			}
		});
	});
</script>
</body>
<#include "/include/footer.ftl">