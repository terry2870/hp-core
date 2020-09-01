<script>
	$(function() {
		$(".nav-main").leftMenu({
			expandId : 10,
			selectedId : 12,
			dataList : [
				{"id":1,"pid":0,"text":"菜单1"},
				{"id":2,"pid":0,"text":"菜单2"},
				{"id":5,"pid":1,"text":"菜单5"},
				{"id":6,"pid":1,"text":"菜单6"},
				{"id":7,"pid":1,"text":"菜单7"},
				{"id":8,"pid":2,"text":"菜单8"},
				{"id":9,"pid":2,"text":"菜单9"},
				{"id":10,"pid":0,"text":"菜单10"},
				{"id":11,"pid":10,"text":"菜单11"},
				{"id":12,"pid":10,"text":"菜单12"},
				{"id":13,"pid":0,"text":"菜单13"},
				{"id":14,"pid":13,"text":"菜单14"},
				{"id":15,"pid":13,"text":"菜单15"}
			],
			onClickMenu : function(item, index) {
				//alert(JSON.stringify(item));
			},
			contextPath : "${request.contextPath}"
		});
	});
</script>