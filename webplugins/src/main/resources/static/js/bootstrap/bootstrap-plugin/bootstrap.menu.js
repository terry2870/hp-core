/**
 * 显示一个菜单
 * 作者：黄平
 * 日期：2016-05-13
 */
(function($) {
	$.fn.menu = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.menu.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.menu.defaults, options);
			self.data("menu", opt);
			if (opt.url) {
				$.ajax({
					url : opt.url,
					data : opt.queryParams,
					type : opt.method,
					dataType : opt.dataType ? opt.dataType : "json",
					success : function(data) {
						if (!data) {
							return;
						}
						opt.data = data;
						if (opt.loadFilter) {
							opt.data = opt.loadFilter(data);
						}
						_createMenu(self);
					}
				});
			} else {
				_createMenu(self);
			}
		});
	};

	function _createSubMenu(jq, ul, nodes) {
		var opt = jq.data("menu");
		if (nodes && nodes.length > 0) {
			$(nodes).each(function(index, item) {
				var li = $("<li>").appendTo(ul).data("menuItem", item);
				if (item.divider === true) {
					li.addClass("divider");
					return true;
				}
				var a = $("<a>").attr("href", "#").html(item[opt.textField]).appendTo(li);
				if (opt.onClick) {
					a.click(function() {
						opt.onClick.call(jq, item);
					});
				}
				var children = _findChild(jq, item[opt.idField]);
				if (children && children.length > 0) {
					li.addClass("dropdown-submenu");
					var u = $("<ul>").addClass("dropdown-menu").appendTo(li);
					_createSubMenu(jq, u, children);
				}
				
			});
		}
	}
	
	/**
	 * 生成menu
	 */
	function _createMenu(jq) {
		var opt = jq.data("menu");
		jq.addClass("dropdown");
		var button = $("<input type=\"button\">").addClass("btn " + opt.buttonClass).attr({
			"data-toggle" : "dropdown",
			"data-target" : "#"
		}).val(opt.buttonName).append($("<span>").addClass("caret")).appendTo(jq);
		var ul = $("<ul>").addClass("dropdown-menu multi-level").attr({
			"role" : "menu",
			"aria-labelledby" : "dropdownMenu"
		}).appendTo(jq);
		var childNode = _findChild(jq, opt.rootPid);
		var li;
		if (childNode && childNode.length > 0) {
			_createSubMenu(jq, ul, childNode);
		}
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
		//设置已经加载完成
		jq.attr("loadStatus", "1");
	}
	
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		var opt = $(target).data("menu");
		var arr = [];
		if (opt.data && opt.data.length > 0) {
			for ( var i = 0; i < opt.data.length; i++) {
				if (opt.data[i][opt.pidField] == id) {
					arr.push(opt.data[i]);
				}
			}
		}
		return arr;
	}
	
	
	//方法
	$.fn.menu.methods = {
		
	};
	
	//事件
	$.fn.menu.event = {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {},
		/**
		 * 点击菜单执行
		 */
		onClick : function(item) {}
	};
	
	//属性
	$.fn.menu.defaults = $.extend({}, $.fn.menu.event, {
		buttonClass : "btn-primary",
		buttonName : "下拉菜单",
		rootPid : 0,
		idField : "id",
		pidField : "pid",
		textField : "text",
		url : undefined,
		dataType : "json",
		type : "POST",
		queryParams : {},
		data : [],
		loadFilter : function(data) {}
	});
	
	/**
	 * 其中data包含的属性有
	 * divider=false			是否是分割线
	 */
})(jQuery);