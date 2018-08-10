<#include "include/head.ftl">
<div class="container">
	<h1 style="line-height: 2em;"></h1>
	<br>
	<br>
	<br>
	<br>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div id="loginDiv">
				
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>
<form class="" name="loginForm" id="loginForm">
	<div class="form-group">
		<input type="text" class="form-control input-lg" id="loginName" name="loginName" placeholder="登录名" />
	</div>
	<div class="form-group">
		<input type="password" class="form-control input-lg" id="loginPwd" name="loginPwd" placeholder="登录密码" />
	</div>
	<div class="form-group">
		<input type="text" class="form-control input-lg" style="width:80%;display:inline" id="checkCode" name="checkCode" maxlength="4" placeholder="验证码" />
		<img id="checkCodeImg" src="${request.contextPath}/refeshCheckCode" style="cursor:pointer" />
	</div>
	<div class="form-group">
		<input type="button" value="立刻登录" id="loginBtn" class="btn btn-primary btn-lg btn-block" name="loginBtn"/>
		<!-- <span><a href="#">找回密码</a></span> <span></span> -->
	</div>
</form>
<script>
	$(function() {
	
		$("#loginForm").keydown(function(e) {
			if (e.keyCode == 13) {
				$("#loginBtn").click();
			}
		});
	
		$("#loginDiv").panel({
			panelClass : DEFAULT_PANEL_CLASS,
			title : "用户登录",
			content : $("#loginForm"),
			footContent : "<div id='messageAlert'></div>",
			showFooter : true
		});
		$("#checkCodeImg").click(function() {
			this.src = "${request.contextPath}/refeshCheckCode?t=" + new Date();
		});
		$("#loginBtn").click(function() {
			$(this).prop("disabled", "disabled");
			var loginName = $("#loginName");
			var loginPwd = $("#loginPwd");
			var checkCode = $("#checkCode");
			if (loginName.val() == "") {
				$("#messageAlert").alerts({
					content : "请输入登录名。",
					type : "danger"
				});
				$(this).removeAttr("disabled");
				loginName.focus();
				return;
			}
			if (loginPwd.val() == "") {
				$("#messageAlert").alerts({
					content : "请输入密码。",
					type : "danger"
				});
				$(this).removeAttr("disabled");
				loginPwd.focus();
				return;
			}
			if (checkCode.val() == "") {
				$("#messageAlert").alerts({
					content : "请输入验证码。",
					type : "danger"
				});
				$(this).removeAttr("disabled");
				checkCode.focus();
				return;
			}
			$.post("${request.contextPath}/doLogin", {
				loginName : loginName.val(),
				loginPwd : loginPwd.val(),
				checkCode : checkCode.val()
			}, function(data) {
				if (!data) {
					$("#messageAlert").alerts({
						content : "登录失败",
						type : "danger"
					});
					$("#loginBtn").removeAttr("disabled");
					$("#checkCodeImg").click();
					return;
				}
				if (data.code != 200) {
					$("#messageAlert").alerts({
						content : data.message,
						type : "danger"
					});
					$("#loginBtn").removeAttr("disabled");
					$("#checkCodeImg").click();
					return;
				}
				location.href = "${request.contextPath}/index";
			});
		});
	});
</script>
<#include "include/footer.ftl">