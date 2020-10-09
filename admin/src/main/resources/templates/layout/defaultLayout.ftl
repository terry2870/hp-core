<#macro layout>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>页面首页</title>
<link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet" >
<link href="${request.contextPath}/css/jquery.leftMenu.css" rel="stylesheet" >
<link href="${request.contextPath}/css/main-body.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/bootstrap-plugins/bootstrap.textbox.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${request.contextPath}/js/jquery-3.5.1.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<#include 'layout/header.ftlh' />
		<div class="row main">
			<div class="col nav-main">
				<#include 'layout/nav.ftlh' />
			</div>
			<div class="col main-body">
				<#-- 在这里嵌入main content -->
				<#nested>
			</div>
		</div>
	</div>
	<#include "layout/footer.ftlh" />
	<script type="text/javascript" src="${request.contextPath}/js/jquery.loadSuccess.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.default.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.breadcrumb.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.breadcrumb.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.card.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.validatebox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.textbox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.combo.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.combobox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.form.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.dialog.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.messager.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.progress.js"></script>
	
	<script type="text/javascript" src="${request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/jquery.leftMenu.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/popper.min.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap.min.js"></script>
	
	
	<script type="text/javascript" src="${request.contextPath}/js/ztree/jquery.ztree.all.min.js"></script>
</body>
</html>
</#macro>