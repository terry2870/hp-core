/**
 * 显示一棵树，树的节点是一次性全部加载完 并且节点不再是children形式，而是通过id,pid的形式 
 * 依赖于easyui的tree 
 * 作者：黄平
 * 日期：2012-10-17
 */
(function($) {
	$.fn.myTree = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myTree.methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				return this.tree(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myTree.defaults, options);
			$(self).data("myTree", opt);
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
		var opt = $(target).data("myTree");
		$(target).tree($.extend({}, opt, {
			data : _createChildRen(target, id)
		}));
	}
	/**
	 * 查找子结点
	 */
	function _createChildRen(target, id) {
		var opt = $(target).data("myTree");
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
			if (opt.allClosed) {
				obj = $.extend({}, obj, {
					state : "closed"
				});
			} else {
				if (dat && dat.length > 0) {
					obj = $.extend({}, obj, {
						state : "closed"
					});
				}
			}
			data.push(obj);
		});
		return data;
	}
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		var opt = $(target).data("myTree");
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
	$.fn.myTree.methods = {
		/**
		 * 获取所有被选中的checkbox（包括实心的节点）
		 */
		getCheckeds : function() {
			var checked = $(this).tree("getChecked");
			var checkbox2 = $(this).find("span.tree-checkbox2").parent();
			$.each(checkbox2, function() {
				var node = $.extend({}, $.data(this, "tree-node"), {
					target : this
				});
				checked.push(node);
			});
			return checked;
		},
		/**
		 * 获取所有实心节点
		 */
		getSolidCheck : function() {
			var checked = [];
			var checkbox2 = $(this).find("span.tree-checkbox2").parent();
			$.each(checkbox2, function() {
				var node = $.extend({}, $.data(this, "tree-node"), {
					target : this
				});
				checked.push(node);
			});
			return checked;
		},
		reload : function() {
			return $(this).myTree($(this).data("myTree"));
		}
	};
	
    $.fn.myTree.events = {};
	
	$.fn.myTree.defaults = $.extend({}, $.fn.myTree.events, {
		dataList : [],
		ajaxParam : {},
		idField : "id",
		pidField : "pid",
		textField : "text",
		checkedField : "checked",
		rootPid : 0,
		allClosed : false	//所有的节点都可以展开
	});
})(jQuery);