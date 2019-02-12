/**
 * 上传文件
 * 作者：黄平
 * 日期：2016-08-23
 */
(function($) {
	$.fn.fileUpload = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.fileUpload.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.fileUpload.defaults, options);
			$(self).data("fileUpload", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		jq = $(jq);
		var opt = jq.data("fileUpload");
		jq.addClass("file-upload").css({
			width : opt.width,
			height : opt.height
		});
		if (!opt.value) {
			_createForm(jq);
		} else {
			_setFile(jq, opt.value);
		}
	}
	
	
	function _getForm(jq) {
		return jq.find("form");
	}
	
	/**
	 * 提交
	 */
	function _submit(jq) {
		var opt = jq.data("fileUpload");
		if (opt.onBeforeSubmit) {
			let result = opt.onBeforeSubmit.call(jq, _getValue(jq));
			if (result === false) {
				return;
			}
		}
		$.fn.fileUpload.event.onBeforeSubmit.call(jq);
		
		
		var frameId = "jquery_frame_" + (new Date().getTime());
		var iframe = null;
		var form = _getForm(jq);
		form.attr("target", frameId);

		//有文件上传提交
		_submitIframe();
		fileSubmit();
		
		/**
		 * 创建iframe
		 */
		function _submitIframe() {
		
			//创建一个隐藏iframe
			iframe = jq.find("#" + frameId);
			
			if (!iframe || iframe.length == 0) {
				iframe = $("<iframe>").attr({
					id : frameId,
					name : frameId
				}).hide().appendTo(jq);
			}
			iframe.bind("load", cb);
		}
		
		var checkCount = 10;
		/**
		 * 回调
		 */
		function cb() {
			if (!iframe.length) {
				return;
			}
			iframe.unbind();
			var data = "";
			try{
				var body = iframe.contents().find("body");
				data = body.html();
				if (data == ""){
					if (--checkCount){
						//最多重试10次
						setTimeout(cb, 100);
						return;
					}
				}
				var ta = body.find(">textarea");
				if (ta.length) {
					data = ta.val();
				} else {
					var pre = body.find(">pre");
					if (pre.length) {
						data = pre.html();
					}
				}
			} catch(e) {
			}
			data = JSON.parse(data);
			jq.data("successData", data);
			if (data.code != 200) {
				window.top.$.messager.alert("失败", data.message, "error");
				_hideProgress();
				return;
			}
			var fileName = opt.filterFileName(data);
			if (opt.showFileName === true) {
				if (fileName) {
					_setFile(jq, fileName);
				}
			}
			
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, fileName, data);
			}
			$.fn.fileUpload.event.onLoadSuccess(fileName, data);
			
			setTimeout(function(){
				iframe.unbind();
				iframe.remove();
			}, 100);
		}
		
		/**
		 * 提交
		 */
		function fileSubmit() {
			var paramFields = $();
			try {
				//把提交额外的参数放到隐藏域里面提交
				for(var n in opt.queryParams) {
					var field = $('<input type="hidden" name="' + n + '">').val(opt.queryParams[n]).appendTo(form);
					paramFields = paramFields.add(field);
				}
				checkState();
				form.submit();
			} finally {
			}
		}
		
		/**
		 * 检查iframe是否已经成功创建
		 */
		function checkState(){
			var f = $("#" + frameId);
			if (!f.length) {
				return;
			}
			try {
				var s = f.contents()[0].readyState;
				if (s && s.toLowerCase() == 'uninitialized'){
					setTimeout(checkState, 100);
				}
			} catch(e){
				cb();
			}
		}
	}
	
	/**
	 * 初始化
	 * @param jq
	 * @returns
	 */
	function _init(jq) {
		jq.empty();
		_create();
	}
	
	/**
	 * 设置文件
	 * @param jq
	 * @returns
	 */
	function _setFile(jq, fileName) {
		jq.empty();
		var opt = jq.data("fileUpload");
		jq.append($("<input type='hidden' />").attr({
			name : opt.realInputName
		}).val(fileName));
		var textSpan = $("<span>").html(fileName.substring(fileName.lastIndexOf("/") + 1));
		if (opt.onClickFile) {
			textSpan.click(function() {
				opt.onClickFile.call(jq, fileName);
			});
		}
		var closeSpan = $("<span>").click(function() {
			_closeFile(jq);
		}).addClass("file-close");
		jq.append(textSpan).append(closeSpan)
	}
	
	function _getValue(jq) {
		var obj = jq.find("input[type='file']");
		if (!obj || obj.length == 0) {
			obj = jq.find("input[type='hidden']");
		}
		return obj.val();
	}
	
	/**
	 * 创建上传form
	 * @param jq
	 * @returns
	 */
	function _createForm(jq) {
		jq.empty();
		var opt = jq.data("fileUpload");
		var form = $("<form>").attr({
			enctype : "multipart/form-data",
			method : "POST",
			action : opt.url
		}).appendTo(jq);
		form.append($("<input type='file'>").attr({
			accept : opt.accept ? opt.accept : null,
			name : opt.uploadInputName,
			id : opt.name
		}).change(function() {
			var value = $(this).val();
			if (value == "") {
				return;
			}
			_submit(jq);
		})).append($("<span>").html(opt.text));
	}
	
	/**
	 * 删除文件
	 * @param jq
	 * @returns
	 */
	function _closeFile(jq) {
		var opt = jq.data("fileUpload");
		_createForm(jq);
	}
	
	function _showProgress() {
		window.top.$.messager.progress({
			title : "正在执行",
			msg : "正在执行，请稍后..."
		});
	}
	
	function _hideProgress() {
		window.top.$.messager.progress("close");
	}
	
	$.fn.fileUpload.methods = {
		setValue : function(value) {
			var jq = $(this);
			if (!value) {
				return jq;
			}
			return jq.each(function() {
				_setFile(jq, value);
			});
		},
		getValue : function() {
			return _getValue(this);
		}
	};
	$.fn.fileUpload.event = {
		/**
		 * 提交文件之前触发，如果该函数返回false，则阻止提交
		 */	
		onBeforeSubmit : function(value) {
			_showProgress();
		},
		/**
		 * 提交后，服务端有返回后触发
		 */
		onLoadSuccess : function(fileName, data) {
			_hideProgress();
			if (data.code != 200) {
				window.top.$.messager.alert("失败", "上传失败", "error");
				return;
			}
			window.top.$.messager.show({
				title : "提示",
				msg : "上传成功！"
			});
		},
		/**
		 * 当点击文件时
		 */
		onClickFile : function(fileName, data) {}
	};
	$.fn.fileUpload.defaults = $.extend({}, {
		text : "请选择文件",					//按钮文字
		uploadInputName : "file",			//上传文件的控件名称
		realInputName : "",					//真实的文件字段名称
		url : null,							//提交到后端的url
		queryParams : {},					//提交到后端额外参数
		dataType : "json",					//返回数据的格式
		value : null,						//文件默认值
		filterFileName : function(data) {	//返回值中，获取文件名
			if (!data || data.code != 200) {
				return null;
			}
			return data.data ? data.data.fileName : null;
		},
		showFileName : true,					//上传成功后，是否显示文件名
		accept : null
	});
})(jQuery);