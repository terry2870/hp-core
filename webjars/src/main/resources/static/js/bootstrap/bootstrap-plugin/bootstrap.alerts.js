/**
 * 显示一个告警框
 * 作者：黄平
 * 日期：2016-04-11
 */
(function($) {
	$.fn.alerts = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.alerts.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.alerts.defaults, options);
			self.data("alerts", opt);
			_createAlerts(self, opt);
		});
	};

	function _createAlerts(jq, opt) {
		jq.empty();
		jq.hide();
		jq.addClass("alert alert-"+ opt.type +" alert-dismissable");
		var close = $("<button type='button' class='close'>").html("&times;").appendTo(jq);
		close.click(function() {
			_hide(jq, "slow");
		});
		var span = $("<span role='content'>").appendTo(jq);
		if (opt.showOnCreate === true) {
			_show(jq, opt.content, "slow");
		}
	}
	
	function _getSpan(jq) {
		return jq.find("span[role='content']");
	}
	
	/**
	 * 设置内容
	 */
	function _setContent(jq, content) {
		if (content != undefined) {
			_getSpan(jq).html(content);
		}
	}
	
	/**
	 * 获取内容
	 */
	function _getContent(jq) {
		return _getSpan(jq).html();
	}
	
	/**
	 * 显示
	 */
	function _show(jq, content, speed) {
		var opt = jq.data("alerts");
		if (opt.onBeforeShow.call(jq) === false) {
			return;
		}
		_setContent(jq, content);
		jq.show(speed, function() {
			opt.onAfterShow.call(jq);
		});
	}
	
	/**
	 * 隐藏
	 */
	function _hide(jq, speed) {
		var opt = jq.data("alerts");
		if (opt.onBeforeHide.call(jq) === false) {
			return;
		}
		jq.hide(speed, function() {
			opt.onAfterHide.call(jq);
		});
	}
	
	$.fn.alerts.methods = {
		hide : function(speed) {
			var jq = this;
			return this.each(function() {
				_hide(jq, speed);
			});
		},
		show : function(option) {
			var jq = this;
			return this.each(function() {
				_show(jq, option.content, option.speed);
			});
		}
	};
	
	$.fn.alerts.event = {
		/**
		 * 显示之前执行（当该函数返回false时，则会阻止显示）
		 */
		onBeforeShow : function(){},
		/**
		 * 显示之后执行
		 */
		onAfterShow : function(){},
		/**
		 * 隐藏之前执行（当该函数返回false时，则会阻止隐藏）
		 */
		onBeforeHide : function(){},
		/**
		 * 隐藏之后执行
		 */
		onAfterHide : function(){}
	};
	$.fn.alerts.defaults = $.extend({}, $.fn.alerts.event, {
		content : "",
		showOnCreate : true,
		type : "info"
	});
})(jQuery);