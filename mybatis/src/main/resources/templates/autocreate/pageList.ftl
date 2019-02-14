${'<#include "/include/head.ftl">'}
<body>
	<table id="${modelNameFirstLow}ListTable"></table>
	<div id="toolbar">
		${'<#include "/other/${modelNameFirstLow}Search.ftl">'}
	</div>
<script>

	//新增或修改${tableComment}
	function edit${modelName}(id) {
		$.saveDialog({
			title : id === 0 ? "新增${tableComment}数据" : "修改${tableComment}数据",
			width : "40%",
			height : "80%",
			href : "${contextPath}/RedirectController/forward?redirectUrl=other/${modelNameFirstLow}Edit&id=" + id,
			handler : {
				formObjectId : "${modelNameFirstLow}EditForm",
				url : "${contextPath}/${modelName}Controller/save${modelName}",
				reloadTableObject : $("#${modelNameFirstLow}ListTable")
			}
		});
	}
	
	//查看${tableComment}详情
	function view${modelName}(id) {
		$.saveDialog({
			title : "${tableComment}数据详细",
			width : "40%",
			height : "80%",
			href : "${contextPath}/RedirectController/forward?redirectUrl=other/${modelNameFirstLow}Edit&id=" + id,
			showSaveBtn : false
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
			url : "${contextPath}/${modelName}Controller/queryAll${modelName}",
			loadFilter : defaultLoadFilter,
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
					var str = "<a role='view' style='margin-left:10px;' rowid='"+ rowData.id +"' id='view${modelName}Btn_"+ rowData.id +"'>查看</a>";
					str += "<a role='edit' style='margin-left:10px;' rowid='"+ rowData.id +"' id='edit${modelName}Btn_"+ rowData.id +"'>修改</a>";
					str += "<a role='del' style='margin-left:10px;' rowid='"+ rowData.id +"' id='del${modelName}Btn_"+ rowData.id +"'>删除</a>";
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
						$.confirmDialog({
							url : "${contextPath}/${modelName}Controller/delete${modelName}?id=" + $(this).attr("rowid"),
							text : "删除${tableComment}",
							reloadTableObject : $("#${modelNameFirstLow}ListTable")
						});
					}
				});
			}
		});
	});
</script>
</body>
<#include "/include/footer.ftl">