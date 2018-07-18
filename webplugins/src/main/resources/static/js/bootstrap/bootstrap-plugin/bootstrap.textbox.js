/**
 * 显示一个文本框
 * 作者：黄平
 * 日期：2016-04-26
 * 继承validate
 */
(function($) {
	$.fn.textbox = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.textbox.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.textbox.defaults, options);
			self.data("textbox", opt);
			_createTextbox(self);
		});
	};

	/**
	 * 创建text
	 */
	function _createTextbox(jq) {
		var opt = jq.data("textbox");
		
		jq.addClass("form-control");
		jq.css("border-radius", opt["border-radius"]);
		if (opt.placeholder) {
			jq.attr("placeholder", opt.placeholder);
		}
		if (opt.type) {
			jq.attr("type", opt.type);
		}
		if (opt.width) {
			jq.css("width", opt.width);
		}
		if (opt.height) {
			jq.css("height", opt.height);
		}
		jq.prop("readonly", opt.editable === false || opt.readonly === true);
		jq.prop("disabled", opt.disabled);
		if (opt.value) {
			jq.val(opt.value);
		}
		//内部的图标
		var span;
		if (opt.iconCls) {
			span = $("<span>").addClass(opt.iconCls).addClass("glyphicon form-control-feedback").attr("aria-hidden", true);
		}
		if (opt.wrap === true) {
			//创建一个div包一下
			var div = $("<div>").css("width", "100%");
			div.addClass("input-group has-feedback");
			jq.before(div);
			div.append(jq).append(span);
		} else {
			jq.parent().addClass("has-feedback");
			jq.after(span);
		}
		
		//继承validate
		jq.validate(opt);
	}
	
	/**
	 * 方法继承$.fn.validate.methods
	 */
	$.fn.textbox.methods = $.extend({}, $.fn.validate.methods, {
		/**
		 * 设置或获取输入框的只读状态
		 * @param value
		 */
		readonly : function(value) {
			if (value === undefined) {
				return $(this).prop("readonly");
			} else {
				return $(this).each(function() {
					$(this).readonly(value);
				});
			}
		},
		/**
		 * 设置或获取值
		 * @param value
		 */
		value : function(value) {
			if (value === undefined) {
				return $(this).val();
			} else {
				return $(this).each(function() {
					$(this).val(value);
				});
			}
		}
	});
	
	/**
	 * 事件继承$.fn.validate.event
	 */
	$.fn.textbox.event = $.extend({}, $.fn.validate.event, {
		
	});
	
	/**
	 * 属性继承$.fn.validate.defaults
	 */
	$.fn.textbox.defaults = $.extend({}, $.fn.textbox.event, $.fn.validate.defaults, {
		"border-radius" : "5px",		//输入框的圆角
		width : undefined,				//文本框宽度
		height : undefined,				//文本框高度
		placeholder : "请输入内容",		//文本框提示文字
		value : "",						//文本框值
		type : "text",					//文本框类型
		editable : true,				//文本框是否可以编辑
		readonly : false,				//文本框是否只读
		disabled : false,				//文本框是否禁用
		iconCls : "",					//文本框显示图标
		wrap : false					//是否使用div包一下
	});
})(jQuery);