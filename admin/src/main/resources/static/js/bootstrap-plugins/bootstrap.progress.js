/**
 * 一个滚动条
 * 作者：黄平
 * 日期：2020-09-29
 * 继承与：
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "progress";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				throw new Error(pluginName + " 没有此方法。");
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
		if (opt.style) {
			jq.css(opt.style);
		}
		if (opt.className) {
			jq.addClass(opt.className);
		}
		jq.addClass("progress");
		let content = $("<div>").attr({
			role : "progressbar",
			"aria-valuemin" : "0",
			"aria-valuemax" : "100"
		}).addClass("progress-bar").appendTo(jq);
		if (opt.animate === true) {
			content.addClass("progress-bar-striped progress-bar-animated");
		}
		if (opt.value) {
			_setValue(jq, opt.value);
		}

		$.tools.markSuccess(jq, pluginName);
	}

	/**
	 * 获取进度条对象
	 * @param {*} jq 
	 */
	function _getProgressBar(jq) {
		return jq.find("div.progress-bar");
	}

	/**
	 * 获取value
	 * @param {*} jq 
	 */
	function _getValue(jq) {
		return jq.find("div.progress-bar").attr("aria-valuenow");
	}

	/**
	 * 设置进度
	 * @param {*} jq 
	 * @param {*} value 
	 */
	function _setValue(jq, value) {
		let opt = jq.data(pluginName);
		if (opt.onBeforeChange) {
			if (opt.onBeforeChange.call(jq) === false) {
				return;
			}
		}
		let progressBar = _getProgressBar(jq);
		let percentageValue = value + "%";
		progressBar.attr("aria-valuenow", value);
		progressBar.css("width", percentageValue);
		if (opt.textWrap) {
			progressBar.text(opt.textWrap.replace("{value}", percentageValue));
		} else {
			progressBar.text(percentageValue);
		}
		if (opt.onAfterChange) {
			opt.onAfterChange.call(jq);
		}
	}

	/**
	 * 关闭
	 * @param {*} jq 
	 */
	function _close(jq) {
		jq.remove();
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 设置或获取value
		 * @param {*} value 
		 */
		value : function(value) {
			let self = this;
			if (value === undefined) {
				return _getValue(self);
			} else {
				return self.each(function() {
					_setValue(self, value);
				});
			}
		},
		/**
		 * 关闭滚动条
		 */
		close : function() {
			let self = this;
			return self.each(function() {
				_close(self);
			});
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当进度发生改变之前执行
		 * 如果返回false，则不会改变进度
		 */
		onBeforeChange : function() {},
		/**
		 * 当进度发生改变之后执行
		 */
		onAfterChange : function() {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		animate : false,		//是否显示动画
		value : "",				//显示进度百分比（整数）
		textWrap : null,		//显示文字包含
		bgColor : null,			//背景颜色
		style : null,			//样式
		className : null		//class
	});
})(jQuery);