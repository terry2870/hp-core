/**
 * 树的节点是一次性全部加载完 并且节点不再是children形式，而是通过id,pid的形式 
 * 依赖于easyui.treegrid 
 * 作者：黄平
 * 日期：2014-06-12
 */
(function($) {
	$.fn.myTreeGrid = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myTreeGrid.methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				return this.treegrid(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myTreeGrid.defaults, options);
			$(self).data("myTreeGrid", opt);
			createTree(self, opt.rootPid);
		});
	};

	/**
	 * 创建树
	 */
	function createTree(target, id) {
		var opt = $(target).data("myTreeGrid");
		$(target).treegrid($.extend({}, opt, {
			loadFilter : function(d) {
				if (opt.loadFilter) {
					d = opt.loadFilter(d);
				}
				opt.dataList = d;
				return _createChildRen(target, id);
			}
		}));
		$(target).loadSuccess();
	}
	/**
	 * 查找子结点
	 */
	function _createChildRen(target, id) {
		var opt = $(target).data("myTreeGrid");
		var obj = {}, data = [];
		var parentNode = _findChild(target, id);
		if (!parentNode || parentNode.length == 0) {
			return data;
		}
		$(parentNode).each(function(i, item) {
			var dat = _createChildRen(target, item[opt.idField]);
			obj = $.extend({}, {
				checked : item[opt.checkedField],
				attributes : item,
				children : dat
			}, item);
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
		var opt = $(target).data("myTreeGrid");
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

	$.fn.myTreeGrid.methods = {

	};

	$.fn.myTreeGrid.events = {};

	$.fn.myTreeGrid.defaults = $.extend({}, $.fn.myTreeGrid.events, {
		dataList : [],
		pidField : "pid",
		checkedField : "checked",
		rootPid : 0
	});
})(jQuery);