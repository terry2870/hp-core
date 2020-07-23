/**
 * 一个面板插件
 * 作者：黄平
 * 日期：2020-05-28
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "card";
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
		jq.addClass("card");
		if (opt.className) {
			jq.addClass(opt.className);
		}
		if (opt.cardStyle) {
			jq.addClass("border-" + opt.cardStyle);
		}
		if (opt.width) {
			jq.css("width", opt.width);
		}
		if (opt.height) {
			jq.css("height", opt.height);
		}
		if (opt.left) {
			jq.css("left", opt.left);
		}
		if (opt.top) {
			jq.css("top", opt.top);
		}
		

		//创建header
		_createHeader(jq, opt);

		//创建body
		_createBody(jq, opt);

		//创建footer
		_createFooter(jq, opt);
	}

	/**
	 * 创建header头部
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createHeader(jq, opt) {
		if (!opt.header) {
			return;
		}
		let header = $("<div>").addClass("card-header").appendTo(jq);
		if (opt.cardStyle) {
			header.addClass("bg-" + opt.cardStyle);
		}

		if (opt.closeable === true) {
			let closeBtn = $("<button>").appendTo(header);
			closeBtn.attr({
				type : "button",
				"aria-label" : "关闭"
			}).addClass("close").append($("<span>").attr("aria-hidden", true).html("&times;"));
			closeBtn.click(function() {
				_close(jq, "slide");
			});
		}
		_createContent(jq, header, opt.header);
	}

	/**
	 * 创建header头部
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createBody(jq, opt) {
		if (!opt.body) {
			return;
		}
		let body = $("<div>").addClass("card-body").appendTo(jq);
		_createContent(jq, body, opt.body);
	}

	/**
	 * 创建footer
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createFooter(jq, opt) {
		if (!opt.footer) {
			return;
		}
		let footer = $("<div>").addClass("card-footer").appendTo(jq);
		_createContent(jq, footer, opt.footer);
	}

	/**
	 * 创建内容
	 * @param {*} jq 
	 * @param {*} target 
	 * @param {*} params 
	 */
	function _createContent(jq, target, params) {
		let opt = jq.data(pluginName);
		if (!params) {
			return;
		}

		if (params.style) {
			for (let key in params.style) {
				target.css(key, params.style[key]);
			}
		}

		if (params.className) {
			target.addClass(params.className);
		}

		if (params.url) {
			//从远端获取数据
			$.post(params.url, params.data, function(loadData) {
				target.params(loadData);
				if (target.hasClass("card-body")) {
					opt.onLoadSuccess.call(jq, loadData);
				}
			}, "json");
		} else if (params.html) {
			target.append(params.html);
		} else if (params.selector) {
			target.append(params.selector);
		}
	}

	/**
	 * 关闭
	 * @param {*} jq 
	 */
	function _close(jq, effect) {
		let opt = jq.data(pluginName);
		if (opt.onbeforeClode) {
			let re = opt.onbeforeClode.call(jq);
			if (re === false) {
				return;
			}
		}

		if (!effect) {
			effect = "show";
		}
		if (effect === "show") {
			jq.hide();
		} else if (effect === "slide") {
			jq.slideUp("normal");
		} else if (effect === "fade") {
			jq.fadeOut("normal");
		}

		if (opt.onAfterClose) {
			opt.onAfterClose.call(jq);
		}
	}

	/**
	 * 显示
	 * @param {*} jq 
	 */
	function _open(jq, effect) {
		if (!effect) {
			effect = "show";
		}
		if (effect === "show") {
			jq.show();
		} else if (effect === "slide") {
			jq.slideDown("normal");
		} else if (effect === "fade") {
			jq.fadeIn("normal");
		}
	}

	/**
	 * 获取header
	 * @param {*} jq 
	 */
	function _getHeader(jq) {
		return jq.find(">div.card-header");
	}

	/**
	 * 获取body
	 * @param {*} jq 
	 */
	function _getBody(jq) {
		return jq.find(">div.card-body");
	}

	/**
	 * 获取footer
	 * @param {*} jq 
	 */
	function _getFooter(jq) {
		return jq.find(">div.card-footer");
	}

	/**
	 * 销毁
	 * @param {*} jq 
	 */
	function _destroy(jq) {
		let opt = jq.data(pluginName);
		if (opt.onbeforeDestroy) {
			let re = opt.onbeforeDestroy.call(jq);
			if (re === false) {
				return;
			}
		}

		jq.remove();
		if (opt.onAfterDestroy) {
			opt.onAfterDestroy.call(jq);
		}
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 关闭
		 * @param {*} effect 
		 */
		close : function(effect) {
			let jq = this;
			return this.each(function() {
				_close(jq, effect);
			});
		},
		/**
		 * 销毁
		 */
		destroy : function() {
			let jq = this;
			return this.each(function() {
				_destroy(jq);
			});
		},
		/**
		 * 设置或获取body
		 * @param {*} value 
		 */
		body : function(value) {
			let self = this;
			if (value === undefined) {
				return _getBody(self);
			} else {
				return self.each(function() {
					_getBody(self).empty().append(value);
				});
			}
		},
		/**
		 * 设置或获取footer
		 * @param {*} value 
		 */
		footer : function(value) {
			let self = this;
			if (value === undefined) {
				return _getFooter(self);
			} else {
				return self.each(function() {
					_getFooter(self).empty().append(value);
				});
			}
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当关闭之前执行
		 * 如果返回false，则阻止关闭
		 */
		onbeforeClode : function() {},
		/**
		 * 当关闭之后执行
		 */
		onAfterClose : function() {},
		/**
		 * 当销毁之前执行
		 * 如果返回false，则阻止销毁
		 */
		onbeforeDestroy : function() {},
		/**
		 * 当销毁之后执行
		 */
		onAfterDestroy : function() {},
		/**
		 * body内容加载成功后执行
		 * @param {*} data 
		 */
		onLoadSuccess : function(data) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		left : null,
		top : null,
		width : "200",					//宽
		height : "200",					//高
		cardStyle : $.bootstrapClass.DEFAULT,					//面板的样式
		className : null,				//面板的class
		header : null,
		body : null,
		footer : null,
		closeable : true
	});

	/**
	 * 其中 header,body, footer包含属性
	 * 	style
	 *  className
	 *  url
	 * 	data
	 *  html
	 *  selector
	 * 
	 * 
	 * 
	 * 
	 */
})(jQuery);