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
			}
		}

		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create(self, opt, random);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} random 
	 */
	function _create(jq, opt, random) {
		
	}

	//方法
	$.fn[pluginName].methods = {

	};
	
	//事件
	$.fn[pluginName].events = {
		
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		
	});
})(jQuery);