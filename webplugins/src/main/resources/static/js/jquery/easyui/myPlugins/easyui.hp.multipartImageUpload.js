/**
 * 显示一个可以多选的上传图片插件
 * 作者：黄平
 * 日期：2016-11-25
 */
(function($) {
	$.fn.multipartImageUpload = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.multipartImageUpload.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.multipartImageUpload.defaults, options);
			$(self).data("multipartImageUpload", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		var opt = jq.data("multipartImageUpload");
		var btnDiv = $("<div>").appendTo(jq);
		var contentDiv = $("<div>").appendTo(jq);
		var btn = $("<a>").linkbutton($.extend({}, opt.btnParam, {
			onClick : function() {
				var target = opt.openOnParent === true ? window.top : window.self;
				var div = $("<div>").appendTo($(target.document.body));
				$.fn.multipartImageUpload.tempParam = opt;
				var content = $("<div>").html("1234");
				content.click(function() {
					alert("fsddf");
				});
				
				target.$(div).myDialog($.extend({}, opt.dialogParam, {
					method : "post",
					href : opt.contextPath + "/js/jquery/easyui/myPlugins/easyui.hp.multipartImageUpload.html",
					//content : $("<div>").append(content.clone(true, true)).html(),
					modal : true,
					collapsible : true,
					cache : false,
					buttons : [{
						text : "保存图片",
						iconCls : "icon-save",
						handler : function() {
							window.top.$("#userEditForm").form("submit", {
								url : "/SysUserController/saveSysUser.do",
								onSubmit : function(param) {
									if (!window.top.$("#userEditForm").form("validate")) {
										return false;
									}
									var roleIds = window.top.$("#userEditForm #roleIdTD").checkboxList("getCheckedValue");
									param.roleIds = roleIds.join(",");
									window.top.$.messager.progress({
										title : "正在执行",
										msg : "正在执行，请稍后..."
									});
									return true;
								},
								success : function(data) {
									window.top.$.messager.progress("close");
									data = JSON.parse(data);
									if (data.code == "<%=CodeEnum.SUCCESS.getCode()%>") {
										window.top.$(div).dialog("close");
										$("#userListTable").datagrid("reload");
										window.top.$.messager.show({
											title : "提示",
											msg : title + "成功！"
										});
									} else {
										window.top.$.messager.alert("失败", data.message, "error");
									}
								}
							});
						}
					}, {
						text : "关闭",
						iconCls : "icon-cancel",
						handler : function() {
							window.top.$(div).dialog("close");
						}
					}]
				}));
			}
		}));
		btnDiv.append(btn);
		
	}
	
	$.fn.multipartImageUpload.tempParam = {};
	
	$.fn.multipartImageUpload.methods = {
		
	};
	$.fn.multipartImageUpload.event = {
		
	};
	$.fn.multipartImageUpload.defaults = $.extend({}, $.fn.multipartImageUpload.event, {
		btnParam : {
			text : "批量上传",
			iconCls : "exter-icon exter-multipart-image"
		},									//按钮的属性
		dialogParam : {						//打开的对话框属性
			width : 600,
			height : 450,
			title : "批量上传"
		},
		openOnParent : false,				//是否在父页面打开对话框
		contextPath : "",
		multiple : false,
		accept : "image/*",
		maxSize : 1048576,					//每个文件的最大限制（byte）
		totalSize : 10 * 1048576,			//总共上传文件的最大限制（bytes）
		totalNum : 10,						//最多上传文件个数
		uploadUrl : "",						//上传文件的接口地址
		uploadSuccess : function() {}		//图片上传成功后的回调方法
	});
})(jQuery);