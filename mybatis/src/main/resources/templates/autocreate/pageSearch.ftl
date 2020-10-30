<!DOCTYPE html>
<div id="${modelNameFirstLow}SearchDiv" class="search_text">
	<a id="searchBtn" style="margin-left:20px">查询</a>
	<a id="add${modelName}Btn" style="margin-left:20px;">新增</a>
</div>
<script type="text/javascript">
	$(function(){
		
		//查询
		$("#${modelNameFirstLow}SearchDiv #searchBtn").linkbutton({
			iconCls : "icon-search",
			onClick : function() {
				search${modelName}();
			}
		});
		
		//新增
		$("#${modelNameFirstLow}SearchDiv #add${modelName}Btn").linkbutton({
			iconCls : "icon-add",
			onClick : function() {
				edit${modelName}(0);
			}
		});
		
		//支持回车键
		$("#${modelNameFirstLow}SearchDiv,#${modelNameFirstLow}SearchDiv input[type='text']").keydown(function(e) {
			if (e.keyCode == 13) {
				search${modelName}();
			}
		});
		
		//搜索
		function search${modelName}() {
			$("#${modelNameFirstLow}ListTable").datagrid("load", {
				
			});
		}
	});
</script>

