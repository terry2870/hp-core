/**
 * 显示一个简易的导航菜单（只能支持两层菜单） 依赖于easyui的accordion 作者：黄平 日期：2012-10-18
 */
(function($) {
	$.fn.myAccordion = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			if (typeof (options) == "string") {
				var method = $.fn.myAccordion.methods[options];
				if (method){
					return method.call(this, param);
				} else {
					return this.accordion(options, param);
				}
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myAccordion.defaults, options);
			self.data("myAccordion", opt);
			if (opt.ajaxParam && opt.ajaxParam.url) {
				$.ajax($.extend({}, {
					success : function(json) {
                        if (json && opt.loadFilter) {
                            json = opt.loadFilter(json);
                        }
						if (json && json != "") {
							opt.dataList = json;
							_createMenuForEasyUi.call(self);
						}
						if (opt.onLoadSuccess) {
							opt.onLoadSuccess.call(self, json);
						}
					},
					dataType : "json"
				}, opt.ajaxParam));
			} else {
				_createMenuForEasyUi.call(self);
				if (opt.onLoadSuccess) {
					opt.onLoadSuccess.call(self, opt.dataList);
				}
			}
		});
	};
	/**
	 * 生成菜单（适用于easyui）
	 */
	function _createMenuForEasyUi() {
		var self = this;
		var opt = $(self).data("myAccordion");
		var parentNode = _findChild(self, opt.rootPid);
		var childNode = [], div, ul;
		if (parentNode && parentNode.length > 0) {
			$(parentNode).each(function(i, pItem) {
				div = $("<div>", {
					title : pItem[opt.textField]
				}).appendTo(self);
				childNode = _findChild(self, pItem[opt.idField]);
				if (childNode && childNode.length > 0) {
					ul = $("<ul>").addClass("easyui-hp-myAccordion_ul").appendTo(div);
					$(childNode).each(function(j, childItem) {
						$("<li>").html(childItem[opt.textField]).on("click", function() {
							$(self).find(".easyui-hp-myAccordion-li-selected").removeClass("easyui-hp-myAccordion-li-selected");
							$(this).addClass("easyui-hp-myAccordion-li-selected");
							opt.onClickMenu(childItem, pItem);
						}).appendTo(ul);
					});
				}
			});
		}
		$(self).removeData("accordion");
		$(self).accordion(opt);
	}
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		var opt = $(target).data("myAccordion");
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
	$.fn.myAccordion.methods = {

	};
	$.fn.myAccordion.events = {
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
	$.fn.myAccordion.defaults = $.extend({}, $.fn.myAccordion.events, {
		rootPid : 0,
		idField : "id",
		pidField : "pid",
		textField : "text",
		ajaxParam : {
			dataType : "json"
		},
		dataList : []
	});
})(jQuery);