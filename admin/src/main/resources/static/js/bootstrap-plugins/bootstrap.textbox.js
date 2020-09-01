/**
 * 一个文本输入框
 * 作者：黄平
 * 日期：2020-08-03
 * 依赖：validatebox
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "textbox";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method) {
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
		let warp = $("<div>").addClass("textbox clearfix");
		jq.before(warp);
		warp.append(jq);
		
		if (opt.width) {
			warp.css("width", opt.width);
			jq.css("width", opt.width);
		}
		if (opt.height) {
			warp.css("height", opt.height);
			jq.css("height", opt.height);
		}
		if (opt.placeholder) {
			jq.attr("placeholder", opt.placeholder);
		}
		if (opt.value) {
			jq.val(opt.value);
		}
		if (opt.type) {
			jq.attr("type", opt.type);
		}
		jq.prop("readonly", opt.editable === false || opt.readonly === true);
		jq.prop("disabled", opt.disabled);
		if (opt.icons && opt.icons.length > 0) {
			let addonDiv = $("<div>").addClass("float-right");
			for (let i = 0; i < opt.icons.length; i++) {
				let svg = _createSVG("svg");
				let use = _createSVG("use");
				use.setAttributeNS(XLINKNS, "href", opt.contextPath +"/css/bootstrap-icons.svg#"+ opt.icons[i].icon);
				svg.appendChild(use);

				let $svg = $(svg);
				$svg.addClass("textbox-addon");
				$svg.attr("fill", "currentColor");

				addonDiv.append(svg);
				//svg.append("<use xlink:href='"+ opt.contextPath +"/css/bootstrap-icons.svg#"+ opt.icons[i].icon +"' />");
				/*
				let addon = $("<img>").addClass("textbox-addon").appendTo(addonDiv);
				addon.attr("src", opt.contextPath + "/svg/"+ opt.icons[i].icon +".svg");*/
				if (opt.icons[i].onClick) {
					$svg.click(function() {
						opt.icons[i].onClick.call(this);
					});
				}
			}
			jq.before(addonDiv);
		}
		jq.addClass("float-left");
		jq.validatebox(opt);
	}
	
	/**
	 * 创建svg标签
	 * @param {*} tagName 
	 */
	function _createSVG(tagName) {
		return document.createElementNS(NAME_SPACE, tagName);
	}

	//方法
	$.fn[pluginName].methods = $.extend({}, $.fn.validatebox.methods, {
		/**
		 * 获取或设置值
		 * @param {*} value 
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
	
	//事件
	$.fn[pluginName].events = $.extend({}, $.fn.validatebox.events, {

	});
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, $.fn.validatebox.defaults, {
		width : undefined,				//文本框宽度
		height : undefined,				//文本框高度
		placeholder : "请输入内容",		//文本框提示文字
		value : "",						//文本框值
		type : "text",					//文本框类型
		editable : true,				//文本框是否可以编辑
		readonly : false,				//文本框是否只读
		disabled : false,				//文本框是否禁用
		icons : [],						//文本框显示图标()
		contextPath : ""				//contextPath(icon的时候使用)
	});

	/**
	 * icons包含字段
	 * icon		png图标的名称
	 * onClick	点击事件
	 */
})(jQuery);