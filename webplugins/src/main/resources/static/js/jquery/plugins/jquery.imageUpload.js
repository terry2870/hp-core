/**
 * 显示一个可以预览的上传图片
 * 作者：黄平
 * 日期：2016-08-20
 */
(function($) {
	$.fn.imageUpload = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.imageUpload.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.imageUpload.defaults, options);
			$(self).data("imageUpload", opt)
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
		var opt = jq.data("imageUpload");
		jq.addClass("image-upload").css({
			width : opt.width,
			height : opt.height
		});
		
		if (!opt.value) {
			_createForm(jq);
		} else {
			_setImage(jq, opt.value);
		}
		if (opt.onCreate) {
			opt.onCreate.call(jq);
		}
	}
	
	function _getForm(jq) {
		return jq.find("form");
	}
	
	/**
	 * 提交
	 */
	function _submit(jq) {
		var opt = jq.data("imageUpload");
		if (opt.onBeforeSubmit.call(jq, _getValue(jq)) === false) {
			return;
		}
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
			
			var fileName = data.data ? data.data.fileName : null;
			if (opt.filterFileName) {
				fileName = opt.filterFileName.call(jq, data);
			}
			if (fileName) {
				_setImage(jq, fileName);
			}

			opt.onLoadSuccess.call(jq, data);
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
	 * 删除文件
	 * @param jq
	 * @returns
	 */
	function _closeFile(jq) {
		var opt = jq.data("imageUpload");
		_createForm(jq);
	}
	
	/**
	 * 设置图片
	 * @param jq
	 * @returns
	 */
	function _setImage(jq, fileName) {
		jq.empty();
		var opt = jq.data("imageUpload");
		
		var imageDiv = $("<div role='image'>").addClass("image-upload-image").css({
			width : "100%",
			height : opt.text ? "80%" : "100%"
		}).appendTo(jq);
		imageDiv.append($("<img />").attr({
			src : fileName,
			width : "100%",
			height : "100%"
		})).append($("<input type='hidden'>").attr({
			name : opt.realInputName
		}).val(fileName));
		if (opt.readonly !== true) {
			imageDiv.append($("<span>").click(function() {
				_closeFile(jq);
			}).addClass("file-close"));
		}
		
		
		
		if (opt.text) {
			var textDiv = $("<div role='text'>").css({
				width : "100%",
				height : "20%"
			}).appendTo(jq);
			if ($.type(opt.text) == "object") {
				textDiv.append(opt.text);
			} else {
				textDiv.html(opt.text);
			}
		}
	}
	
	function _showImage(jq, src) {
		_getImage(jq).attr("src", src);
	}
	
	function _getImage(jq) {
		return jq.find("div[role='image'] img");
	}
	
	function _getText(jq) {
		return jq.find("div[role='text']");
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
		var opt = jq.data("imageUpload");
		var imageDiv = $("<div role='image'>").addClass("image-upload-image").css({
			width : "100%",
			height : opt.text ? "80%" : "100%"
		}).appendTo(jq);
		
		if (opt.text) {
			var textDiv = $("<div role='text'>").css({
				width : "100%",
				height : "20%"
			}).appendTo(jq);
			if ($.type(opt.text) == "object") {
				textDiv.append(opt.text);
			} else {
				textDiv.html(opt.text);
			}
		}
		var form = $("<form>").attr({
			enctype : "multipart/form-data",
			method : "POST",
			action : opt.url
		}).appendTo(imageDiv);
		form.append($("<a>").css({
			"line-height" : opt.text ? (opt.height * 0.8) + "px" : opt.height + "px"
		}).html("+"));
		form.append($("<input type='"+ (opt.readonly === true ? "text" : "file") +"'>").attr({
			name : opt.uploadInputName,
			id : opt.uploadInputName
		}).change(function() {
			var value = $(this).val();
			if (value == "") {
				return;
			}
			_submit(jq);
		}));
		
	}
	
	$.fn.imageUpload.methods = {
		setValue : function(value) {
			var jq = $(this);
			if (!value) {
				return jq;
			}
			return jq.each(function() {
				_setImage(jq, value);
			});
		},
		getValue : function() {
			return _getValue(this);
		},
		showImage : function(src) {
			var jq = $(this);
			if (!src) {
				return jq;
			}
			return jq.each(function() {
				_showImage(jq, src);
			});
		}
	};
	$.fn.imageUpload.event = {
		onBeforeSubmit : function(value) {},
		onLoadSuccess : function(data) {},
		onCreate : function() {}
	};
	$.fn.imageUpload.defaults = $.extend({}, $.fn.imageUpload.event, {
		width : 120,						//控件宽度
		height : 150,						//控件高度
		readonly : false,					//是否禁用
		uploadInputName : "file",			//上传文件的控件名称
		realInputName : "",					//真实的文件字段名称
		url : null,							//提交到后台的url
		queryParams : {},					//提交到后台的额外参数
		dataType : "json",					//返回数据的格式
		value : null,						//默认值
		filterFileName : function(data) {	//返回值中，获取文件名
			return data;
		},
		accept : null,
		text : "",							//图片底端提示文字
		textHeight : "20%"					//图片底端提示文本的高度
	});
})(jQuery);