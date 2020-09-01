<#-- 引入布局指令的命名空间 -->
<#import "layout/defaultLayout.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
	<#include '${viewPage}.ftlh'> 
</@defaultLayout.layout>