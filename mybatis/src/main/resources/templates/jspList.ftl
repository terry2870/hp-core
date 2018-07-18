<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="t" uri="/my-tags" %>
<!DOCTYPE html>
<html>
<head>
<title>main</title>
<jsp:include page="/jsp/common/include.jsp" flush="true" />
</head>
<body class="easyui-layout" fit="true">
	<div region="north" title="查询条件" style="height: 70px; border-style: none">
		<jsp:include page="${modelNameFirstLow}Search.jsp" flush="true" />
	</div>
	<div region="center" id="${modelNameFirstLow}ListDiv">
		<table id="${modelNameFirstLow}ListTable"></table>
	</div>
<script>

	//新增或修改${tableComment}
	function edit${modelName}(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		var title = id === 0 ? "新增${tableComment}数据" : "修改${tableComment}数据";
		window.top.$(div).myDialog({
			width : "40%",
			height : "70%",
			title : title,
			href : "<t:path/>/jsp/newfile/${modelNameFirstLow}Edit.jsp",
			method : "post",
			queryParams : {
				id : id
			},
			modal : true,
			collapsible : true,
			cache : false,
			buttons : [{
				text : "保存",
				id : "save${modelName}Btn",
				disabled : true,
				iconCls : "icon-save",
				handler : function() {
					window.top.$("#${modelNameFirstLow}EditForm").form("submit", {
						url : "<t:path />/${modelName}Controller/save${modelName}.do",
						onSubmit : function(param) {
							if (!window.top.$("#${modelNameFirstLow}EditForm").form("validate")) {
								return false;
							}
							window.top.$.messager.progress({
								title : "正在执行",
								msg : "正在执行，请稍后..."
							});
							return true;
						},
						success : function(data) {
							window.top.$.messager.progress("close");
							data = JSON.parse(data);
							if (data.code == CODE_SUCCESS) {
								window.top.$(div).dialog("close");
								$("#${modelNameFirstLow}ListTable").datagrid("reload");
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
	
	//查看${tableComment}详情
	function view${modelName}(id) {
		var div = $("<div>").appendTo($(window.top.document.body));
		window.top.$(div).myDialog({
			width : "40%",
			height : "70%",
			title : "${tableComment}数据详细",
			href : "<t:path/>/jsp/newfile/${modelNameFirstLow}Edit.jsp",
			method : "post",
			queryParams : {
				id : id
			},
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
	
	//删除${tableComment}
	function del${modelName}(id) {
		window.top.$.messager.confirm("确认", "您确定要删除该${tableComment}数据吗？", function(flag) {
			if (!flag) {
				return;
			}
			window.top.$.messager.progress({
				title : "正在执行",
				msg : "正在执行，请稍后..."
			});
			$.post("<t:path />/${modelName}Controller/delete${modelName}.do", {
				id : id
			}, function(data) {
				window.top.$.messager.progress("close");
				if (data.code == CODE_SUCCESS) {
					$("#${modelNameFirstLow}ListTable").datagrid("reload");
					window.top.$.messager.show({
						title : "提示",
						msg : "删除数据成功！"
					});
				} else {
					window.top.$.messager.alert("失败", data.message, "error");
				}
			});
		});
	}

	$(function() {
		$("#${modelNameFirstLow}ListTable").myDatagrid({
			title : "${tableComment}数据列表",
			emptyMsg : "没有数据",
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			url : "<t:path />/${modelName}Controller/queryAll${modelName}.do",
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			},
			columns : [[{
				title : "id",
				field : "id",
				width : "10%",
				align : "center"
			}, {
				title : "title",
				field : "title",
				width : "10%",
				align : "center"
			}, {
				title : "操作",
				field : "str",
				width : "80%",
				align : "center",
				formatter : function(value, rowData, rowIndex) {
					var str = "<a role='view' style='margin-left:10px;display:none;' rowid='"+ rowData.id +"' id='view${modelName}Btn_"+ rowData.id +"'>查看</a>";
					str += "<a role='edit' style='margin-left:10px;display:none;' rowid='"+ rowData.id +"' id='edit${modelName}Btn_"+ rowData.id +"'>修改</a>";
					str += "<a role='del' style='margin-left:10px;display:none;' rowid='"+ rowData.id +"' id='del${modelName}Btn_"+ rowData.id +"'>删除</a>";
					return str;
				}
			}]],
			cache : false,
			pagination : true,
			pageSize : 50,
			idField : "id",
			showFooter : true,
			singleSelect : true,
			onLoadSuccess : function(data) {
				$(this).myDatagrid("getPanel").find("a[role='view']").linkbutton({
					iconCls : "icon-search",
					onClick : function() {
						view${modelName}($(this).attr("rowid"));
					}
				});
				
				$(this).myDatagrid("getPanel").find("a[role='edit']").linkbutton({
					iconCls : "icon-edit",
					onClick : function() {
						edit${modelName}($(this).attr("rowid"));
					}
				});
				
				$(this).myDatagrid("getPanel").find("a[role='del']").linkbutton({
					iconCls : "icon-remove",
					onClick : function() {
						var id = $(this).attr("rowid");
						window.top.$.messager.confirm("确认", "您确定要删除该${tableComment}吗？", function(flag) {
							if (!flag) {
								return;
							}
							window.top.$.messager.progress({
								title : "正在执行",
								msg : "正在执行，请稍后..."
							});
							$.post("<t:path />/${modelName}Controller/delete${modelName}.do", {
								id : id
							}, function(data) {
								window.top.$.messager.progress("close");
								if (data.code == CODE_SUCCESS) {
									$("#${modelNameFirstLow}ListTable").myDatagrid("reload");
									window.top.$.messager.show({
										title : "提示",
										msg : "删除${tableComment}成功！"
									});
								} else {
									window.top.$.messager.alert("失败", data.message, "error");
								}
							});
						});
					}
				});
				
				window.top.showButtonList("<t:write name='menuId' />", $("body"), window.top.$("#<t:write name='iframeId' />").get(0).contentWindow);
			}
		});
	});
</script>
</body>
</html>

