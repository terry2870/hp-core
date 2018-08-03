/**
 * 显示一个面板
 * 作者：黄平
 * 日期：2018-07-05
 */
(function($) {
	$.fn.panel = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.panel.methods[options];
			if (method) {
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.panel.defaults, $.fn.panel.event, options);
			self.data("panel", opt);
			if (opt.closeAble === true) {
				self.find("a.close").click(function() {
					_destory(self, opt);
				});
			}
		});
	};
	
	//获取标题
	function _getTitle(jq) {
		return jq.find(".card-title:first");
	}
	
	//获取头部
	function _getHead(jq) {
		return jq.find("> .card-header:first");
	}
	
	//获取主体
	function _getBody(jq) {
		return jq.find("> .card-body:first");
	}
	
	//获取脚部
	function _getFooter(jq) {
		return jq.find("> .card-footer:first");
	}
	
	/**
	 * 销毁整个panel
	 */
	function _destory(jq, option) {
		var onClose = option.onClose.call(jq, option);
		if (onClose === false) {
			return;
		}
		$(jq).remove();
	}
	
	/**
	 * 设置或获取title
	 */
	function _title(jq, title) {
		var obj = _getTitle(jq);
		if (title === undefined) {
			return obj.html();
		}
		if ($.type(title) === "object") {
			obj.empty().append(title);
		} else {
			obj.html(title);
		}
	}
	
	//方法
	$.fn.panel.methods = {
		/**
		 * 设置标题
		 */
		setTitle : function(title) {
			var jq = this;
			return this.each(function() {
				_title(jq, title);
			});
		},
		/**
		 * 获取标题
		 */
		getTitle : function() {
			var jq = this;
			return _title(jq);
		}
	};
	
	//事件
	$.fn.panel.event = {
		/**
		 * 当panel加载完成时执行
		 */
		onLoadSuccess : function(data) {},
		/**
		 * 关闭之前执行
		 * 如果返回false，则阻止关闭
		 */
		onClose : function(option) {}
	};
	
	//属性
	$.fn.panel.defaults = {
		
	};
})(jQuery);
