<#include "head.ftl">
<div class="container-fluid">
	<div class="row clearfix" id="topBanner" style="background-color: #337ab7;color: white">
		<!-- 顶部banner -->
		<#include "topBanner.ftl">
	</div>
	<div class="row clearfix" style="margin-top: 4px">
		<!-- 左侧菜单 -->
		<div class="col-md-2 column sidebar" id="left">
			<div class="" id="leftMenu"></div>
		</div>
		
		<!-- 页面主内容 -->
		<div class="col-md-10 column" id="body">
		</div>
	</div>
</div>
<script>
	$(function() {
		$("#leftMenu").accordion({
			panelClass : DEFAULT_PANEL_CLASS,
			selected : 0,
			idField : "id",
			pidField : "parentMenuId",
			textField : "menuName",
			ajaxParam : {
				dataType : "json",
				url: "${request.contextPath}/SysMenuController/queryUserMenuFromSession",
				type: "POST"
			},
			onClickMenu : function(item, parent) {
				window.location.href = "${request.contextPath}" + item.menuUrl;
			},
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			}
		});
	
		$("#body").append($("[role='body']"));
	});
</script>
<#include "footer.ftl">
