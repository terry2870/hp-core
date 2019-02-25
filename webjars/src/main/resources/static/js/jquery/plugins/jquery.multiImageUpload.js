/**
 * 显示一个可以预览的上传多图片
 * 作者：黄平
 * 日期：2017-06-07
 */
(function($) {
	$.fn.multiImageUpload = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.multiImageUpload.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.multiImageUpload.defaults, options);
			$(self).data("multiImageUpload", opt)
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
		var opt = jq.data("multiImageUpload");
		jq.addClass("multi_image_upload").css({
			width : opt.pluginWidth,
			height : opt.pluginHeight
		});
		
		_createForm(jq);
		
		if (opt.value) {
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
		var opt = jq.data("multiImageUpload");
		if (opt.onBeforeSubmit) {
			let result = opt.onBeforeSubmit.call(jq, _getValue(jq));
			if (result === false) {
				return;
			}
		}
		$.fn.multiImageUpload.event.onBeforeSubmit.call(jq);
		
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
			if (data.code != 200) {
				window.top.$.messager.alert("失败", data.message, "error");
				_hideProgress();
				return;
			}
			
			var fileArr = data.data || [];
			if (opt.filterFile) {
				fileArr = opt.filterFile.call(jq, data);
			}
			if (fileArr) {
				_setImage(jq, fileArr);
			}

			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, data);
			}
			$.fn.multiImageUpload.event.onLoadSuccess.call(jq, data);
			
			setTimeout(function(){
				iframe.unbind();
				iframe.remove();
			}, 100);
		}
		
		/**
		 * 提交
		 */
		function fileSubmit() {
			var hiddenField = [];
			try {
				//把提交额外的参数放到隐藏域里面提交
				for(var n in opt.queryParams) {
					var field = $('<input type="hidden" name="' + n + '">').val(opt.queryParams[n]).appendTo(form);
					hiddenField.push(field);
				}
				checkState();
				form.submit();
			} finally {
				$(hiddenField).each(function() {
					$(this).remove();
				});
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
	function _closeFile(obj, jq) {
		//var opt = jq.data("multiImageUpload");
		//_createForm(jq);
		$(obj).parent().parent().remove();
		_checkMaxFileSize(jq);
	}
	
	/**
	 * 设置图片
	 * @param jq
	 * @returns
	 */
	function _setImage(jq, fileArr) {
		var opt = jq.data("multiImageUpload");
		if ($.type(fileArr) !== "array") {
			//如果传过来不是数组，则变为数组
			fileArr = [fileArr];
		}
		
		var uploadForm = _getUploadForm(jq);
		__showImage(0);
		
		//延迟显示图片
		function __showImage(index) {
			if (index == fileArr.length) {
				return;
			}
			window.setTimeout(function() {
				_drawImage(jq, uploadForm, fileArr[index]);
				__showImage(index + 1);
				_checkMaxFileSize(jq);
			}, opt.showImageInterval);
		}
		
		/*$(fileArr).each(function(index, item) {
			_drawImage(jq, uploadForm, item);
		});*/
	}
	
	/**
	 * 设置是否支持拖动
	 * @param jq
	 * @returns
	 */
	function _setDraggable(jq, li) {
		var opt = jq.data("multiImageUpload");
		if (!opt.draggable) {
			return;
		}
		
		li.draggable({
			proxy : "clone",
			revert : true,
			delay : 500,
			handle : li.selector,
			onStartDrag : function() {
				$(this).draggable("proxy").removeClass("multi_image_upload_image multi_image_upload_proxy_enter");
				$(this).draggable("proxy").addClass("multi_image_upload_proxy");
				//$(this).draggable("proxy").get(0).offsetTop = $(this).draggable("proxy").get(0).offsetTop - div.parent().scrollTop();
			},
			onStopDrag : function() {
				//$(this).draggable("options").cursor = "auto";
			},
			onDrag : function(e) {
				var d = e.data;
				if (d.left < 0) {
					d.left = 0;
				}
				if (d.top < 0) {
					d.top = 0;
				}
				/* d.top = d.top - $(d.parent).scrollTop();
				if (d.left + $(d.target).outerWidth() >= $(d.parent).outerWidth()) {
					d.left = $(d.parent).outerWidth() - $(d.target).outerWidth();
				} */
			}
		});
		li.droppable({
			accept : li.selector,
			onDragEnter : function(e, source) {
				$(source).draggable("proxy").removeClass("multi_image_upload_image multi_image_upload_proxy");
				$(source).draggable("proxy").addClass("multi_image_upload_proxy_enter");
				$(this).addClass("multi_image_upload_target_enter");
			},
			onDragLeave : function(e, source) {
				$(source).draggable("proxy").removeClass("multi_image_upload_proxy_enter");
				$(source).draggable("proxy").addClass("multi_image_upload_image multi_image_upload_proxy");
				$(this).removeClass("multi_image_upload_target_enter").addClass("multi_image_upload_image");
			},
			onDrop : function(e, source) {
				$(source).draggable("proxy").removeClass("multi_image_upload_proxy multi_image_upload_proxy_enter");
				$(this).removeClass("multi_image_upload_target_enter").addClass("multi_image_upload_image");
				var tmpSource = $(source).clone();
				tmpSource.insertBefore($(source));
				$(source).insertBefore($(this));
				$(this).detach().insertBefore(tmpSource);
				tmpSource.remove();
			}
		});
	}
	
	function _drawImage(jq, uploadForm, fileItem) {
		
		if (!_checkMaxFileSize(jq)) {
			return;
		}
		
		var opt = jq.data("multiImageUpload");
		var imageLi = $("<li role='image'>").addClass("multi_image_upload_image").css({
			width : opt.width,
			//height : opt.text ? "80%" : "100%"
			height : opt.height
		}).insertBefore(uploadForm);
		var imageDiv = $("<div role='image'>").addClass("img_div").appendTo(imageLi);
		imageDiv.append($("<img />").attr({
			src : fileItem.fileName || fileItem.url,
			width : "100%",
			height : "100%"
		})).append($("<input type='hidden'>").attr({
			name : opt.realInputName,
			width : fileItem.width || "",
			height : fileItem.height || ""
		}).val(fileItem.fileName || fileItem.url));
		if (opt.readonly !== true) {
			imageDiv.append($("<span>").click(function(event) {
				event.stopPropagation();
				_closeFile(this, jq);
			}).addClass("file_close"));
		}
		
		_setDraggable(jq, imageLi);
	}
	
	/**
	 * 生成底部文字
	 */
	function createText(jq, opt, imageLi) {
		if (!opt.text) {
			return;
		}
		
		let textDiv = $("<div role='text'>").css({
			width : "100%",
			height : opt.textHeight,
			display : "flex"
		}).appendTo(imageLi);
		if ($.type(opt.text) == "function") {
			let txt = opt.text(jq, opt);
			textDiv.append(txt);
		} else if ($.type(opt.text) == "object") {
			textDiv.append(opt.text);
		} else {
			textDiv.html(opt.text);
		}
	}
	
	function _getText(jq) {
		return jq.find("div[role='text']");
	}

	function _checkMaxFileSize(jq) {
		var images = _getImage(jq);
		var opt = jq.data("multiImageUpload");
		if (images && images.length >= opt.maxFileNum) {
			_getUploadForm(jq).hide();
			return false;
		} else {
			_getUploadForm(jq).show();
			return true;
		}
	}
	
	function _getImage(jq) {
		return jq.find("li[role='image'] img");
	}

	
	function _getValue(jq) {
		var obj = jq.find("input[type='hidden']");
		if (!obj || obj.length == 0) {
			return [];
		}
		var arr = [];
		$(obj).each(function(index, item) {
			arr.push({
				url : $(this).val(),
				//fileName : $(this).val(),
				width : $(this).attr("width"),
				height : $(this).attr("height")
			});
		});
		return arr;
	}
	
	function _getUL(jq) {
		return jq.find("ul.filelist");
	}
	
	function _getUploadForm(jq) {
		return jq.find("li[role='uploadForm']");
	}
	
	/**
	 * 创建上传form
	 * @param jq
	 * @returns
	 */
	function _createForm(jq) {
		var opt = jq.data("multiImageUpload");
		var ul = $("<ul>").addClass("filelist").appendTo(jq);
		
		var imageLi = $("<li role='uploadForm'>").addClass("multi_image_upload_image").css({
			width : opt.width,
			//height : opt.text ? "80%" : "100%"
			height : opt.height
		}).appendTo(ul);
		
		var imageDiv = $("<div role='image'>").addClass("img_div").appendTo(imageLi);
		
		var form = $("<form>").attr({
			enctype : "multipart/form-data",
			method : "POST",
			action : opt.url
		}).appendTo(imageDiv);
		form.append($("<a>").css({
			"line-height" : opt.text ? (opt.height * 0.8) + "px" : opt.height + "px"
		}).html("+"));
		form.append($("<input type='"+ (opt.readonly === true ? "text" : "file") +"' multiple='multiple'>").attr({
			name : opt.uploadInputName,
			id : opt.uploadInputName
		}).change(function() {
			var value = $(this).val();
			if (value == "") {
				return;
			}
			if (opt.maxFileNum !== 0) {
				if (this.files.length > opt.maxFileNum) {
					alert("超过文件数量限制");
					return;
				}
				//获取已经有的图片数量
				var images = _getImage(jq);
				if (images && (images.length + this.files.length) > opt.maxFileNum) {
					alert("超过文件数量限制");
					return;
				}
			}
			
			_submit(jq);
		}));
		
		
		createText(jq, opt, ul);
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
	
	$.fn.multiImageUpload.methods = {
		setValue : function(values) {
			var jq = $(this);
			if (!values) {
				return jq;
			}
			return jq.each(function() {
				_setImage(jq, values);
			});
		},
		getValue : function() {
			return _getValue(this);
		}
	};
	$.fn.multiImageUpload.event = {
		onBeforeSubmit : function(value) {
			_showProgress();
		},
		onLoadSuccess : function(data) {
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
		onCreate : function() {}
	};
	$.fn.multiImageUpload.defaults = $.extend({}, {
		width : 120,						//图片宽度
		height : 150,						//图片高度
		pluginHeight : 300,					//控件高度
		pluginWidth : "100%",				//控件宽度
		maxFileNum : 10,					//最多文件数量
		readonly : false,					//是否禁用
		multiple : true,					//是否可以多选
		uploadInputName : "file",			//上传文件的控件名称
		realInputName : "",					//真实的文件字段名称
		url : null,							//提交到后台的url
		queryParams : {},					//提交到后台的额外参数
		dataType : "json",					//返回数据的格式
		value : null,						//默认值
		showImageInterval : 100,			//每张显示图片间隔
		filterFile : function(data) {		//返回值中，获取文件名
			return data;
		},
		accept : "image/gif,image/jpeg,image/jpg,image/png",
		text : "",							//图片底端提示文字
		textHeight : "20%",					//图片底端提示文本的高度
		draggable : false					//是否支持拖动
	});
})(jQuery);