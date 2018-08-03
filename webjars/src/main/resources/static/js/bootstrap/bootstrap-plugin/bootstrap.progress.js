/**
 * 显示一个进度条
 * 作者：黄平
 * 日期：2016-04-29
 */
(function($) {
	$.fn.progress = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.progress.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.progress.defaults, options);
			self.data("progress", opt);
			_createProgress(self);
		});
	};

	/**
	 * 生成progress
	 */
	function _createProgress(jq) {
		var opt = jq.data("progress");
		
		
	}
	
	
	
	//方法
	$.fn.progress.methods = {
		
	};
	
	//事件
	$.fn.progress.event = {
		onLoadSuccess : function(data) {}
	};
	
	//属性
	$.fn.progress.defaults = $.extend({}, $.fn.progress.event, {

	});
})(jQuery);