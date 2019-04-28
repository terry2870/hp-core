/**
 * 显示一个日期选择框
 * 继承与easyui的datetimebox
 * 作者：黄平
 * 日期：2016-11-21
 */
(function($) {
	$.parser.plugins.push("myDatetimebox");
	$.fn.myDatetimebox = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myDatetimebox.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.datetimebox(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myDatetimebox.defaults, $.fn.datetimebox.parseOptions(this), options);
			self.data("myDatetimebox", opt);
			_createDatetimebox(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _createDatetimebox(jq) {
		var opt = jq.data("myDatetimebox");
		var btns = $.extend([], $.fn.datetimebox.defaults.buttons);
		btns.splice(1, 0, {
			text : "清除",
			handler : function(target) {
				jq.datetimebox("clear");
			}
		});
		jq.datetimebox($.extend({}, {
			width : 200,
			buttons : btns,
			icons : [{
				iconCls : "icon-clear",
				handler : function(e) {
					$(e.data.target).textbox("clear");
				}
			}],
		}, opt));
	}
	
	$.fn.myDatetimebox.methods = {
		
	};
	
	$.fn.myDatetimebox.event = {
		
	};
	
	$.fn.myDatetimebox.defaults = $.extend({}, $.fn.myDatetimebox.event, {
		editable : false
	});
})(jQuery);