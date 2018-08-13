<#include "${request.contextPath}/include/head.ftl">
<body class="easyui-layout" fit="true">
	<div region="north" title="查询条件"id="userSearchDiv" style="height:70px;border-style: none;overflow: hidden;">
		
	</div>
	<div region="center" id="userListDiv">
		<table id="userListTable"></table>
	</div>
<script>

	//新增或修改用户
	function editSysUser(index) {
		var data = index === undefined ? {userId:0} : $("#userListTable").myDatagrid("getRowDataByIndex", index);
		var div = $("<div>").appendTo($(window.top.document.body));
		var title = data.userId === 0 ? "新增用户" : "修改用户";
		window.top.$(div).myDialog({
			width : 500,
			height : 670,
			title : title,
			href : "<t:path />/jsp/sysManage/sysUser/sysUserEdit.jsp",
			method : "post",
			queryParams : data,
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "保存",
				disabled : true,
				id : "saveSysUserBtn",
				iconCls : "icon-save",
				handler : function() {
					window.top.$("#userEditForm").form("submit", {
						url : "<t:path />/SysUserController/saveSysUser.do",
						onSubmit : function(param) {
							if (!window.top.$("#userEditForm").form("validate")) {
								return false;
							}
							var roleIds = window.top.$("#userEditForm #roleIdTD").checkboxList("getCheckedValue");
							param.roleIds = roleIds.join(",");
							window.top.$.messager.progress({
								title : "正在执行",
								msg : "正在执行，请稍后..."
							});
							return true;
						},
						success : function(data) {
							window.top.$.messager.progress("close");
							data = JSON.parse(data);
							if (data.code == "<%=CodeEnum.SUCCESS.getCode()%>") {
								window.top.$(div).dialog("close");
								$("#userListTable").datagrid("reload");
								window.top.$.messager.show({
									title : "提示",
									msg : title + "成功！"
								});
							} else {
								window.top.$.messager.alert("失败", data.message, "error");
							}
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
				window.top.showButtonList("<t:write name='menuId' />", div.parent());
			}
		});
	}
	
	//查看用户详情
	function viewSysUser(index) {
		var data = $("#userListTable").myDatagrid("getRowDataByIndex", index);
		var div = $("<div>").appendTo($(window.top.document.body));
		window.top.$(div).myDialog({
			width : 500,
			height : 670,
			title : "用户详细",
			href : "<t:path />/jsp/sysManage/sysUser/sysUserEdit.jsp",
			method : "post",
			queryParams : data,
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
		var data = $("#userListTable").myDatagrid("getRowDataByIndex", index);
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
					$("#userListTable").datagrid("reload");
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
		$("#userListTable").myDatagrid({
			title : "用户列表",
			emptyMsg : "没有数据",
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
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
				width : "10%",
				align : "center",
				sortable : true
			}, {
				title : "用户名称",
				field : "userName",
				width : "10%",
				align : "center",
				sortable : true
			}, {
				title : "手机号码",
				field : "mobile",
				width : "10%",
				align : "center"
			}, {
				title : "用户身份",
				field : "identity",
				width : "10%",
				align : "center",
				sortable : true,
				formatter : function(value, rowData) {
					return rowData.identityStr;
				}
			}, {
				title : "开户时间",
				field : "createTime",
				width : "10%",
				align : "center",
				sortable : true,
				formatter : function(value, rowData) {
					return rowData.createTimeStr;
				}
			}, {
				title : "用户状态",
				field : "status",
				width : "10%",
				align : "center",
				sortable : true,
				formatter : function(value, rowData) {
					return rowData.statusStr;
				}
			}, {
				title : "操作",
				field : "str",
				width : "40%",
				align : "center",
				formatter : function(value, rowData, rowIndex) {
					var str = "<a role='view' style='margin-left:10px;display:none;' index='"+ rowIndex +"' id='viewSysUserBtn_"+ rowData.userId +"'>查看</a>";
					str += "<a role='edit' style='margin-left:10px;display:none;' index='"+ rowIndex +"' id='editSysUserBtn_"+ rowData.userId +"'>修改</a>";
					str += "<a role='del' style='margin-left:10px;display:none;' index='"+ rowIndex +"' id='delSysUserBtn_"+ rowData.userId +"'>删除</a>";
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
                    	viewSysUser($(this).attr("index"));
                    }
                });
				$(this).myDatagrid("getPanel").find("a[role='edit']").linkbutton({
                    iconCls : "icon-edit",
                    onClick : function() {
                    	editSysUser($(this).attr("index"));
                    }
                });
				$(this).myDatagrid("getPanel").find("a[role='del']").linkbutton({
                    iconCls : "icon-remove",
                    onClick : function() {
                    	delSysUser($(this).attr("index"));
                    }
                });
				//window.top.showButtonList("<t:write name='menuId' />", $("body"), window.top.$("#<t:write name='iframeId' />").get(0).contentWindow);
			}
		});
	});
</script>
</body>
<#include "${request.contextPath}/include/footer.ftl">