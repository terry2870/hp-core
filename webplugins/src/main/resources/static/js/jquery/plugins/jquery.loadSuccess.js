/**
 * 设置插件加载完成
 * 作者：黄平
 * 日期：2016-08-13
 */
(function($) {
	$.fn.loadSuccess = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.loadSuccess.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.loadSuccess.defaults, options);
			$(self).data("loadSuccess", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		_setLoadSuccess(jq);
	}
	
	/**
	 * 设置加载完成
	 * @param jq
	 * @returns
	 */
	function _setLoadSuccess(jq) {
		jq.attr("load-status", "1");
	}
	
	/**
	 * 获取是否加载完成
	 * @param jq
	 * @returns
	 */
	function _isLoadSuccess(jq) {
		return jq.attr("load-status") == 1;
	}
	
	
	$.fn.loadSuccess.methods = {
		isLoadSuccess : function() {
			return _isLoadSuccess(this);
		},
		isNotLoadSuccess : function() {
			return !_isLoadSuccess(this);
		}
	};
	$.fn.loadSuccess.event = {
		
	};
	$.fn.loadSuccess.defaults = $.extend({}, $.fn.loadSuccess.event, {
		
	});
})(jQuery);