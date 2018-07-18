/**
 * 显示一个随鼠标移动的对话框 
 * 继承与easyui的dialog
 * 作者：黄平
 * 日期：2013-10-07
 */
(function($) {
	$.fn.floatDialog = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.floatDialog.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.dialog(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.floatDialog.defaults, options);
			$(self).hover(
				function(ev) {
					window.setTimeout(function() {
						var isCreate = $(self).data("floatDialogIsCreate");
						if (!isCreate) {
							var div = $("<div>").appendTo($("body"));
							div.dialog($.extend({}, opt, {
								shadow : false,
								modal : false,
								closable : false,
								onOpen : function() {
									$(this).dialog("panel").css({
										opacity : opt.opacity
									});
									div.dialog("move", _getPosition(ev, div));
									$(self).data("floatDialogIsCreate", true);
									$(self).data("dialogObj", div);
								}
							}));
						} else {
							var div = $(self).data("dialogObj");
							div.dialog("open");
							div.dialog("move", _getPosition(ev, div));
						}
					}, opt.delay);
				},
				function() {
					window.setTimeout(function() {
						var dialog = $(self).data("dialogObj");
						if (dialog) {
							if (opt.destroyOnOut) {
								$.fn.floatDialog.methods.destroy.call(self);
							} else {
								dialog.dialog("close");
							}
						}
					}, opt.delay);
				}
			).mousemove(function(ev) {
				var div = $(self).data("dialogObj");
				if (div) {
					div.dialog("move", _getPosition(ev, div));
				}
			});
			
			
			
		});
	};
	
	/**
	 * 获取显示位置
	 */
	function _getPosition(ev, div) {
		var off = 15;
		var totalWidth = $("body").width() - off;
		var totalHeight = $(document).height() - off;
		var panel = div.panel("panel");
		var size = {
			height : panel.height(),
			width : panel.width()
		};
		var t = ev.clientY - size.height - off;
		var l = ev.clientX + off;
		if (t < 0 && (l + size.width) > totalWidth) {
			t = ev.clientY + off;
			l = ev.clientX - size.width - off;
		} else if (t < 0) {
			t = ev.clientY + off;
		} else if ((l + size.width) > totalWidth) {
			l = ev.clientX - size.width - off;
		}
		if ((t + size.height) > totalHeight) {
			t = totalHeight - size.height - off;
		}
		if ((l + size.width) > totalWidth) {
			l = totalWidth - size.width - off;
		}
		return {
			top : t,
			left : l
		};
	}
	
	function _destroy(jq) {
		var dialog = $(jq).data("dialogObj");
		dialog.dialog("destroy");
		$.fn.floatDialog.event.onDestroy.call(jq);
		
	}
	
	$.fn.floatDialog.methods = {
		destroy : function() {
			var jq = this;
			return this.each(function() {
				_destroy(jq);
			});
		}
	};
	$.fn.floatDialog.event = {
		onDestroy : function() {
			$(this).data("floatDialogIsCreate", false);
			$(this).data("dialogObj", null);
		}
	};
	$.fn.floatDialog.defaults = $.extend({}, $.fn.floatDialog.event, {
		title : "",				//对话框标题
		destroyOnOut : false,	//在鼠标离开对象时，是否销毁该对话框
		opacity : 1,			//透明度
		width : 150,			//宽度
		height : 200,			//高度
		delay : 100				//延迟
	});
})(jQuery);