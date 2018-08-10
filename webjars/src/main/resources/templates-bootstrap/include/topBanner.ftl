<div class="col-md-3 column">
	<h4>后台管理系统</h4>
</div>
<div class="col-md-5 column">
	
</div>
<div class="col-md-4 column">
	<h3 id="userInfo" style="color:white;font-size: 14px;float:right">
		
	</h3>
</div>
<script>
	$(function() {
		$.post("${request.contextPath}/SysUserController/getUserInfo", function(data) {
			data = data.data;
			var userInfo = $("#userInfo");
			$("<span>").html("欢迎您：" + data.userName).appendTo(userInfo);

			$("<a href='#'>").html("退出").css({
				"margin-left" : "10px",
				color : "white"
			}).appendTo(userInfo).click(function() {
				window.location.href = "${request.contextPath}/logout";
			});
		});
	});
</script>