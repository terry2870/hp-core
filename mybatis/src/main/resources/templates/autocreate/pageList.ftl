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
			href : "${r"${request.contextPath}"}/redirect/forward?redirectUrl=other/${modelNameFirstLow}Edit&id=" + id,
			handler : {
				formObjectId : "${modelNameFirstLow}EditForm",
				url : "${r"${request.contextPath}"}/${modelName}Controller/save${modelName}",
				reloadTableObject : $("#${modelNameFirstLow}ListTable")
			}
		});
	}

	$(function() {
		$("#${modelNameFirstLow}ListTable").myDatagrid({
			title : "${tableComment}数据列表",
			emptyMsg : "没有数据",
			toolbar : "#toolbar",
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			url : "${r"${request.contextPath}"}/${modelName}Controller/queryAll${modelName}",
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
					let str = "";
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
				$(this).myDatagrid("clearChecked");
				
				//编辑
				$(this).myDatagrid("getPanel").find("a[role='edit']").linkbutton({
					iconCls : "icon-edit",
					onClick : function() {
						edit${modelName}($(this).attr("rowid"));
					}
				});
				
				//删除
				$(this).myDatagrid("getPanel").find("a[role='del']").linkbutton({
					iconCls : "icon-remove",
					onClick : function() {
						$.confirmDialog({
							url : "${r"${request.contextPath}"}/${modelName}Controller/delete${modelName}?id=" + $(this).attr("rowid"),
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
${'<#include "/include/footer.ftl">'}