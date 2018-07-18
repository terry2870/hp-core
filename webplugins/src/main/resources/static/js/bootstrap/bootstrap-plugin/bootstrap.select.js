/**
 * 显示一个下拉框
 * 作者：黄平
 * 日期：2016-05-14
 */
(function($) {
	$.fn.select = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.select.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.select.defaults, options);
			self.data("select", opt);
			if (opt.url) {
				$.ajax({
					url : opt.url,
					data : opt.queryParams,
					type : opt.method,
					dataType : opt.dataType ? opt.dataType : "json",
					success : function(data) {
						if (!data) {
							return;
						}
						opt.data = data;
						if (opt.loadFilter) {
							opt.data = opt.loadFilter(data);
						}
						_createSelect(self);
					}
				});
			} else {
				_createSelect(self);
			}
		});
	};

	
	/**
	 * 生成select
	 */
	function _createSelect(jq) {
		var opt = jq.data("select");
		if (opt.style) {
			jq.css(opt.style);
		}
		if (opt.className) {
			jq.addClass(opt.className);
		}
		/*if (!opt.data) {
			return;
		}*/
		if (opt.onChange) {
			jq.change(function() {
				var optionData = jq.find("option[value='"+ jq.val() +"']").data("optionData");
				opt.onChange.call(jq, jq.val(), optionData);
			});
		}
		if (opt.firstText !== undefined || opt.firstValue !== undefined) {
			$("<option>").val(opt.firstValue).html(opt.firstText).appendTo(jq);
		}
		$(opt.data).each(function(index, item) {
			$("<option "+ ((opt.value && opt.value == item[opt.valueField]) ? "selected" : "") +">").data("optionData", item).val(item[opt.valueField]).html(item[opt.textField]).appendTo(jq);
		});
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
		
		//设置已经加载完成
		jq.attr("loadStatus", "1");
	}
	
	function _getValue(jq) {
		return $(jq).val();
	}
	
	function _getText(jq) {
		var obj = $(jq).find("option:selected");
		return obj ? obj.html() : "";
	}
	
	//方法
	$.fn.select.methods = $.extend({}, $.fn.validate.methods, {
		/**
		 * 获取值
		 */
		getValue : function() {
			return _getValue(this);
		},
		/**
		 * 获取文本
		 */
		getText : function() {
			return _getText(this);
		}
	});
	
	//事件
	$.fn.select.event = $.extend({}, $.fn.validate.event, {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {
			
		},
		/**
		 * 当下拉框改变的时候执行
		 * @param value
		 * @param optionData
		 */
		onChange : function(value, optionData) {}
	});
	
	//属性
	$.fn.select.defaults = $.extend({}, $.fn.select.event, $.fn.validate.defaults, {
		firstText : undefined,
		firstValue : undefined,
		style : null,
		className : "",
		valueField : "value",
		textField : "text",
		url : undefined,
		dataType : "json",
		type : "POST",
		queryParams : {},
		data : [],
		loadFilter : function(data) {},
		value : null
	});
	
	/**
		其中data包含的属性有
		
	*/
})(jQuery);