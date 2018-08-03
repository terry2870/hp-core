/**
 * 树的节点是一次性全部加载完 并且节点不再是children形式，而是通过id,pid的形式 依赖于easyui.combotree 作者：黄平
 * 日期：2014-06-12
 */
(function($) {
	$.fn.myCombotree = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myCombotree.methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				return this.combotree(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myCombotree.defaults, options);
			$(self).data("myCombotree", opt);
			if (opt.ajaxParam && opt.ajaxParam.url) {
				$.ajax($.extend({}, {
					success : function(json) {
						if (json) {
							opt.dataList = json;
							createTree(self, opt.rootPid);
						}
					},
					dataType : "json"
				}, opt.ajaxParam));
			} else {
				createTree(self, opt.rootPid);
			}
		});
	};

	/**
	 * 创建树
	 */
	function createTree(target, id) {
		var opt = $(target).data("myCombotree");
		var data = _createChildRen(target, id);
		$(target).combotree($.extend({}, opt, {
			icons : [{
				iconCls : "icon-no",
				handler : function(e) {
					$(e.data.target).myCombotree("clear");
				}
			}],
			data : data
		}));
		$(target).loadSuccess();
	}
	/**
	 * 查找子结点
	 */
	function _createChildRen(target, id) {
		var opt = $(target).data("myCombotree");
		var obj = {}, data = [];
		var parentNode = _findChild(target, id);
		if (!parentNode || parentNode.length == 0) {
			return data;
		}
		$(parentNode).each(function(i, item) {
			var dat = _createChildRen(target, item[opt.idField]);
			obj = {
				id : item[opt.idField],
				text : item[opt.textField],
				checked : item[opt.checkedField],
				attributes : item,
				children : dat
			};
			if (dat && dat.length > 0) {
				obj = $.extend({}, obj, {
					state : "closed"
				});
			}
			data.push(obj);
		});
		return data;
	}
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		var opt = $(target).data("myCombotree");
		var arr = [];
		if (opt.dataList && opt.dataList.length > 0) {
			for (var i = 0; i < opt.dataList.length; i++) {
				if (opt.dataList[i][opt.pidField] == id) {
					arr.push(opt.dataList.splice(i, 1)[0]);
					i--;
				}
			}
		}
		return arr;
	}

	$.fn.myCombotree.methods = {

	};

	$.fn.myCombotree.events = {};

	$.fn.myCombotree.defaults = $.extend({}, $.fn.myCombotree.events, {
		dataList : [],
		ajaxParam : {},
		idField : "id",
		pidField : "pid",
		textField : "text",
		checkedField : "checked",
		rootPid : 0
	});
})(jQuery);