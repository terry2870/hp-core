 /**
 * 给一个输入框加上验证信息
 * 作者：黄平
 * 日期：2016-04-18
 */
(function($) {
	$.fn.validate = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.validate.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.validate.defaults, options);
			self.data("validate", opt);
			_createValidate(self);
		});
	};
	
	/**
	 * 创建验证信息
	 */
	function _createValidate(jq) {
		var opt = jq.data("validate");
		var textValue = jq.val();
		jq.attr({
			"data-toggle" : "tooltip",
			"data-placement" : opt.tipPosition,
			"data-trigger" : "manual",
			enableValidation : "true"
		});
		
		//开始验证
		_validataOnEvent(jq);
	}
	
	/**
	 * 根据事件，来验证
	 */
	function _validataOnEvent(jq) {
		var opt = jq.data("validate");
		var textValue = jq.val();
		
		//text失去焦点时验证
		if (opt.validateOnBlur === true) {
			jq.blur(function() {
				window.setTimeout(function() {
					jq.validate("validate");
				}, 300);
				//_validateText(jq);
			});
		}
		
		//获取焦点时，取消弹出信息
		jq.focus(function() {
			_setDivStyle(jq, true);
			jq.tooltip("hide");
		});
	}
	
	/**
	 * 销毁
	 */
	function _destory(jq) {
		jq.tooltip("destroy");
	}
	
	/**
	 * 验证信息
	 */
	function _validateText(jq) {
		var opt = jq.data("validate");
		var textValue = jq.val();
		
		//开始验证时，触发
		if (opt.onBeforeValidate) {
			opt.onBeforeValidate.call(jq, textValue);
		}
		
		var message = "", result = undefined;
		if (jq.attr("enableValidation") == "false") {
			result = true;
		} else {
			
			//输入框为空时
			if (opt.required === true && textValue == "") {
				message = opt.missingMessage;
				result = false;
			} else {
				
				if (opt.validType) {
					//开始验证
					if ($.type(opt.validType) === "string") {
						//string类型
						result = _validataString(textValue, opt.validType);
					} else if ($.type(opt.validType) === "array") {
						//array类型
						for (var i = 0; i < opt.validType.length; i++) {
							result = _validataString(textValue, opt.validType[i]);
							if (result !== true) {
								break;
							}
						}
					} else if ($.type(opt.validType) === "function") {
						//function类型
						result = opt.validType(textValue);
					}
					if (!_checkResult(result)) {
						//验证结果未通过，则显示提示
						message = $.type(result) === "string" ? result : opt.invalidMessage;
					}
				}
			}
			_addEvent(jq);
		}
		
		if (!_checkResult(result)) {
			_destory(jq);
			//bt的bug，需要延迟一段时间再显示，不然会有问题
			window.setTimeout(function() {
				jq.tooltip({
					title : message,
					delay : {
						show : 500,
						hide : 500
					}
				});
				jq.tooltip("show");
			}, 500);
			//jq.focus();
		} else {
			jq.tooltip("hide");
		}
		
		//当验证完成后，设置外面框的样式
		_setDivStyle(jq, result);
		
		//验证完成时，触发
		if (opt.onAfterValidate) {
			opt.onAfterValidate.call(jq, textValue, result);
		}
		return _checkResult(result);
	}
	
	/**
	 * 返回值为true或者未有数据返回时，都认为验证通过
	 */
	function _checkResult(result) {
		return result === true || result === undefined;
	}
	
	/**
	 * 当验证失败时，设置外面框的样式
	 */
	function _setDivStyle(jq, result) {
		var groupDiv = jq.parent();
		if (!groupDiv) {
			return;
		}
		/*groupDiv = groupDiv.parent();
		if (!groupDiv) {
			return;
		}*/
		if (_checkResult(result)) {
			groupDiv.removeClass("has-error");
		} else {
			groupDiv.addClass("has-error");
		}
	}
	
	/**
	 * string或array类型的验证
	 */
	function _validataString(textValue, validType) {
		var param = "";
		if (validType.indexOf("[") > 0) {
			param = validType.substring(validType.indexOf("["));
			validType = validType.substring(0, validType.indexOf("["));
		}
		return $.fn.validate.defaults.rules[validType](textValue, eval(param));
	}
	
	/**
	 * 添加事件
	 */
	function _addEvent(jq) {
		var opt = jq.data("validate");
		jq.on("show.bs.tooltip", function(e) {
			if (opt.onBeforeShow) {
				opt.onBeforeShow.call(jq);
			}
		});
		jq.on("shown.bs.tooltip", function(e) {
			if (opt.onAfterShow) {
				opt.onAfterShow.call(jq);
			}
		});
		jq.on("hide.bs.tooltip", function(e) {
			if (opt.onBeforeHide) {
				opt.onBeforeHide.call(jq);
			}
		});
		jq.on("hidden.bs.tooltip", function(e) {
			if (opt.onAfterHide) {
				opt.onAfterHide.call(jq);
			}
		});
	}
	
	$.fn.validate.methods = {
		/**
		 * 验证输入框
		 */
		validate : function() {
			return this.each(function() {
				_validateText($(this));
			});
		},
		/**
		 * 返回验证结果
		 */
		isValid : function() {
			return _validateText($(this));
		},
		/**
		 * 开启验证
		 */
		enableValidation : function() {
			$(this).attr("enableValidation", "true");
		},
		/**
		 * 关闭验证
		 */
		disableValidation : function() {
			$(this).attr("enableValidation", "false");
		}
	};
	
	//事件
	$.fn.validate.event = {
		onBeforeShow : function() {},
		onAfterShow : function() {},
		onBeforeHide : function() {},
		onAfterHide : function() {},
		onBeforeValidate : function(textValue) {},
		onAfterValidate : function(textValue, result) {}
	};
	
	$.fn.validate.defaults = $.extend({}, $.fn.validate.event, {
		required : false,				//是否必填
		/**
		 * 使用默认或自定义的验证方法
		 * string|array|function
		 * string	指定一个默认或自定义的验证方法
		 * array	指定多个默认或者自定义验证方法
		 * function	传入验证框的内容，返回true，则验证通过，返回string，则验证不通过，并且把该string作为提示消息
		 */
		validType : null,				
		missingMessage : "该值为必填项",			//该值为空时并且required=true的提示
		invalidMessage : "",					//验证失败时，并且验证的函数只返回false的时候显示
		tipPosition : "right",					//提示框的位置（'left','right','top','bottom'）
		validateOnBlur : false					//是否失去焦点时验证
	});
	
	/**
	 * 验证框的，验证方法
	 * 返回为true或者undefined，则表示验证通过；其余都是失败
	 * 返回字符串，则该值会显示在错误提示里面
	 */
	$.fn.validate.defaults.rules = {
		
		/**
		 * 最小长度
		 */
		minlength : function(value, param) {
			if (value.length < param[0]) {
				return "长度必须大于" + param[0];
			}
		},
		/**
		 * 最大长度
		 */
		maxlength : function(value, param) {
			if (value.length > param[0]) {
				return "长度超过了" + param[0];
			}
		},
		/**
		 * 设置最大和最小长度
		 */
		length : function(value, param) {
			var result = $.fn.validate.defaults.rules.minlength(value, [param[0]]);
			if (!_checkResult(result)) {
				return result;
			}
			result = $.fn.validate.defaults.rules.maxlength(value, [param[1]]);
			if (!_checkResult(result)) {
				return result;
			}
		},
		/**
		 * 验证url
		 */
		url : function(value, param) {
			if (value == "") {
				return true;
			}
			var reg = /^http[s]{0,1}:\/\/.+$/;
			if (!reg.test(value)) {
				return "url格式不正确";
			}
		}
	};
})(jQuery);