<ul id="roleMenuTree"></ul>
<script>
	$(function() {
		$("#roleMenuTree").myTree({
			animate : true,
			checkbox : true,
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
				if (!$(this).myTree("isLeaf", node.target)) {
					$(this).myTree("toggle", node.target);
				}
			},
			onLoadSuccess : function(node, data) {
				let tree = $(this);
				$(this).myTree("expandAll");
				$.post("${request.contextPath}/SysRoleController/selectMenuByRoleId", {
					roleId : "${roleId}"
				}, function(json) {
					if (!json || json.code != CODE_SUCCESS || !json.data) {
						return;
					}
					$(json.data).each(function(i, item) {
						let t = tree.myTree("find", item);
						if (t) {
							tree.myTree("check", t.target);
						}
					});
				});
			}
		});
	});
	function saveRoleMenu(div) {
		var check = $("#roleMenuTree").myTree("getChecked");
		if (!check || check.length == 0) {
			$.messager.alert("提示", "请至少分配一个菜单！", "error");
			return;
		}
		var menuArr = [];
		$(check).each(function(i, item) {
			if ($("#roleMenuTree").myTree("isLeaf", item.target)) {
				menuArr.push(item.attributes.id);
			}
		});
		$.messager.progress({
			title : "正在执行",
			msg : "正在执行，请稍后..."
		});
		$.post("${request.contextPath}/SysRoleController/saveSysRoleMenu", {
			menuIds : menuArr.join(","),
			roleId : "${roleId}"
		}, function(json) {
			$.messager.progress("close");
			if (!json) {
				return;
			}
			if (json.code == CODE_SUCCESS) {
				$(div).dialog("close");
				$.messager.show({
					title : "提示",
					msg : "分配菜单权限成功！"
				});
			} else {
				$.messager.alert("失败", json.message, "error");
			}
		});
	}
</script>


