/**
 * 使各种输入框带有验证功能
 * 作者：黄平
 * 日期：2020-07-29
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "validatebox";
	let enableValidation = "enableValidation";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
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
		jq.tooltip();

		//先验证一遍
		_validate(jq);

		//失去焦点时
		jq.blur(function() {
			_validate(jq);
		});

		//按下任何按键也验证
		jq.keyup(function() {
			_validate(jq);
		});
	}

	/**
	 * 显示错误信息
	 * @param {*} jq 
	 * @param {*} message 
	 */
	function _showError(jq, message) {
		let opt = jq.data(pluginName);
		//jq.removeClass("is-valid");
		jq.addClass("border-danger");
		jq.tooltip("dispose");
		jq.tooltip({
			title : message,
			placement : opt.placement,
			trigger : "manual"
		});
		jq.tooltip("show");
	}

	/**
	 * 清除
	 * @param {*} jq 
	 */
	function _clear(jq) {
		jq.removeClass("border-danger");
		//jq.addClass("is-valid");
		jq.tooltip("dispose");
	}

	/**
	 * 验证
	 * @param {*} jq 
	 */
	function _validate(jq) {
		let opt = jq.data(pluginName);
		let textValue = jq.val();
		//开始验证时，触发
		if (opt.onBeforeValidate) {
			opt.onBeforeValidate.call(jq, textValue);
		}
		
		let validateResult = _getValidateResult(jq, textValue);
		if (validateResult.result !== true) {
			_showError(jq, validateResult.message);
		} else {
			_clear(jq);
		}

		//验证完成时，触发
		if (opt.onAfterValidate) {
			opt.onAfterValidate.call(jq, textValue, validateResult);
		}

		_addEvent(jq);
	}

	/**
	 * 获取验证结果
	 * @param {*} jq 
	 * @param {*} textValue 
	 */
	function _getValidateResult(jq, textValue) {
		let opt = jq.data(pluginName);
		let result = undefined;
		let message = "";
		if (jq.attr(enableValidation) == "false") {
			//已经禁用验证
			return {
				result : true
			};
		}
		//输入框为空时
		if (opt.required === true && textValue == "") {
			return {
				result : false,
				message : opt.missingMessage
			};
		}

		if (!opt.validType) {
			return {
				result : true
			};
		}
		
		//开始验证
		if ($.type(opt.validType) === "string") {
			//string类型
			result = _validataString(textValue, opt.validType);
		} else if ($.type(opt.validType) === "array") {
			//array类型
			for (let i = 0; i < opt.validType.length; i++) {
				result = _validataString(textValue, opt.validType[i]);
				if (result !== true) {
					break;
				}
			}
		} else if ($.type(opt.validType) === "function") {
			//function类型
			result = opt.validType(textValue);
		}
		return _toResult(result, opt);
	}

	/**
	 * 转成结果
	 * @param {*} result 
	 * @param {*} opt
	 */
	function _toResult(result, opt) {
		let message = "";
		let re = undefined;
		let checkResult = _checkResult(result);
		if (!checkResult) {
			//验证结果未通过，则显示提示
			message = $.type(result) === "string" ? result : opt.invalidMessage;
		}
		re = checkResult;
		return {
			result : re,
			message : message
		};
	}

	/**
	 * string或array类型的验证
	 * @param {*} textValue 
	 * @param {*} validType 
	 */
	function _validataString(textValue, validType) {
		var param = "";
		if (validType.indexOf("[") > 0) {
			param = validType.substring(validType.indexOf("["));
			validType = validType.substring(0, validType.indexOf("["));
		}
		return $.fn[pluginName].defaults.rules[validType](textValue, eval(param));
	}

	/**
	 * 返回值为true或者未有数据返回时，都认为验证通过
	 * @param {*} result 
	 */
	function _checkResult(result) {
		return result === true || result === undefined;
	}

	/**
	 * 添加事件
	 * @param {*} jq 
	 */
	function _addEvent(jq) {
		var opt = jq.data(pluginName);
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
	
	//方法
	$.fn[pluginName].methods = {
		/**
		 * 开启验证
		 */
		enableValidation : function() {
			let self = this;
			return this.each(function() {
				$(self).attr(enableValidation, "true");
			});
		},
		/**
		 * 关闭验证
		 */
		disableValidation : function() {
			let self = this;
			return this.each(function() {
				$(self).attr(enableValidation, "false");
			});
		},
		/**
		 * 验证
		 */
		validate : function() {
			let self = this;
			return this.each(function() {
				_validate(self);
			});
		},
		/**
		 * 获取验证结果
		 */
		getValidateResult : function() {
			return _getValidateResult($(this), $(this).val()).result;
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当验证之前触发
		 * @param {*} textValue 
		 */
		onBeforeValidate : function(textValue) {},
		/**
		 * 当验证之后触发
		 * @param {*} textValue 
		 * @param {*} result 
		 */
		onAfterValidate : function(textValue, result) {},
		/**
		 * 当显示之前触发
		 */
		onBeforeShow : function() {},
		/**
		 * 当显示之后触发
		 */
		onAfterShow : function() {},
		/**
		 * 当隐藏之前
		 */
		onBeforeHide : function() {},
		/**
		 * 当隐藏之后
		 */
		onAfterHide : function() {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
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
		placement : "right"						//提示框的位置（'left','right','top','bottom'）
	});

	/**
	 * 验证框的，验证方法
	 * 返回为true或者undefined，则表示验证通过；其余都是失败
	 * 返回字符串，则该值会显示在错误提示里面
	 */
	$.fn[pluginName].defaults.rules = {
		
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
			let result = $.fn[pluginName].defaults.rules.minlength(value, [param[0]]);
			if (!_checkResult(result)) {
				return result;
			}
			result = $.fn[pluginName].defaults.rules.maxlength(value, [param[1]]);
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