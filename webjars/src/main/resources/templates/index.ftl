<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#include "/include/head.ftl">
<body class="easyui-layout" id="ff"  data-options="fit:true">
	<div data-options="region:'north'" style="height: 50px;border-style: none;overflow: hidden;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="top_bg01" style="back">
			<tr>
				<td width="20%" align="center" class="top_banner">管理后台系统</td>
				<td height="26" valign="middle"><div align="right" style="margin-right: 100px">你好，<span id="userName"></span>&nbsp;&nbsp; |&nbsp; <a href="#" id="logout">注销</a></div></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'west',iconCls:'icon-reload',split:true,title:'导航菜单',minWidth:200" style="width: 200px;">
		<div id="left">
			
		</div>
	</div>
	<div data-options="region:'center'" style="overflow:hidden">
		<div id="right">
			<div title="首页" id="homePageDiv">
				
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$.post("${request.contextPath}/NoFilterController?method=getUserInfo", function(data) {
				var user = data.data;
				if (!user) {
					return;
				}
				$("#userName").html(user.userName);
			});
			
			$("#logout").linkbutton({
				plain:true,
				iconCls:'icon-cancel',
				onClick : function() {
					$.messager.confirm('确认','你确定要注销登录吗?',function(f){
						if (!f) {
							return ;
						}
						window.location.href = "${request.contextPath}/logout";
					});
				}
			});

			$("#right").myTabs({
				fit : true
			});
			
			$("#left").myAccordion({
				fit : true,
				rootPid : 0,
				idField : "id",
				pidField : "parentMenuId",
				textField : "menuName",
				ajaxParam : {
					cache : false,
					type : "POST",
					dataType : "json",
					url : "${request.contextPath}/NoFilterController?method=queryUserMenuFromSession"
				},
				onClickMenu : function(item, parent) {
					addTab(item);
				},
				loadFilter : function(data) {
					return defaultLoadFilter(data);
				}
			});
		});

		function addTab(item, forceClose) {
			if ($("#right").tabs("exists", item.menuName)) {
				if (forceClose === true) {
					$("#right").tabs("close", item.menuName);
					addTab(item);
					return;
				}
				$("#right").tabs("select", item.menuName);
			} else {
				var iframeId = "iframe_" + item.id;
				var iframe = $("<iframe>").attr({
					width : "100%",
					height : "100%",
					frameborder : 0,
					id : iframeId
				});
				if (item.menuUrl.indexOf("?") > 0) {
					iframe.attr("src", "${request.contextPath}"+ item.menuUrl +"&menuId="+ item.id + "&iframeId=" + iframeId);
				} else {
					iframe.attr("src", "${request.contextPath}"+ item.menuUrl +"?menuId="+ item.id + "&iframeId=" + iframeId);
				}
				$("#right").tabs("add", {
					title : item.menuName,
					content : iframe,
					closable : true,
					selected : true,
					cache : true
				});
			}
		}
		
		//列表，显示按钮
		function showButtonList(menuId, parentDiv, target) {
			$.post("${request.contextPath}/NoFilterController?method=querySessionButtonByMenuId", {
				menuId : menuId
			}, function(data) {
				data = defaultLoadFilter(data);
				$(data).each(function(index, item) {
					if (!item.buttonId) {
						return true;
					}
					var btn;
					if (target) {
						btn = target.$(parentDiv).find("[id^='"+ item.buttonId +"']");
						if (btn && btn.data("linkbutton")) {
							btn.linkbutton("enable");
							btn.show();
						}
					} else {
						btn = $(parentDiv).find("[id^='"+ item.buttonId +"']");
						if (btn && btn.data("linkbutton")) {
							btn.linkbutton("enable");
							btn.show();
						}
					}
				});
			});
		}
	</script>
</body>
<#include "/include/footer.ftl">