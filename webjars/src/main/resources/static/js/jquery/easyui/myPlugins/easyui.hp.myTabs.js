/**
 * 显示一个tab 继承与easyui的tabs
 * 可以右键点击tab头，来实现关闭和选择tab功能，还可以选择最大化tab页
 * 作者：黄平
 * 日期：2014-05-15
 */
(function($) {
	$.fn.myTabs = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myTabs.methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				return this.tabs(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myTabs.defaults, options, {
				onContextMenu : function(e, title,index) {
					if (options.onContextMenu) {
						options.onContextMenu(e, title, index);
					}
					$(self).tabs("select", index);
				}
			});
			$(self).tabs(opt);
			
			var menu = $("<div>");
			menu.menu({
				hideOnUnhover : false,
				onClick : function(item) {
					if (!item.onclick) {
						$(self).tabs("select", item.text);
					}
				}
			});
			menu.menu("appendItem", {
				text : "关闭",
				iconCls : "icon-cancel",
				onclick : function() {
					$(self).tabs("close", $(self).tabs("getSelected").panel("options").title);
				}
			}).menu("appendItem", {
				text : "关闭其他",
				iconCls : "icon-cancel",
				onclick : function() {
					var tab = $(self).tabs("tabs");
					var selectTab = $(self).tabs("getSelected").panel("options");
					var o = null;
					for (var i = 0; i < tab.length; i++) {
						o = tab[i].panel("options");
						if (selectTab.title == o.title) {
							continue;
						}
						if (o.closable) {
							$(self).tabs("close", o.title);
							i--;
						}
					}
				}
			}).menu("appendItem", {
				text : "关闭全部",
				iconCls : "icon-cancel",
				onclick : function() {
					var tab = $(self).tabs("tabs");
					var selectTab = $(self).tabs("getSelected").panel("options");
					var o = null;
					for (var i = 0; i < tab.length; i++) {
						o = tab[i].panel("options");
						if (o.closable) {
							$(self).tabs("close", o.title);
							i--;
						}
					}
				}
			});
			if (opt.maxSizeAble === true) {
				menu.menu("appendItem", {
					text : "最大化",
					iconCls : "icon-more",
					onclick : function() {
						var selectPanel = $(self).tabs("getSelected");
						
						var div = $("<div>").appendTo($(window.top.document.body));
						window.top.$(div).window({
							width : "100%",
							height : "100%",
							title : selectPanel.panel("options").title,
							content : selectPanel.html(),
							modal : true,
							collapsible : true,
							cache : false
						});
					}
				});
			}
			
			menu.menu("appendItem", {
				separator : true
			});
			$(".tabs-header", self).bind("contextmenu", function(e1) {
				e1.preventDefault();
				var tab = $(self).tabs("tabs");
				var selectTab = $(self).tabs("getSelected").panel("options");
				var closeMenu = menu.menu("findItem", "关闭");
				if (selectTab.closable) {
					menu.menu("enableItem", closeMenu.target);
				} else {
					menu.menu("disableItem", closeMenu.target);
				}
				var parentMenu = menu.menu("findItem", "选择标签");
				if (parentMenu) {
					menu.menu("removeItem", parentMenu.target);
				}
				menu.menu("appendItem", {
					text : "选择标签",
					iconCls : "icon-ok"
				});
				parentMenu = menu.menu("findItem", "选择标签");
				var o;
				for (var i = 0; i < tab.length; i++) {
					o = tab[i].panel("options");
					menu.menu("appendItem", {
						parent : parentMenu.target,
						text : o.title,
						disabled : o.title == selectTab.title ? true : false,
						iconCls : "icon-tip"
					});
				}
				menu.menu("show", {
					left : e1.clientX,
					top : e1.clientY
				});
			});
		});
	};
	
	$.fn.myTabs.methods = {
		
	};
	$.fn.myTabs.events = {
		
	};
	$.fn.myTabs.defaults = $.extend({}, $.fn.myTabs.events, {
		maxSizeAble : false
	});
})(jQuery);