<#include "${request.contextPath}/include/layout.ftl">
<div role="body">
	<!-- 路径导航 -->
	<ul id="breadcrumb"></ul>

	<!-- 搜索条件 -->
	<div id="sysUserQueryDiv"></div>
	
	<!-- 列表 -->
	<div id="sysUserListDiv"></div>
</div>
<script>
	$(function() {
		$("#breadcrumb").breadcrumb({
			data : [{
				text : "社区后台管理首页",
				href : "/admin/html/index.html"
			}, {
				text : "用户管理"
			}, {
				text : "真实用户列表"
			}]
		});
		
		$("#sysUserQueryDiv").panel({
			title : "查询条件",
			panelClass : DEFAULT_PANEL_CLASS,
			url : "${request.contextPath}/RedirectController/forward?redirectUrl=sysManage/sysUser/sysUserQuery"
		});
	
		//$("#sysUserSearchDiv")
		$("#sysUserListDiv").table({
			title : "用户列表",
			url : "${request.contextPath}/SysUserController/queryAllSysUser",
			striped : true,
			panelClass : DEFAULT_PANEL_CLASS,
			pagination : true,
			pageSize : 10,
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			},
			columns : [{
				title : "id",
				field : "id",
				align : "center"
			}, {
				title : "登录名",
				field : "loginName",
				align : "center"
			}, {
				title : "用户名",
				field : "userName",
				align : "center"
			}]
		});
	});
</script>