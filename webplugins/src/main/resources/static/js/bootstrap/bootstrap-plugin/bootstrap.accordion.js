/**
 * 显示一个简易的导航菜单（只能支持两层菜单） 
 * 依赖于bootstrap的accordion 
 * 作者：黄平 日期：2016-04-12
 */
(function($) {
	$.fn.accordion = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.accordion.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.accordion.defaults, $.fn.accordion.events, options);
			self.data("accordion", opt);
			if (opt.ajaxParam && opt.ajaxParam.url) {
				$.ajax($.extend({}, {
					success : function(json) {
						if (!json) {
							return;
						}
						opt.dataList = json;
						if (opt.loadFilter) {
							opt.dataList = opt.loadFilter(json);
						}
						_createAccordionMenu.call(self);
						if (opt.onLoadSuccess) {
							opt.onLoadSuccess.call(self, json);
						}
					},
					dataType : "json"
				}, opt.ajaxParam));
			} else {
				_createAccordionMenu.call(self);
				if (opt.onLoadSuccess) {
					opt.onLoadSuccess.call(self, opt.dataList);
				}
			}
		});
	};
	/**
	 * 生成菜单（适用于bootstrap）
	 */
	function _createAccordionMenu() {
		var self = this;
		var opt = $(self).data("accordion");
		var parentNode = _findChild(self, opt.rootPid);
		var childNode = [], div, ul, panelHead, headA, bodyDiv;
		if (parentNode && parentNode.length > 0) {
			self.addClass("panel-group");
			$(parentNode).each(function(i, pItem) {
				div = $("<div>").addClass("panel").appendTo(self);
				if (opt.panelClass) {
					div.addClass(opt.panelClass);
				}
				panelHead = $("<div>").addClass("panel-heading").appendTo(div);
				headA = $("<a>").addClass("panel-title collapsed").attr({
					"data-toggle" : "collapse",
					"data-parent" : self.attr("id"),
					"href" : "#panel-element-" + pItem[opt.idField]
				}).html(pItem[opt.textField]).appendTo(panelHead);
				
				childNode = _findChild(self, pItem[opt.idField]);
				if (childNode && childNode.length > 0) {
					bodyDiv = $("<div>").addClass("panel-collapse collapse").attr({
						"id" : "panel-element-" + pItem[opt.idField]
					}).appendTo(div);
					ul = $("<ul>").addClass("nav nav-list").appendTo(bodyDiv);
					$(childNode).each(function(j, childItem) {
						$("<li>").append($("<a href='#'>").html(childItem[opt.textField])).click(function() {
							opt.onClickMenu.call(self, childItem, pItem);
						}).appendTo(ul);
					});
				}
			});
		}
//		$(self).removeData("accordion");
	}
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		var opt = $(target).data("accordion");
		var arr = [];
		if (opt.dataList && opt.dataList.length > 0) {
			for ( var i = 0; i < opt.dataList.length; i++) {
				if (opt.dataList[i][opt.pidField] == id) {
					arr.push(opt.dataList.splice(i, 1)[0]);
					i--;
				}
			}
		}
		return arr;
	}
	$.fn.accordion.methods = {

	};
	$.fn.accordion.events = {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {
			
		},
		/**
		 * 点击菜单执行
		 * @param item
		 * @param parent
		 */
		onClickMenu : function(item, parent) {
			
		}
	};
	$.fn.accordion.defaults = $.extend({}, $.fn.accordion.events, {
		panelClass : "panel-default",	//标题面板 的样式 （primary,success,info,warning,danger）
		rootPid : 0,
		idField : "id",
		pidField : "pid",
		textField : "text",
		ajaxParam : {
			dataType : "json"
		},
		dataList : [],
		loadFilter : function(data) {
			return data;
		}
	});
})(jQuery);