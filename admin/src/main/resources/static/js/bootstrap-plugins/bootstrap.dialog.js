/**
 * 一个模态对话框
 * 作者：黄平
 * 日期：2020-09-24
 * 继承与：
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "dialog";
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
		jq.addClass("modal fade").attr({
			"data-keyboard" : false,
			"tabindex" : -1,
			role : "dialog",
			"aria-hidden" : true
		}).css({
			"z-index" : 9000
		});

		let dialog = $("<div>").addClass("modal-dialog modal-dialog-scrollable").appendTo(jq);
		if (opt.center === true) {
			dialog.addClass("modal-dialog-centered");
		}
		let content = $("<div>").css({
			//"min-height" : "250px"
		}).addClass("modal-content").appendTo(dialog);
		
		if (opt.size) {
			dialog.addClass("modal-" + opt.size);
		}

		if (opt.style) {
			content.css($.extend({}, opt.style));
		}

		//header
		_createHeader(jq, opt, content);

		//body
		_createBody(jq, opt, content);

		//footer
		_createFooter(jq, opt, content);
		
		//显示对话框
		jq.modal({
			backdrop : "static",
			keyboard : true,
			focus : true
		});
		
		if (opt.onAfterOpen) {
			jq.on("shown.bs.modal", function(e) {
				opt.onAfterOpen.call(jq);
			});
		}

	}

	/**
	 * 创造header
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} content 
	 */
	function _createHeader(jq, opt, content) {
		let header = $("<div>").css({
			padding : "0.5rem 1rem",
			"background-color" : "rgba(0,0,0,.03)",
			"border-bottom" : "1px solid rgba(0,0,0,.125)"
		}).addClass("modal-header").appendTo(content);
		$("<h5>").addClass("modal-title").appendTo(header).html(opt.title);
		if (opt.closeAble === true) {
			let closeBtn = $("<button>").appendTo(header);
			closeBtn.attr({
				type : "button",
				"aria-label" : "关闭",
				"data-dismiss" : "modal"
			}).addClass("close").append($("<span>").attr("aria-hidden", true).html("&times;"));
			closeBtn.click(function() {
				_destory(jq);
			});
		}
	}

	/**
	 * 创建主内容
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} content 
	 */
	function _createBody(jq, opt, content) {
		let body = $("<div>").addClass("modal-body").appendTo(content);
		if (opt.url) {
			//从远端获取数据
			$.post(opt.url, opt.queryParams, function(loadData) {
				body.append(loadData);
				if (opt.onLoadSuccess) {
					opt.onLoadSuccess.call(jq, loadData);
				}
			}).then(function() {
				$.tools.markSuccess(jq, pluginName);
			});
		} else if (opt.content) {
			body.append(opt.content);
			$.tools.markSuccess(jq, pluginName);
		}
	}

	/**
	 * 创建footer
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} content 
	 */
	function _createFooter(jq, opt, content) {
		if (!opt.buttons || opt.buttons.length == 0) {
			return;
		}
		let footer = $("<div>").css({
			padding : ".1rem",
			"background-color" : "rgba(0,0,0,.03)",
			"border-top" : "1px solid rgba(0,0,0,.125)"
		}).addClass("modal-footer").appendTo(content);
		if (opt.footerStyle) {
			footer.css(opt.footerStyle);
		}
		$(opt.buttons).each(function(index, item) {
			let btn = $("<button>").addClass("btn").attr({
				type : "button"
			}).appendTo(footer);
			if (item.id) {
				btn.attr("id", item.id);
			}
			if (item.className) {
				btn.addClass("btn-" + item.className);
			}
			if (item.disabled === true) {
				btn.attr("disabled", true);
			}
			btn.html(item.text);
			if (item.onClick) {
				btn.click(function() {
					item.onClick.call(this);
				});
			}
		});
	}

	/**
	 * 销毁
	 * @param {*} jq 
	 */
	function _destory(jq) {
		let opt = jq.data(pluginName);
		if (opt.onBeforeClose) {
			opt.onBeforeClose.call(jq);
		}
		jq.next(".modal-backdrop").remove();
		jq.remove();
		if (opt.onAfterClose) {
			opt.onAfterClose.call(jq);
		}
		//jq.modal("dispose");
	}

	/**
	 * 获取header
	 * @param {*} jq 
	 */
	function _getHeader(jq) {
		return jq.find("div.modal-header");
	}

	/**
	 * 获取title
	 * @param {*} jq 
	 */
	function _getTitle(jq) {
		return _getHeader(jq).find("h5.modal-title");
	}

	/**
	 * 获取body
	 * @param {*} jq 
	 */
	function _getBody(jq) {
		return jq.find("div.modal-body");
	}

	/**
	 * 获取footer
	 * @param {*} jq 
	 */
	function _getFooter(jq) {
		return jq.find("div.modal-footer");
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 关闭。就是销毁
		 */
		close : function() {
			let self = this;
			return this.each(function() {
				_destory(self);
			});
		},
		/**
		 * 设置或获取title
		 * @param {*} title 
		 */
		title : function(title) {
			if (title === undefined) {
				return _getTitle(jq).html();
			} else {
				let self = this;
				return this.each(function() {
					_getTitle(self).html(title);
				});
			}
		},
		/**
		 * 设置或获取内容
		 * @param {*} content 
		 */
		content : function(content) {
			if (content === undefined) {
				return _getBody(jq).html();
			} else {
				let self = this;
				return this.each(function() {
					_getBody(self).html(content);
				});
			}
		},
		/**
		 * 获取footer对象
		 */
		getFooter : function() {
			return _getFooter(this);
		},
		/**
		 * 销毁
		 */
		destory : function() {
			let self = this;
			return this.each(function() {
				_destory(self);
			});
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 显示之后
		 */
		onAfterOpen : function() {},
		/**
		 * 关闭之前
		 */
		onBeforeClose : function() {},
		/**
		 * 关闭之后
		 */
		onAfterClose : function() {},
		/**
		 * 从远端加载完成时
		 * @param {*} data 
		 */
		onLoadSuccess : function(data) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		size : null,			//对话框大小（xl；lg；sm）
		style : null,			//对话框样式
		closeAble : true,		//是否可以关闭
		title : "",				//标题
		content : undefined,	//内容
		center : false,			//是否水平居中
		url : "",				//从远端加载内容
		queryParams : {},		//从远端加载内容传递的
		/**
		 * 每个按钮包含属性
		 * className	样式
		 * text			内容
		 * onClick		点击事件
		 * disabled		是否禁用
		 * id
		 */
		buttons : [],			//按钮,
		footerStyle : null		//footer的样式
	});
})(jQuery);