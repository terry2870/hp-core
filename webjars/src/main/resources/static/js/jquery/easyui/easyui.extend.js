
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
	 * 			message
	 * 			reloadTableObject
	 * 			callback
	 * @returns
	 */
	confirmDialog : function(obj) {
		window.top.$.messager.confirm("确认", obj.message ? obj.message : "您确定要"+ obj.text +"该数据吗？", function(flag) {
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
						obj.reloadTableObject.datagrid("clearChecked");
					}
					if (obj.callback) {
						obj.callback(data);
					}
					window.top.$.messager.show({
						title : "提示",
						msg : "操作成功！"
					});
				} else {
					window.top.$.messager.alert("失败", data.message, "error");
				}
			});
		});
	},
	/**
	 * 保存对话框保存按钮事件
	 * @param obj
	 * 			dialogObject
	 * 			formObject
	 * 			url
	 * 			onSubmit
	 * 			reloadTableObject
	 * 			callback
	 * @returns
	 */
	saveDialogHandler : function(obj) {
		if (!obj.formObject) {
			return;
		}
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
							obj.reloadTableObject.datagrid("clearChecked");
						}
						
						$.messager.show({
							title : "提示",
							msg : "保存成功！"
						});
					} else {
						window.top.$.messager.alert("出错", data.message, "error");
					}
					if (obj.callback) {
						obj.callback(data);
					}
				} else {
					window.top.$.messager.alert("出错", data.message, "error");
				}
			}
		});
	},
	/**
	 * 打开保存对话框
	 * @param obj
	 * 		title
	 * 		width
	 * 		height
	 * 		href
	 * 		queryParams
	 * 		showSaveBtn = true
	 * 		showCloseBtn = true
	 * 		saveBtn.text = '保存'
	 * 		saveBtn.iconCls = 'icon-save'
	 * 		handler
	 */
	saveDialog : function(obj) {
		var div = $("<div>").appendTo($(window.top.document.body));

		let buttons = obj.buttons || [];
        if (obj.showSaveBtn !== false) {
        	if (!obj.saveBtn) {
        		obj.saveBtn = {};
        	}
            buttons.push({
                text: (obj.saveBtn.text) ? obj.saveBtn.text : "保存",
                iconCls: (obj.saveBtn.iconCls) ? obj.saveBtn.iconCls : "icon-save",
                handler: function () {
                	if (!obj.handler) {
                		return;
                	}
                    obj.handler.dialogObject = window.top.$(div);
                    if (!obj.handler.formObject && obj.handler.formObjectId) {
                        obj.handler.formObject = window.top.$("#" + obj.handler.formObjectId);
                    }
                    $.saveDialogHandler(obj.handler);
                }
            });
		}
		if (obj.showCloseBtn !== false) {
            buttons.push({
                text: "关闭",
                iconCls: "icon-cancel",
                handler: function () {
                    window.top.$(div).dialog("close");
                }
            });
		}

		window.top.$(div).myDialog({
			width: obj.width ? obj.width : '500',
			height: obj.height ? obj.height : '300',
			title: obj.title,
			href: obj.href,
			content : obj.content,
			queryParams: obj.queryParams,
			method: "post",
			modal: true,
			collapsible: true,
			cache: false,
			buttons: buttons,
			onOpen : obj.onOpen || $.noop
		});
		return div;
	}
});