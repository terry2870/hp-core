<#include "/include/head.ftl">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/login.css" />
<body style="background-color: #428bca;text-align: center;overflow: hidden;">
	<div class="login_div">
		<div class="login_top"></div>
		<div class="login_bg">
			<form id="f1" method="post">
				<div>
					<input type="text" id="loginName" name="loginName" class="easyui-textbox" style="width:80%;height:40px;padding:12px" data-options="
						prompt : '登录名',
						required : true,
						iconCls : 'icon-man',
						iconWidth : 38,
						cls : 'login_input',
						missingMessage : '请输入登录账号名！'
					" />
				</div>
				<div>
					<input type="password" id="loginPwd" name="loginPwd" class="easyui-textbox" style="width:80%;height:40px;padding:12px" data-options="
						prompt : 'password',
						required : true,
						iconCls : 'icon-lock',
						iconWidth : 38,
						cls : 'login_input',
						missingMessage : '请输入登录密码！'
					" />
				</div>
				<div>
					<input type="text" id="checkCode" name="checkCode" class="easyui-textbox" style="width:80%;height:40px;padding:12px" data-options="
						prompt : '请输入验证码',
						required : true,
						cls : 'login_input',
						missingMessage : '请输入验证码',
						validType : 'length[4,4]',
						invalidMessage : '验证码长度为4个字符！'
					" />
				</div>
				<div style="margin-top: 10px;text-align: left;margin-left: 30px">
					<img src="${request.contextPath}/refeshCheckCode" id="codeImg" title="点击刷新验证码" style="cursor:pointer;">
					<a href="#" id="refeshImg">看不清楚，换个一个</a>
				</div>
				<div style="margin-top: 10px;text-align: left;margin-left: 30px">
					<input type="image" src="${request.contextPath}/css/images/login/login_1_08.png" id="submitBtn" />
				</div>
			</form>
		</div>
		<div class="login_bottom"></div>
	</div>
	<script>
	$(function() {
		
		$("#submitBtn").hover(
			function() {
				$(this).attr("src", "${request.contextPath}/css/images/login/login_1_08a.png");
			},
			function() {
				$(this).attr("src", "${request.contextPath}/css/images/login/login_1_08.png");
			}
		);

		$("form").form({
			url : "${request.contextPath}/doLogin",
			onSubmit : function() {
				if (!$("form").form("validate")) {
					return false;
				}
				return true;
			},
			success : function(data) {
				if (data) {
					data = JSON.parse(data);
					if (data.code == CODE_SUCCESS) {
						window.location.href = "${request.contextPath}/index";
					} else {
						$.messager.alert("登录失败", data.message, "error", function() {
							refreshCode();
						});
					}
				} else {
					$.messager.alert("登录失败", data.message, "error", function() {
						refreshCode();
					});
				}
			}
		});
		
		function refreshCode() {
			$("#codeImg").attr("src", "${request.contextPath}/refeshCheckCode?" + Math.random());
		}
		$("#codeImg,#refeshImg").click(function() {
			refreshCode();
		});
	});
</script>
</body>
<#include "/include/footer.ftl">