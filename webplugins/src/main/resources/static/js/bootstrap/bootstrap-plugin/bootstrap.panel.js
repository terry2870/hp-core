/**
 * 显示一个面板
 * 作者：黄平
 * 日期：2016-04-19
 */
(function($) {
	$.fn.panel = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.panel.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.panel.defaults, options);
			self.data("panel", opt);
			_createPanel.call(self);
		});
	};

	/**
	 * 生成panel
	 */
	function _createPanel() {
		var jq = this;
		var opt = jq.data("panel");
		jq.empty();
		jq.addClass("panel");
		if (opt.className) {
			if (opt.className.indexOf("-") > 0) {
				jq.addClass(opt.className);
			} else {
				jq.addClass("panel-" + opt.className);
			}
		}
		if (opt.style) {
			jq.css(opt.style);
		}
		
		$("<div>").addClass("panel-heading").appendTo(jq);
		$("<div>").addClass("panel-body").appendTo(jq);
		$("<div>").addClass("panel-footer").appendTo(jq);
		
		
		//如果有标题，则生成head
		_createHead(jq);
		
		//body部分
		_createBody(jq);
		
		//foot部分
		_createFoot(jq);
		
		//设置已经加载完成
		$(jq).loadSuccess();
	}
	
	/**
	 * 生成head
	 * @param jq
	 * @returns
	 */
	function _createHead(jq) {
		var opt = jq.data("panel");
		var head = _getHead(jq);
		if (!opt.title) {
			head.hide();
		}
		if (opt.headClassName) {
			head.addClass(opt.headClassName);
		}
		if (opt.headStyle) {
			head.css(opt.headStyle);
		}
		var h = $("<h>").addClass("panel-title").appendTo(head);
		if (opt.closeAble === true) {
			var close = $("<a>").addClass("close").append("&times;").appendTo(h);
			close.click(function() {
				_destory(jq, opt);
			});
		}
		h.append($("<apan>").append(opt.title));
	}
	
	/**
	 * 生成body
	 */
	function _createBody(jq) {
		var opt = jq.data("panel");
		var body = _getBody(jq);
		if (opt.bodyClassName) {
			body.addClass(opt.bodyClassName);
		}
		if (opt.bodyStyle) {
			body.css(opt.bodyStyle);
		}
		//优先级selector>content>url
		if (opt.selector) {
			body.append($(opt.selector));
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq);
			}
			
		} else if (opt.url) {
			body.load(opt.url, opt.queryParams, function(data) {
				opt.onLoadSuccess.call(jq, data);
			});
		} else {
			body.html(opt.content);
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq);
			}
		}
	}
	
	/**
	 * 生成foot
	 */
	function _createFoot(jq) {
		var opt = jq.data("panel");
		var foot = _getFooter(jq);
		if ((!opt.buttons || opt.buttons.length == 0) && opt.showFooter !== true) {
			foot.hide();
			return;
		}
		
		if (opt.footClassName) {
			foot.addClass(opt.footClassName);
		}
		if (opt.footStyle) {
			foot.css(opt.footStyle);
		}
		//设置底部内容靠右对齐
		foot.css("text-align", "right");
		$(opt.buttons).each(function(index, item) {
			var btn = $("<input type='button'>").addClass("btn").addClass(item.className).css("margin-left", "20px").val(item.text).appendTo(foot);
			if (item.disabled === true) {
				btn.prop("disabled", true);
			}
			if (item.id) {
				btn.attr("id", item.id);
			}
			if (item.onclick) {
				btn.click(function() {
					item.onclick.call(btn);
				});
			}
		});
	}
	
	//获取标题
	function _getTitle(jq) {
		return jq.find("> .panel-title:first");
	}
	
	//获取头部
	function _getHead(jq) {
		return jq.find("> .panel-heading:first");
	}
	
	//获取主体
	function _getBody(jq) {
		return jq.find("> .panel-body:first");
	}
	
	//获取脚部
	function _getFooter(jq) {
		return jq.find("> .panel-footer:first");
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
	
	
	
	/**
	 * 设置或获取content
	 */
	function _content(jq, content) {
		var obj = _getBody(jq);
		if (content === undefined) {
			return obj.html();
		}
		if ($.type(content) === "object") {
			obj.empty().append(content);
		} else {
			obj.html(content);
		}
	}
	
	/**
	 * 设置或获取footer
	 */
	function _footer(jq, footer) {
		var obj = _getFooter(jq);
		if (footer === undefined) {
			return obj.html();
		}
		if ($.type(footer) === "object") {
			obj.empty().append(footer);
		} else {
			obj.html(footer);
		}
	}
	
	/**
	 * 显示body
	 * @param jq
	 * @param option
	 * @returns
	 */
	function _showBody(jq, option) {
		$.myPlugin.show(_getBody(jq), option);
	}
	
	/**
	 * 隐藏body
	 * @param jq
	 * @param option
	 * @returns
	 */
	function _hideBody(jq, option) {
		$.myPlugin.hide(_getBody(jq), option);
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
	
	//方法
	$.fn.panel.methods = {
		/**
		 * 隐藏面板
		 */
		hide : function(option) {
			var jq = this;
			return this.each(function() {
				$.myPlugin.hide(jq, option);
			});
		},
		/**
		 * 显示面板
		 */
		show : function(option) {
			var jq = this;
			return this.each(function() {
				$.myPlugin.show(jq, option);
			});
		},
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
		},
		/**
		 * 设置内容
		 */
		setContent : function(content) {
			var jq = this;
			return this.each(function() {
				_content(jq, content);
			});
		},
		/**
		 * 获取内容
		 */
		getContent : function() {
			var jq = this;
			return _content(jq);
		},
		/**
		 * 设置底部
		 */
		setFooter : function(footer) {
			var jq = this;
			return this.each(function() {
				_footer(jq, footer);
			});
		},
		/**
		 * 获取底部
		 */
		getFooter : function() {
			var jq = this;
			return _footer(jq);
		},
		/**
		 * 隐藏内容
		 */
		hideBody : function(option) {
			var jq = this;
			return this.each(function() {
				_hideBody(jq, option);
			});
		},
		/**
		 * 显示内容
		 */
		showBody : function(option) {
			var jq = this;
			return this.each(function() {
				_showBody(jq, option);
			});
		},
		/**
		 * 销毁
		 */
		destory : function(option) {
			var jq = this;
			return this.each(function() {
				_destory(jq, option);
			});
		}
	};
	
	//事件
	$.fn.panel.event = {
		onLoadSuccess : function(data) {},
		/**
		 * 关闭之前执行
		 * 如果返回false，则阻止关闭
		 */
		onClose : function(option) {}
	};
	
	//属性
	$.fn.panel.defaults = $.extend({}, $.fn.panel.event, {
		className : $.myPlugin.bootstrapClass.DEFAULT,	//面板的css
		style : {},						//面板的样式
		headClassName : null,			//面板头部css
		headStyle : {},					//面板头部样式
		headTitleSize : 3,				//面板标题的文字大小（就是1,2,3）
		bodyClassName : null,			//面板body  css
		bodyStyle : {},					//面板body样式
		footClassName : null,			//面板底部css
		footStyle : {},					//面板底部样式
		title : "",						//标题
		selector : undefined,			//加载到panel内容的选择器(优先级selector>content>url)
		content : "",					//内容
		url : "",						//从远端加载内容
		queryParams : {},				//从远端加载内容传递的参数
		buttons : [],					//底部的按钮
		showFooter : false,				//是否显示底
		closeAble : false				//是否可以关闭
	});
})(jQuery);