/**
 * 一个带下拉面板的输入框
 * 作者：黄平
 * 日期：2020-09-17
 * 继承与：form
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "form";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
			} else {
				throw new Error(pluginName + " 没有此方法。");
			}
		}

		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create(self, opt);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt
	 */
	function _create(jq, opt) {
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

		jq.loadSuccess();
	}

	/**
	 * 加载数据
	 * @param {*} jq 
	 */
	function _loadData(jq) {
		let opt = jq.data(pluginName);
		
		//如果加载前的事件，返回false，则停止加载
		if (opt.onBeforeLoad) {
			if (opt.onBeforeLoad.call(jq, opt.loadParams) === false) {
				return;
			}
		}
		
		let interval = window.setInterval(function() {
			let result = _checkLoadStatus(jq, opt);
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
	 * 设置值
	 * @param {*} jq 
	 * @param {*} data 
	 */
	function _setData(jq, data) {
		if (!data) {
			return;
		}
		for (let key in data) {
			let obj = jq.find("[name='"+ key +"']");
			if (!obj || !obj.get(0)) {
				continue;
			}
			let value = data[key];
			let tagName = obj.get(0).tagName;
			if (tagName == "INPUT" || tagName == "TEXTAREA") {
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
					let arr = [];
					if ($.type(value) == "string") {
						arr = value.split(",");
					} else {
						arr = value;
					}
					$(arr).each(function(index, item) {
						jq.find("input:checkbox[name='"+ key +"'][value='"+ item +"']").prop("checked", true);
					});
				} else {
					let pluginName = obj.attr(PLUGIN_NAME);
					if (pluginName == "combobox") {
						obj.combobox("values", value);
					} else {
						obj.val(value);
					}
					obj.validatebox("validate");
				}
			} else {
				obj.val(value);
			}
		}
		let opt = jq.data("form");
		//从远端获取完数据后执行
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, data);
		}
	}

	/**
	 * 检查是否已经全部加载完
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _checkLoadStatus(jq, opt) {
		if (!opt.loadDepend || opt.loadDepend.length == 0) {
			return true;
		}
		for (let i = 0; i < opt.loadDepend.length; i++) {
			if ($(opt.loadDepend[i]).loadSuccess("isNotLoadSuccess")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 重置
	 * @param {*} jq 
	 */
	function _reset(jq) {
		jq.get(0).reset();
		_validate(jq);
	}

	/**
	 * 验证整个form内的元素
	 * @param {*} jq 
	 */
	function _validate(jq) {
		//获取该form下，所有的需要验证的表单
		var allEl = jq.find("[validatebox='1']");
		if (!allEl || allEl.length == 0) {
			return true;
		}
		
		//遍历所有的需要验证的控件
		var result = null;
		for (var i = 0; i < allEl.length; i++) {
			result = $(allEl[i]).validatebox("validate");
			if (result === false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 提交form
	 * @param {*} jq 
	 * @param {*} submitParam 
	 */
	function _submit(jq, submitParam) {
		if (!submitParam || !submitParam.url) {
			return;
		}

		let param = {};
		let opt = jq.data(pluginName);
		if (submitParam.onBeforeSubmit) {
			let onBeforeSubmitResult = submitParam.onBeforeSubmit.call(jq, param);
			if (onBeforeSubmitResult === false) {
				return;
			}
		}

		//获取form里面所有参数
		let formData = jq.serialize();
		let formParam = {};
		if (formData) {
			//转换成对象
			let arr = decodeURIComponent(formData).split("&");
			for (let i = 0; i < arr.length; i++) {
				let objArr = arr[i].split("=");
				formParam[objArr[0]] = objArr[1];
			}
		}
		
		$.post(submitParam.url, $.extend({}, formParam, param), function(data) {
			if (submitParam.success) {
				submitParam.success.call(jq, data);
			}
		}, submitParam.dataType || "json");
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 提交
		 * @param submitParam 该参数可以是form的所有defaults和event的值，将会使用该传递的值提交到后台
		 * 包含参数
		 * url 											//提交到后台的url
		 * dataType	: json								//返回数据的格式
		 * onBeforeSubmit : function(param) {}			//提交前执行，如果返回false，则阻止提交。参数param 会作为参数传递到后台
		 * success : function(data) {}					//提交成功后的回调
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
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 加载数据前执行，如果返回false，则停止加载数据
		 * @param {*} loadParams 
		 */
		onBeforeLoad : function(loadParams) {},
		/**
		 * 加载完成后执行
		 * @param {*} data 
		 */
		onLoadSuccess : function(data) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		loadDepend : [],		//加载前依赖于
		id : "",				//id
		name : "",				//name
		loadData : {},			//加载的数据
		loadUrl : "",			//加载form时，请求的url
		loadParams : {},		//初始化form时，传递到后台的参数
		loadFilter : function(data) {	//对初始化返回值进行特殊处理
			return data;
		}
	});
})(jQuery);