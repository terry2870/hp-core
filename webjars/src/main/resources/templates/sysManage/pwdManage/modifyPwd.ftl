<#include "/include/head.ftl">
<body>
<div id="panel">
	<form name="pwdForm" id="pwdForm" method="post" class="edit_form">
		<table align="center">
			<tr>
				<td width="30%" align="right">账号：</td>
				<td width="70%">${loginName}</td>
			</tr>
			<tr>
				<td align="right">原始密码：</td>
				<td><input name="oldPwd" id="oldPwd" size="20" class="easyui-passwordbox" data-options="
					required : true,
					missingMessage : '请输入原始密码！'
				" /></td>
			</tr>
			<tr>
				<td align="right">新密码：</td>
				<td><input name="newPwd" id="newPwd" size="20" /></td>
			</tr>
			<tr>
				<td align="right">重复密码：</td>
				<td><input name="newPwd2" id="newPwd2" size="20" /></td>
			</tr>
		</table>
	</form>
</div>
<script>
	$(function() {
	
		$("#pwdForm").form({
			url : "${request.contextPath}/SysUserController/modifyPwd",
			onSubmit : function() {
				if (!$("#pwdForm").form("validate")) {
					return false;
				}
				return true;
			},
			success : function(data) {
				data = JSON.parse(data);
				if (data.code == CODE_SUCCESS) {
					window.top.$.messager.alert("提示", "密码修改成功！", "info");
					$("#pwdForm").form("clear");
				} else {
					window.top.$.messager.alert("失败", data.message, "error");
				}
			}
		});
		$("#pwdForm #newPwd").passwordbox({
			required : true,
			validType : "length[6, 20]",
			missingMessage : '请输入新密码！'
		});
		$("#pwdForm #newPwd2").passwordbox({
			required : true,
			validType : "checkPwd['#pwdForm #newPwd']",
			missingMessage : '请再次输入新密码！'
		});
	
		$("#panel").myDialog({
			width : 400,
			height : 300,
			left : 250,
			top : 50,
			draggable : false,
			title : "修改密码",
			cache : false,
			closable : false,
			buttons : [{
				text : "清空",
				iconCls : "icon-remove",
				handler : function() {
					$("#pwdForm").form("clear");
				}
			}, {
				id : "modifyPwdButton",
				text : "保存",
				iconCls : "icon-save",
				handler : function() {
					$("#pwdForm").submit();
				}
			}]
		});
	});
</script>
</body>
<#include "/include/footer.ftl">