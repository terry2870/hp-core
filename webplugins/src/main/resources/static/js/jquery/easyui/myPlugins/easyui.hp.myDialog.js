/**
 * 显示一个对话框 继承与easyui的dialog 作者：黄平 日期：2012-10-17
 */
(function($) {
	$.fn.myDialog = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myDialog.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.dialog(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myDialog.defaults, options);
			var o = $.extend({}, opt);
			if (o.closeOnEsc) {
				o.onOpen = function() {
					if (opt.onOpen) {
						opt.onOpen();
					}
					closeSelf.call(self);
					self.parent(".panel").focus();
				};
			} else {
				o.onOpen = function() {
					if (opt.onOpen) {
						opt.onOpen();
					}
					self.parent(".panel").focus();
				};
			}
			if (o.destoryOnClose) {
				o.onClose = function() {
					if (opt.onClose) {
						opt.onClose();
					}
					if (window.top) {
						window.top.$(self).dialog("destroy");
					} else {
						$(self).dialog("destroy");
					}
				};
			}
			self.dialog(o);
		});
	};
	$.fn.myDialog.methods = {
		content : function(content) {
			var jq = this;
			if (content) {
				return this.each(function() {
					jq.dialog("body").find(".dialog-content").html(content);
				});
			} else {
				return this.dialog("body").find("div.dialog-content").html();
			}
		},
		title : function(title) {
			var jq = this;
			if (title) {
				return this.each(function() {
					jq.dialog("setTitle", title);
				});
			} else {
				return this.dialog("header").find("div.panel-title").html();
			}
		}
	};
	
	function closeSelf() {
		var self = $(this);
		self.parent(".panel").keydown(function(e) {
			if (e.keyCode == 27) {
				if (window.parent) {
					window.parent.$(self).myDialog("close");
				} else {
					$(self).myDialog("close");
				}
			}
		});
	}
	$.fn.myDialog.event = {
		onOpen : function() {
			
		}
	};
	$.fn.myDialog.defaults = $.extend({}, $.fn.myDialog.event, {
		closeOnEsc : true,
		destoryOnClose : true,
		openAnimation : "",
		closeAnimation : ""
	});
})(jQuery);