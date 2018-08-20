
/**
 * 为easyui 创建一些快捷使用的方法
 */
jQuery.extend({
	/**
	 * 确认操作提示框
	 * @param obj
	 * 			url
	 * 			params
	 * 			text
	 * 			reloadTableSelector
	 * 			callback
	 * @returns
	 */
	confirmDialog : function(obj) {
		window.top.$.messager.confirm("确认", "您确定要"+ obj.text +"该数据吗？", function(flag) {
			if (!flag) {
				return;
			}
			window.top.$.messager.progress({
				title : "正在执行",
				msg : "正在执行，请稍后..."
			});
			$.post(obj.url, obj.params || {}, function(data) {
				window.top.$.messager.progress("close");
				if (data.code == CODE_SUCCESS) {
					if (obj.reloadTableObject) {
						obj.reloadTableObject.datagrid("reload");
					}
					if (obj.callback) {
						obj.callback(data);
					}
					window.top.$.messager.show({
						title : "提示",
						msg : obj.text + "成功！"
					});
				} else {
					window.top.$.messager.alert("失败", data.message, "error");
				}
			});
		});
	},
	/**
	 * 保存对话框
	 * @param obj
	 * 			dialogObject
	 * 			formObject
	 * 			url
	 * 			onSubmit
	 * 			reloadTableObject
	 * 			callback
	 * @returns
	 */
	saveDialog : function(obj) {
		obj.formObject.form("submit", {
			url : obj.url,
			onSubmit : function(param) {
				if (!obj.formObject.form("validate")) {
					return false;
				}
				
				if (obj.onSubmit) {
					let re = obj.onSubmit(param);
					if (re === false) {
						return false;
					}
				}
				
				window.top.$.messager.progress({
					title : "正在执行",
					msg : "正在执行，请稍后..."
				});
				return true;
			},
			success : function(data) {
				window.top.$.messager.progress("close");
				if (data) {
					data = JSON.parse(data);
					if (data.code == CODE_SUCCESS) {
						if (obj.dialogObject) {
							obj.dialogObject.dialog("close");
						}
						if (obj.reloadTableObject) {
							obj.reloadTableObject.datagrid("reload");
						}
						if (obj.callback) {
							obj.callback(data);
						}
						$.messager.show({
							title : "提示",
							msg : "保存成功！"
						});
					} else {
						window.top.$.messager.alert("出错", data.message, "error");
					}
				} else {
					window.top.$.messager.alert("出错", data.message, "error");
				}
			}
		});
	}
});