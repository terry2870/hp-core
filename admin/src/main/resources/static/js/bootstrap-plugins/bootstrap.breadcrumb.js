/**
 * 一个面包屑导航
 * 作者：黄平
 * 日期：2020-07-28
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "breadcrumb";
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
		jq.attr("aria-label", "breadcrumb");
		let ol = $("<ol>").addClass("breadcrumb").appendTo(jq);
		if (!opt.data || opt.data.length == 0) {
			return;
		}
		$(opt.data).each(function(index, item) {
			let li = $("<li>").addClass("breadcrumb-item").appendTo(ol);
			let a = $("<a>").appendTo(li);
			if (item[opt.hrefField]) {
				a.attr("href", item[opt.hrefField]);
			}
			a.html(item[opt.textField]);
		});
	}

	//方法
	$.fn[pluginName].methods = {

	};
	
	//事件
	$.fn[pluginName].events = {
		
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		textField : "text",
		hrefField : "href",
		data : []
	});
})(jQuery);