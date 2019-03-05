/**
 * 显示一个日期选择框
 * 继承与easyui的datebox
 * 作者：黄平
 * 日期：2016-11-21
 */
(function($) {
	$.parser.plugins.push("myDatebox");
	$.fn.myDatebox = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myDatebox.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.datebox(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myDatebox.defaults, options);
			self.data("myDatebox", opt);
			_createDatebox(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _createDatebox(jq) {
		var opt = jq.data("myDatebox");
		var btns = $.extend([], $.fn.datebox.defaults.buttons);
		btns.splice(1, 0, {
			text : "清除",
			handler : function(target) {
				jq.datebox("clear");
			}
		});
		jq.datebox($.extend({}, {
			buttons : btns
		}, opt));
	}
	
	$.fn.myDatebox.methods = {
		
	};
	
	$.fn.myDatebox.event = {
		
	};
	
	$.fn.myDatebox.defaults = $.extend({}, $.fn.myDatebox.event, {
		editable : false
	});
})(jQuery);