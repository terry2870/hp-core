<#include "head.ftl">
<div class="container-fluid">
	<div class="row clearfix" id="topBanner" style="background-color: #337ab7;color: white">
		<!-- 顶部banner -->
		顶部banner new
	</div>
	<div class="row clearfix" style="margin-top: 4px">
		<!-- 左侧菜单 -->
		<div class="col-md-2 column" id="left">
			<div id="leftMenu"></div>
		</div>
		
		<!-- 页面主内容 -->
		<div class="col-md-10 column" id="body">
		</div>
	</div>
</div>
<script>
	$(function() {
		$("#leftMenu").accordion({
			panelClass : "panel-primary",
			idField : "menuId",
			pidField : "parentMenuId",
			textField : "menuName",
			ajaxParam : {
				dataType : "json",
				url: contextPath + '/admin/SysMenuController/getSysMenuList.do',
				type: "POST"
			},
			onClickMenu : function(item, parent) {
				$("body").find("div.fade").remove();
				$("#body").load(contextPath + item.menuUrl);
			},
			loadFilter : function(data) {
				if (!data || data.code != 200) {
					return null;
				}
				return data.data;
			}
		});
	
		$("#body").append($("[role='body']"));
	});
</script>
<#include "footer.ftl">
