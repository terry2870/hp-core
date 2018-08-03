/**
 * 显示一个输入的表单
 * 作者：黄平
 * 日期：2016-04-18
 * 依赖validate
 */
(function($) {
	$.fn.form = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.form.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.form.defaults, options);
			self.data("form", opt);
			_createForm(self);
		});
	};

	/**
	 * 创建form
	 * @param jq
	 * @returns
	 */
	function _createForm(jq) {
		var opt = jq.data("form");
		jq.addClass("form-horizontal").attr("role", "form");
		if (opt.id) {
			jq.attr("id", opt.id);
		}
		if (opt.name) {
			jq.attr("name", opt.name);
		}
		if (opt.method) {
			jq.attr("method", opt.method);
		}
		
		_loadData(jq);
	}
	
	/**
	 * 提交
	 */
	function _submit(jq, submitParam) {
		var opt = $.extend({}, jq.data("form"), submitParam);
		if (opt.onBeforeSubmit.call(jq) === false) {
			return;
		}
		var frameId = "bootstrap_frame_" + (new Date().getTime());
		var iframe = null;
		if (opt.hasFile === true) {
			//有文件上传提交
			_submitIframe();
			fileSubmit();
			
			/**
			 * 创建iframe
			 */
			function _submitIframe() {
				//如果有文件，则设置form的属性
				jq.attr({
					enctype : "multipart/form-data",
					method : "POST",
					action : opt.submitUrl,
					target : frameId
				});
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
				opt.success.call(jq, opt.dataType === "json" ? $.parseJSON(data) : data);
				setTimeout(function(){
					iframe.unbind();
					iframe.remove();
				}, 100);
			}
			
			/**
			 * 提交
			 */
			function fileSubmit() {
				if (opt.submitUrl){
					jq.attr("action", opt.submitUrl);
				}
				var paramFields = $();
				try {
					//把提交额外的参数放到隐藏域里面提交
					for(var n in opt.submitData) {
						var field = $('<input type="hidden" name="' + n + '">').val(opt.submitData[n]).appendTo(jq);
						paramFields = paramFields.add(field);
					}
					checkState();
					jq.submit();
				} finally {
					//移除target属性
					jq.removeAttr("target");
					//移除隐藏域
					paramFields.remove();
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
		} else {
			//没有文件提交
			var submitData = opt.submitData;
			//把form里面的所有参数序列化
			var formData = jq.serialize();
			if (submitData) {
				if ($.type(submitData) === "object") {
					for (var key in submitData) {
						formData += "&" + key + "=" + encodeURIComponent(submitData[key]);
					}
				} else {
					formData += "&" + encodeURIComponent(submitData);
				}
			}
			$.post(opt.submitUrl, formData, function(data) {
				opt.success.call(jq, data);
			}, opt.dataType);
		}

	}
	
	function _submitIframe() {
		
	}
	
	/**
	 * 加载数据
	 */
	function _loadData(jq) {
		var opt = jq.data("form");
		
		//如果加载前的事件，返回false，则停止加载
		if (opt.onBeforeLoad) {
			if (opt.onBeforeLoad.call(jq, opt.loadParams) === false) {
				return;
			}
		}
		
		var interval = window.setInterval(function() {
			var result = _checkLoadStatus(jq);
			if (result === true) {
				//依赖项全部加载完成，清除延迟
				clearInterval(interval);
				//继续加载
				if (opt.loadUrl) {
					//调用url进行加载
					$.post(opt.loadUrl, opt.loadParams, function(data) {
						//处理返回的数据
						if (opt.loadFilter) {
							data = opt.loadFilter(data);
						}
						_setData(jq, data);
					}, opt.dataType);
				} else {
					_setData(jq, opt.loadData);
				}
			}
		}, 200);
	}
	
	/**
	 * 检查依赖的项，是否加载完成
	 */
	function _checkLoadStatus(jq) {
		var opt = jq.data("form");
		if (!opt.loadDepend || opt.loadDepend.length == 0) {
			return true;
		}
		for (var i = 0; i < opt.loadDepend.length; i++) {
			if ($(opt.loadDepend[i]).attr("loadStatus") != "1") {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 往form里面设置值
	 */
	function _setData(jq, data) {
		if (!data) {
			return;
		}
		for (var key in data) {
			var obj = jq.find("[name='"+ key +"']");
			if (!obj || !obj.get(0)) {
				continue;
			}
			var value = data[key];
			if (obj.get(0).tagName == "INPUT") {
				if (obj.attr("type") == "radio") {
					obj.each(function(index, item) {
						if ($(item).val() == value) {
							$(item).prop("checked", true);
							return false;
						}
					});
				} else if (obj.attr("type") == "checkbox") {
					if (!value) {
						continue;
					}
					var arr = [];
					if ($.type(value) == "string") {
						arr = value.split(",");
					} else {
						arr = value;
					}
					$(arr).each(function(index, item) {
						jq.find("input:checkbox[name='"+ key +"'][value='"+ item +"']").prop("checked", true);
					});
				} else {
					obj.val(value);
				}
			} else {
				obj.val(value);
			}
		}
		var opt = jq.data("form");
		//从远端获取完数据后执行
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, data);
		}
	}
	
	/**
	 * 验证form里面所有数据的有效性
	 */
	function _validate(jq) {
		//获取该form下，所有的需要验证的表单
		var allEl = jq.find("[data-toggle='tooltip']");
		if (!allEl || allEl.length == 0) {
			return true;
		}
		
		//遍历所有的需要验证的控件
		var result = null;
		for (var i = 0; i < allEl.length; i++) {
			result = $(allEl[i]).validate("isValid");
			if (result === false) {
				return false;
			}
		}
		return true;
	}
	
	function _reset(jq) {
		jq.get(0).reset();
	}
	
	$.fn.form.methods = {
		/**
		 * 提交
		 * @param submitParam 该参数可以是form的所有defaults和event的值，将会使用该传递的值提交到后台
		 */
		submit : function(submitParam) {
			var jq = $(this);
			return jq.each(function() {
				_submit(jq, submitParam);
			});
		},
		/**
		 * 验证，并且返回验证结果
		 */
		validate : function() {
			return _validate(this);
		},
		/**
		 * 加载数据
		 */
		load : function(data) {
			var jq = $(this);
			return jq.each(function() {
				_setData(jq, data);
			});
		},
		reset : function() {
			var jq = $(this);
			return jq.each(function() {
				_reset(jq);
			});
		}
	};
	
	$.fn.form.event = {
		onBeforeSubmit : function() {},				//提交前执行，如果返回false，则阻止提交
		success : function(data) {},				//提交完后，的回调函数
		onBeforeLoad : function(loadParams) {},		//加载数据前执行，如果返回false，则停止加载数据
		onLoadSuccess : function(data) {}			//加载完成后执行
	};
	
	$.fn.form.defaults = $.extend({}, $.fn.form.event, {
		loadDepend : [],		//加载前依赖于
		id : "",				//id
		name : "",				//name
		submitUrl : "",			//提交的url
		submitData : {},		//提交时，额外传递的参数
		method : "POST",		//method
		dataType : "json",		//返回的数据类型
		hasFile : false,		//是否包含文件上传
		loadData : {},			//加载的数据
		loadUrl : "",			//加载form时，请求的url
		loadParams : {},		//初始化form时，传递到后台的参数
		loadFilter : function(data) {	//对初始化返回值进行特殊处理
			return data;
		}
	});
	
})(jQuery);