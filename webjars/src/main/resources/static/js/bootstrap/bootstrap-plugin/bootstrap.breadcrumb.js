/**
 * 显示一个路劲导航
 * 作者：黄平
 * 日期：2016-04-13
 */
(function($) {
	$.fn.breadcrumb = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.breadcrumb.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.breadcrumb.defaults, options);
			self.data("breadcrumb", opt);
			_createBreadcrumb.call(self, opt);
		});
	};

	function _createBreadcrumb(opt) {
		var jq = this;
		jq.empty();
		jq.addClass("breadcrumb");
		if (!opt.data || opt.data.length == 0) {
			return;
		}
		var li;
		$(opt.data).each(function(index, item) {
			li = $("<li>").appendTo(jq);
			if (index === opt.data.length - 1) {
				li.addClass("active");
			}
			if (item[opt.hrefField]) {
				if (!item.target) {
					li.append($("<a>").attr({
						href : item[opt.hrefField]
					}).html(item[opt.textField]));
				} else {
					li.append($("<a>").attr({
						href : "#"
					}).click(function() {
						$(item.target).load(item[opt.hrefField]);
					}).html(item[opt.textField]));
				}
			} else {
				li.html(item[opt.textField]);
			}
		});
		
	}
	
	$.fn.breadcrumb.methods = {
		
	};
	
	$.fn.breadcrumb.event = {
		
	};
	$.fn.breadcrumb.defaults = $.extend({}, $.fn.breadcrumb.event, {
		textField : "text",
		hrefField : "href",
		data : []
	});
})(jQuery);