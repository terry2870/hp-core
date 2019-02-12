<form id="sysRoleMenuForm" name="sysRoleMenuForm" method="post">
	<ul id="roleMenuTree"></ul>
</form>
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
</script>


