<div id="leftMenu"></div>
<script>
	$(function() {
		$("#leftMenu").leftMenu({
			expandId : 1,
			selectedId : 5,
			dataList : [
				{"id":1,"pid":0,"text":"菜单1"},
				{"id":2,"pid":0,"text":"菜单2"},
				{"id":3,"pid":0,"text":"菜单3"},
				{"id":4,"pid":0,"text":"菜单4"},
				{"id":5,"pid":1,"text":"菜单5"},
				{"id":6,"pid":1,"text":"菜单6"},
				{"id":7,"pid":1,"text":"菜单7"},
				{"id":8,"pid":2,"text":"菜单8"},
				{"id":9,"pid":2,"text":"菜单9"},
				{"id":10,"pid":0,"text":"菜单10"},
				{"id":11,"pid":0,"text":"菜单10"},
				{"id":12,"pid":0,"text":"菜单10"},
				{"id":13,"pid":0,"text":"菜单10"},
				{"id":14,"pid":0,"text":"菜单10"},
				{"id":15,"pid":0,"text":"菜单10"},
				{"id":16,"pid":0,"text":"菜单10"},
				{"id":17,"pid":0,"text":"菜单10"},
				{"id":18,"pid":0,"text":"菜单10"},
				{"id":19,"pid":0,"text":"菜单10"},
				{"id":10,"pid":0,"text":"菜单10"},
				{"id":21,"pid":0,"text":"菜单10"},
				{"id":22,"pid":0,"text":"菜单10"},
				{"id":23,"pid":0,"text":"菜单10"},
				{"id":24,"pid":0,"text":"菜单24"},
				{"id":25,"pid":0,"text":"菜单25"}
			],
			onClickMenu : function(item, index) {
				alert(JSON.stringify(item));
			}
		});
	});
</script>