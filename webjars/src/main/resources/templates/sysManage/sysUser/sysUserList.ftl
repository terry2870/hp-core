<#include "${request.contextPath}/include/head.ftl">
<body>
	<table id="sysUserListTable"></table>
	<div id="sysUserListToolbar">
		<#include "${request.contextPath}/sysManage/sysUser/sysUserSearch.ftl">
	</div>
<script>

	//新增或修改用户
	function editSysUser(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		var title = id === 0 ? "新增用户" : "修改用户";
		window.top.$(div).myDialog({
			width : "40%",
			height : "80%",
			title : title,
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysUser/sysUserEdit&id=" + id,
			method : "post",
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "保存",
				disabled : true,
				id : "sysUserSaveBtn",
				iconCls : "icon-save",
				handler : function() {
					$.saveDialog({
						dialogObject : window.top.$(div),
						formObject : window.top.$("#sysUserEditForm"),
						url : "${request.contextPath}/SysUserController/saveSysUser",
						reloadTableObject : $("#sysUserListTable"),
						onSubmit : function(param) {
							var roleIds = window.top.$("#sysUserEditForm #roleIdTD").checkboxList("getCheckedValue");
							param.roleIds = roleIds.join(",");
						}
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
	
	//查看用户详情
	function viewSysUser(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		window.top.$(div).myDialog({
			width : "40%",
			height : "80%",
			title : "用户详细",
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysUser/sysUserEdit&id=" + id,
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
	
	//删除用户
	function delSysUser(index) {
		var data = $("#sysUserListTable").myDatagrid("getRowDataByIndex", index);
		window.top.$.messager.confirm("确认", "您确定要删除该用户吗？", function(flag) {
			if (!flag) {
				return;
			}
			window.top.$.messager.progress({
				title : "正在执行",
				msg : "正在执行，请稍后..."
			});
			$.post("<t:path />/SysUserController/deleteSysUser.do", {
				userId : data.userId
			}, function(data) {
				window.top.$.messager.progress("close");
				if (data.code == "<%=CodeEnum.SUCCESS.getCode()%>") {
					$("#sysUserListTable").datagrid("reload");
					window.top.$.messager.show({
						title : "提示",
						msg : "删除用户成功！"
					});
				} else {
					window.top.$.messager.alert("失败", data.message, "error");
				}
			});
		});
	}

	$(function() {
		$("#sysUserListTable").myDatagrid({
			title : "用户列表",
			emptyMsg : "没有数据",
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			toolbar : "#sysUserListToolbar",
			url : "${request.contextPath}/SysUserController/queryAllSysUser",
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			},
			queryParams : {
				status : 1
			},
			columns : [[{
				title : "登录名",
				field : "loginName",
				width : "15%",
				align : "center"
			}, {
				title : "用户名",
				field : "userName",
				width : "15%",
				align : "center"
			}, {
				title : "手机号码",
				field : "mobile",
				width : "10%",
				align : "center"
			}, {
				title : "用户身份",
				field : "identityStr",
				width : "10%",
				align : "center"
			}, {
				title : "开户时间",
				field : "createTimeStr",
				width : "15%",
				align : "center"
			}, {
				title : "用户状态",
				field : "statusStr",
				width : "10%",
				align : "center"
			}, {
				title : "操作",
				field : "str",
				width : "25%",
				align : "center",
				formatter : function(value, rowData, rowIndex) {
					var str = "<a role='view' class='table_btn' style='display:none' rowid='"+ rowData.id +"' id='sysUserViewBtn_"+ rowData.id +"'>查看</a>";
					str += "<a role='edit' class='table_btn' style='display:none;display:none' rowid='"+ rowData.id +"' id='sysUserEditBtn_"+ rowData.id +"'>修改</a>";
					str += "<a role='del' class='table_btn' style='display:none;display:none' rowid='"+ rowData.id +"' id='sysUserDelBtn_"+ rowData.id +"'>删除</a>";
					return str;
				}
			}]],
			cache : false,
			pagination : true,
			pageSize : 20,
			idField : "id",
			singleSelect : true,
			onLoadSuccess : function(data) {
				$(this).myDatagrid("getPanel").find("a[role='view']").linkbutton({
					iconCls : "icon-search",
					onClick : function() {
						viewSysUser($(this).attr("rowid"));
					}
				});
				$(this).myDatagrid("getPanel").find("a[role='edit']").linkbutton({
					iconCls : "icon-edit",
					onClick : function() {
						editSysUser($(this).attr("rowid"));
					}
				});
				$(this).myDatagrid("getPanel").find("a[role='del']").linkbutton({
					iconCls : "icon-remove",
					onClick : function() {
						$.confirmDialog({
							url : "${request.contextPath}/SysUserController/deleteSysUser?id=" + $(this).attr("rowid"),
							text : "删除用户",
							reloadTableObject : $("#sysUserListTable")
						});
					}
				});
				window.top.showButtonList("${menuId}", $("body"), window.top.$("#${iframeId}").get(0).contentWindow);
			}
		});
	});
</script>
</body>
<#include "${request.contextPath}/include/footer.ftl">