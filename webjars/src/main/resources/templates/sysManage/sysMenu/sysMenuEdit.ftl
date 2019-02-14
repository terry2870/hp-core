<form name="sysMenuEditForm" id="sysMenuEditForm" method="post">
	<input type="hidden" name="parentMenuId" id="parentMenuId" />
	<input type="hidden" name="id" id="id" />
	<input type="hidden" name="menuType" id="menuType" />
	<table align="center">
		<tr>
			<td width="30%" align="right">菜单名称：</td>
			<td width="70%">
				<input type="text" name="menuName" id="menuName" size="40" class="easyui-textbox" data-options="
					required : true,
					validType : 'checkName',
					invalidMessage : '请输入正确的菜单名，菜单名不能输入形如（@#$）等特殊字符'
				" />
			</td>
		</tr>
		<tr id="menuUrlTr">
			<td align="right">菜单地址：</td>
			<td><input type="text" name="menuUrl" id="menuUrl" size="40" class="easyui-textbox" /></td>
		</tr>
		<tr id="extraUrlTr">
			<td align="right">额外菜单地址：</td>
			<td><input type="text" name="extraUrl" id="extraUrl" size="40" class="easyui-textbox" data-options="multiline:true,height:100" /></td>
		</tr>
		<tr id="buttonIdTr">
			<td align="right">按钮ID名称：</td>
			<td><input type="text" name="buttonId" id="buttonId" size="40" class="easyui-textbox" /></td>
		</tr>
		<tr>
			<td align="right">状态：</td>
			<td>
				<input name="status" id="status" size="40" class="easyui-combobox" data-options="
					url : '${request.contextPath}/NoFilterController?method=getEnumForSelect&className=com.hp.core.common.enums.StatusEnum',
					panelHeight : 100,
					editable : false,
					required : true,
					missingMessage : '状态必须选择！',
					loadFilter : function(data) {
						return defaultLoadFilter(data);
					}
				" />
			</td>
		</tr>
		<tr id="iconNameTr" style="display:none">
			<td align="right">图标名称：</td>
			<td>
				<input name="iconName" id="iconName" size="40" />
			</td>
		</tr>
		<tr>
			<td align="right">排序：</td>
			<td>
				<input name="sortNumber" id="sortNumber" size="40" class="easyui-numberbox" data-options="min : 0,max : 1000,required : true" />
			</td>
		</tr>
		<tr style="padding-top:20px">
			<td align="center" colspan="2" id="buttons">
				<a id="addRootMenu" menutype="1">增加根节点</a>
				<a id="addChildMenu" menutype="2">新增子节点</a>
				<a id="addMenuButton" menutype="3">新增按钮</a>
				<a id="deleteMenu">删除节点</a>
				<a id="saveMenu">保存</a>
			</td>
		</tr>
	</table>
</form>
<script>
	$(function() {
		let id = ${id!0};
		let menuType = "${menuType!}";
		let parentMenuId = "${parentMenuId!}";
		let menuName = "${menuName!}";
		let menuUrl = "${menuUrl!}";
		let status = ${status!1};
		let iconName = "${iconName!}";
		let sortNumber = "${sortNumber!}";
		let extraUrl = "${extraUrl!}";
		let buttonId = "${buttonId!}";
		
		
		
		//初始化页面
		$("#sysMenuEditForm").form("load", {
			id : id,
			menuType : menuType,
			parentMenuId : parentMenuId,
			menuName : menuName,
			menuUrl : menuUrl,
			status : status,
			iconName : iconName,
			sortNumber : sortNumber,
			extraUrl : extraUrl,
			buttonId : buttonId
		});
		
		
		if (id == "0") {
			$("#addRootMenu,#addChildMenu,#addMenuButton,#deleteMenu").hide();
		}
		if (menuType == "1") {
			$("#buttonIdTr,#menuUrlTr,#extraUrlTr").hide();
			$("#addMenuButton").hide();
		} else if (menuType == "2") {
			$("#buttonIdTr").hide();
			$("#addChildMenu").hide();
			//$("#iconNameTr").hide();
		} else if (menuType == "3") {
			$("#addChildMenu,#addMenuButton,#menuUrlTr").hide();
			$("#iconNameTr").hide();
		} else {
			$("#buttonIdTr,#addChildMenu,#addMenuButton,#deleteMenu,#saveMenu,#extraUrlTr").hide();
			$("#iconNameTr").hide();
		}
		
		//新增按钮
		$("#addRootMenu,#addChildMenu,#addMenuButton").linkbutton({
			iconCls : "icon-add",
			onClick : function() {
				let menutype = $(this).attr("menutype");
				let treeNode = $("#menuLeft").myTree("getSelected");
				let data = {
					id : 0,
					menuType : menutype,
					parentMenuId : menutype == "1" ? 0 : treeNode.attributes.id
				};
				$("#menuRight").panel("refresh", "${request.contextPath}/RedirectController/forward?redirectUrl=/sysManage/sysMenu/sysMenuEdit&" + $.param(data, true));
			}
		});
		
		//删除按钮
		$("#deleteMenu").linkbutton({
			iconCls : "icon-remove",
			onClick : function() {
				$.confirmDialog({
					url : "${request.contextPath}/SysMenuController/deleteSysMenu",
					params : {
						id : id
					},
					text : "删除菜单",
					callback : function() {
						$('#menuLeft').myTree("reload");
						$("#sysMenuEditForm").form("clear");
					}
				});
			}
		});
		
		//保存按钮
		$("#saveMenu").linkbutton({
			iconCls : "icon-save",
			onClick : function() {
				$.saveDialogHandler({
					formObject : $("#sysMenuEditForm"),
					url : "${request.contextPath}/SysMenuController/saveSysMenu",
					callback : function() {
						$('#menuLeft').myTree("reload");
						$("#sysMenuEditForm").form("clear");
					}
				});
			}
		});
	});
</script>

