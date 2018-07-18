/**
 * 显示一个模态对话框
 * 作者：黄平
 * 日期：2015-07-28
 */
(function($) {
	$.fn.dialog = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.dialog.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
//			_destory(self);
			var opt = $.extend({}, $.fn.dialog.defaults, options);
			self.data("dialog", opt);
			self.addClass("modal");
			if (opt.animate === true) {
				self.addClass("fade");
			}
			self.attr({
				role : "dialog",
				tabindex : opt.tabIndex
			});
			var header = $("<div>").addClass("modal-header");
			if (opt.closeAble === true) {
				header.append($("<button type='button'>").addClass("close").attr({
					"data-dismiss" : "modal",
					"aria-label" : "Close"
				}).append($("<span>").attr("aria-hidden", true).html("&times;")));
			}
			header.append($("<h4>").addClass("modal-title").html(opt.title));
			var body = $("<div>").addClass("modal-body").css({
                width : "100%",
				overflow : "auto"
			});
			if (opt.content !== undefined) {
				if ($.type(opt.content) === "string") {
					body.html(opt.content);
				} else {
					body.append(opt.content);
				}
			} else {
				if (opt.href) {
					if (opt.onBeforeLoad) {
						opt.onBeforeLoad.call(self);
					}
					$(body).load(opt.href, $.extend({}, opt.queryParams), function(data) {
						if (opt.onLoadSuccess) {
							opt.onLoadSuccess.call(self, data);
						}
					});
				}
			}
			var footer = $("<div>").addClass("modal-footer");
			if (opt.buttons && opt.buttons.length > 0) {
				var btn = null;
				$(opt.buttons).each(function(index, item) {
					btn = $("<button type='button'>").addClass("btn").addClass(item.className).html(item.text).appendTo(footer);
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
			var dialogDiv = $("<div>").addClass("modal-dialog").css(opt.style).addClass(opt.size ? opt.size : "").attr("role", "document").appendTo(self);
			var contentDiv = $("<div>").addClass("modal-content").append(header).append(body).append(footer).appendTo(dialogDiv);
			
			self.on("show.bs.modal", function(e) {
				if (opt.onBeforeShow) {
					opt.onBeforeShow.call(self);
				}
			});
			self.on("shown.bs.modal", function(e) {
				if (opt.onAfterShow) {
					opt.onAfterShow.call(self);
				}
			});
			self.on("hide.bs.modal", function(e) {
				if (opt.onBeforeHide) {
					opt.onBeforeHide.call(self);
				}
			});
			self.on("hidden.bs.modal", function(e) {
				if (opt.onAfterHide) {
					opt.onAfterHide.call(self);
				}
				_destory(self);
			});
			self.modal($.extend({}, {
				backdrop : opt.backdrop,
				keyboard : opt.keyboard,
				show : opt.show
			}));
			
		});
	};
	
	/**
	 * 销毁
	 */
	function _destory(jq) {
		//$(jq).empty().removeClass("modal fade").removeAttr("role").removeAttr("aria-labelledby");
		jq.remove();
	}
	
	
	$.fn.dialog.methods = {
		show : function() {
			return this.each(function() {
				$(this).modal("show");
			});
		},
		hide : function() {
			return this.each(function() {
				$(this).modal("hide");
			});
		},
		toggle : function() {
			return this.each(function() {
				$(this).modal("toggle");
			});
		},
		title : function(title) {
			if (title === undefined) {
				return $(this).find(".modal-title").html();
			} else {
				return this.each(function() {
					$(this).find(".modal-title").html(title);
				});
			}
		},
		content : function(content) {
			if (content === undefined) {
				return $(this).find(".modal-body").html();
			} else {
				return this.each(function() {
					$(this).find(".modal-body").html(content);
				});
			}
		},
		destory : function() {
			return this.each(function() {
				_destory(this);
			});
		}
	};
	
	$.fn.dialog.events = {
		onBeforeShow : function() {},
		onAfterShow : function() {},
		onBeforeHide : function() {},
		onAfterHide : function() {},
		onBeforeLoad : function() {},
		onLoadSuccess : function(data) {}
	};
	$.fn.dialog.defaults = $.extend({}, $.fn.dialog.events, {
		height : null,
		width : null,
		size : "",				//尺寸，有modal-sm或modal-lg
		style : {},				//对话框样式
		animate : true,			//是否动画显示
		tabIndex : -1,			//
		closeAble : true,		//是否可以关闭
		title : "",				//标题
		content : undefined,	//内容
		href : "",				//从远端加载内容
		queryParams : {},		//从远端加载内容传递的
		backdrop : true,		//如果为 static 则点击空白不会关闭对话框
		keyboard : true,		//是否可以使用esc键关闭
		show : true,			//是否显示对话框
		/**
		 * 每个按钮包含属性
		 * className	样式
		 * text			内容
		 * onclick		点击事件
		 * disabled		是否禁用
		 * id
		 */
		buttons : []			//按钮
	});
})(jQuery);