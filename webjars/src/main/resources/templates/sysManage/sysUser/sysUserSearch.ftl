<div class="form-inline">
	<div class="form-group">
		<label class="sr-only" for="id">id</label>
		<input type="text" class="form-control" id="id" name="id" placeholder="id" />
	</div>
	<div class="form-group">
		<label class="sr-only" for="id">登录名</label>
		<input type="text" class="form-control" id="loginName" name="loginName" placeholder="登录名" />
	</div>
	<div class="form-group">
		<label class="sr-only" for="id">用户名</label>
		<input type="text" class="form-control" id="userName" name="userName" placeholder="用户名" />
	</div>
	<button type="button" id="searchBtn" class="btn btn-success">搜索</button>
</div>
<script>
	$(function() {
		$("#sysUserQueryDiv").keydown(function(e) {
			console.log('111');
			if (e.keyCode == 13) {
				$("#sysUserQueryDiv #searchBtn").click();
			}
		});
	
		$("#sysUserQueryDiv #searchBtn").click(function() {
			$("#sysUserListDiv").table("load", {
				id : $("#sysUserQueryDiv #id").val(),
				loginName : $("#sysUserQueryDiv #loginName").val(),
				userName : $("#sysUserQueryDiv #userName").val()
			});
		});
		
	});
</script>