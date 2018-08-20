<#include "${request.contextPath}/include/head.ftl">
<body class="easyui-layout">
	<div region="west" style="width: 300px;">
		<ul id="menuLeft"></ul>
	</div>
	<div region="center">
		<div id="menuRight" style="margin-top:50px"></div>
	</div>
<script>
	$(function() {
		$("#menuLeft").myTree({
			animate : true,
			lines : true,
			ajaxParam : {
				url : "${request.contextPath}/SysMenuController/queryAllSysMenu",
				cache : false
			},
			idField : "id",
			pidField : "parentMenuId",
			textField : "menuName",
			rootPid : 0,
			onClick : function(node) {
				$("#menuRight").panel("refresh", "${request.contextPath}/RedirectController/forward?redirectUrl=/sysManage/sysMenu/sysMenuEdit&" + $.param(node.attributes, true));
			}
		});
		$("#menuRight").panel({
			title : "编辑菜单",
			fit : true,
			cache : false,
			href : "${request.contextPath}/RedirectController/forward?redirectUrl=/sysManage/sysMenu/sysMenuEdit"
		});
	});
</script>
</body>
<#include "${request.contextPath}/include/footer.ftl">