<#macro layout>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>三级导航</title>
	<link rel="stylesheet" href="css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="css/left-side-menu.css">
	<link rel="stylesheet" type="text/css" href="font/iconfont.css">
	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
</head>
<body>
	<#include "layout/header.ftlh">
	<#include "layout/sidebar.ftlh">
	<#-- 在这里嵌入main content -->
	<div class="center-content">
		<#nested>
	</div>
	<#include "layout/footer.ftlh">
	<script type="text/javascript" src="js/bootstrap-plugins/bootstrap.default.js"></script>
	<script type="text/javascript" src="js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-plugins/leftMenu.js"></script>
	<script src="js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
</#macro>