<form name="sysUserEditForm" id="sysUserEditForm" method="post" class="edit_form">
	<input type="hidden" name="id" id="id" value="${id}" />
	<table align="center">
		<tr>
			<td width="30%" align="right">登录名：</td>
			<td width="70%">
				<input type="text" name="loginName" id="loginName" class="easyui-textbox" data-options="
					required : true,
					prompt : '登录名',
					validType : 'checkLoginName',
					missingMessage : '登录名不能为空'
				" />
			</td>
		</tr>
		<tr id="passwdTr">
			<td align="right">密码：</td>
			<td><input name="loginPwd" id="loginPwd" /></td>
		</tr>
		<tr id="passwd2Tr">
			<td align="right">重复新密码：</td>
			<td><input name="loginPwd2" id="loginPwd2" /></td>
		</tr>
		<tr>
			<td align="right">用户名称：</td>
			<td>
				<input type="text" name="userName" id="userName" class="easyui-textbox" data-options="
					required : true,
					prompt : '用户名称'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">手机号码：</td>
			<td>
				<input type="text" name="mobile" id="mobile" class="easyui-textbox" data-options="
					prompt : '手机号码',
					validType : 'checkMobile'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">固定电话：</td>
			<td>
				<input type="text" name="phoneNumber" id="phoneNumber" class="easyui-textbox" data-options="
					prompt : '固定电话',
					validType : 'checkPhoneNum'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">email：</td>
			<td>
				<input type="text" name="email" id="email" class="easyui-textbox" data-options="
					prompt : 'email',
					validType : 'email'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">用户地址：</td>
			<td>
				<input type="text" name="address" id="address" class="easyui-textbox" data-options="
					prompt : '用户地址'
				" />
			</td>
		</tr>
		<tr>
			<td align="right">角色：</td>
			<td><input id="roleId" /></td>
		</tr>
		<tr>
			<td align="right">用户身份：</td>
			<td>
				<input name="identity" id="identity" />
			</td>
		</tr>
		<tr>
			<td align="right">用户状态：</td>
			<td>
				<input name="status" id="status" />
			</td>
		</tr>
	</table>
</form>
<script>
	$(function() {
		let id = ${id};
		//身份
		$("#sysUserEditForm #identity").combobox({
			url : "${request.contextPath}/NoFilterController",
			queryParams : {
				method : 'getEnumForSelect',
				className : 'IdentityEnum'
			},
			panelHeight : 150,
			prompt : '用户身份',
			editable : false,
			required : true,
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			}
		});
		
		//角色
		$("#sysUserEditForm #roleId").tagbox({
			valueField : "id",
			textField : "roleName",
			limitToList : true,
			hasDownArrow : true,
			url : "${request.contextPath}/SysRoleController/queryAllSysRole",
			queryParams : {
				rows : 0,
				status : 1
			},
			loadFilter : function(data) {
				if (!data || data.code != 200) {
					return [];
				}
				return data.data.rows;
			},
			onLoadSuccess : function() {
				let self = this;
				$.post("${request.contextPath}/SysUserController/selectRoleByUserId", {
					userId : id
				}, function(data) {
					if (!data || data.code != 200 || !data.data || data.data.length == 0) {
						return;
					}
					var arr = [];
					$(data.data).each(function(index, item) {
						arr.push(item);
					});
					
					$(self).tagbox("setValues", arr);
				});
			}
		});
		
		//状态
		$("#sysUserEditForm #status").combobox({
			url : "${request.contextPath}/NoFilterController",
			queryParams : {
				method : 'getEnumForSelect',
				className : 'com.hp.core.common.enums.StatusEnum'
			},
			prompt : '状态',
			panelHeight : 100,
			editable : false,
			required : true,
			loadFilter : function(data) {
				return defaultLoadFilter(data);
			}
		});
		
		//密码框
		if (id == "0") {
			$("#sysUserEditForm #loginPwd").passwordbox({
				required : true,
				prompt : 'password',
				validType : "length[6,20]",
				missingMessage : "密码不能为空!"
			});
			$("#sysUserEditForm #loginPwd2").passwordbox({
				required : true,
				prompt : '请再次输入密码',
				validType : "checkPwd['#sysUserEditForm #loginPwd']"
			});
		} else {
			$("#sysUserEditForm #loginName").textbox("readonly");
			$("#sysUserEditForm #passwdTr,#passwd2Tr").hide();
		}
		
		//初始化页面
		$.post("${request.contextPath}/SysUserController/querySysUserById", {
			id : id
		}, function(data) {
			$("#sysUserEditForm").form("load", data.data);
		});
	});
</script>

