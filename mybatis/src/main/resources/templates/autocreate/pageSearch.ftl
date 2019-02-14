<!DOCTYPE html>
<div id="${modelNameFirstLow}SearchDiv" class="search_text">
	<a id="searchBtn" style="margin-left:20px">查询</a>
	<a id="add${modelName}Btn" style="margin-left:20px;">新增</a>
</div>
<script type="text/javascript">
	$(function(){
		
		$("#${modelNameFirstLow}SearchDiv #searchBtn").linkbutton({
			iconCls : "icon-search",
			onClick : function() {
				search${modelName}();
			}
		});
		$("#${modelNameFirstLow}SearchDiv #add${modelName}Btn").linkbutton({
			iconCls : "icon-add",
			onClick : function() {
				edit${modelName}(0);
			}
		});
		$("#${modelNameFirstLow}SearchDiv,#${modelNameFirstLow}SearchDiv input[type='text']").keydown(function(e) {
			if (e.keyCode == 13) {
				search${modelName}();
			}
		});
		function search${modelName}() {
			$("#${modelNameFirstLow}ListTable").datagrid("load", {
				
			});
		}
	});
</script>

