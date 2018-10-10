<div id="left"></div>
<script>
	$(function() {
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
</script>