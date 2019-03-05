<#include "/include/head.ftl">
<body>
	<table id="sysRoleListTable"></table>
	<div id="sysRoleListToolbar">
		<#include "/sysManage/sysRole/sysRoleSearch.ftl">
	</div>
<script>

	//新增或修改角色
	function editSysRole(id) {
		$.saveDialog({
			title : id === 0 ? "新增角色数据" : "修改角色数据",
			width : 500,
			height : 400,
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleEdit&id=" + id,
			handler : {
				formObjectId : "sysRoleEditForm",
				url : "${request.contextPath}/SysRoleController/saveSysRole",
				reloadTableObject : $("#sysRoleListTable")
			}
		});
	}
	
	//查看角色详情
	function viewSysRole(id) {
		$.saveDialog({
			title : "角色数据详细",
			width : 500,
			height : 400,
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleEdit&id=" + id,
			showSaveBtn : false
		});
	}
	
	//分配角色菜单
	function viewRoleMenu(roleId) {
		$.saveDialog({
			title : "分配菜单",
			width : "30%",
			height : "90%",
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysRole/sysRoleMenuEdit&roleId=" + roleId,
			handler : {
				formObjectId : "sysRoleMenuForm",
				onSubmit : function(param) {
					var check = window.top.$("#roleMenuTree").myTree("getChecked");
					if (!check || check.length == 0) {
						window.top.$.messager.alert("提示", "请至少分配一个菜单！", "error");
						return false;
					}
					var menuArr = [];
					$(check).each(function(i, item) {
						if (window.top.$("#roleMenuTree").myTree("isLeaf", item.target)) {
							menuArr.push(item.attributes.id);
						}
					});
					param.menuIds = menuArr.join(",");
					param.roleId = roleId;
				},
				url : "${request.contextPath}/SysRoleController/saveSysRoleMenu"
			}
		});
	}

	$(function() {
		$("#sysRoleListTable").myDatagrid({
			title : "角色数据列表",
			emptyMsg : "没有数据",
			fit : true,
			fitColumns : true,
			nowrap : false,
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
					iconCls : "icon-more",
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