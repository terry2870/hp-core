/**
 * 显示一个简易的导航菜单（只能支持两层菜单） 
 * 依赖于bootstrap的accordion 
 * 作者：黄平 日期：2016-04-12
 */
(function($) {
	$.fn.accordion = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn.accordion.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn.accordion.defaults, $.fn.accordion.events, options);
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
		let self = this;
		let opt = $(self).data("accordion");
		let parentNode = _findChild(self, opt.rootPid);
		//let childNode = [], div, ul, panelHead, headA, bodyDiv;
		let childNode = [], panel, ul;
		if (!parentNode || parentNode.length == 0) {
			return;
		}
		
		self.addClass("panel-group");
		for (let i = 0; i < parentNode.length; i++) {
			let parentItem = parentNode[i];
			panel = $("<div>").appendTo(self);
			//查找该节点的子节点
			childNode = _findChild(self, parentItem[opt.idField]);
			if (!childNode || childNode.length == 0) {
				continue;
			}
			ul = $("<ul>").addClass("nav nav-list");
			for (let j = 0; j < childNode.length; j++) {
				let childItem = childNode[j];
				let li = $("<li>").appendTo(ul);
				let a = $("<a>").appendTo(li);
				a.attr({
					href : "#"
				}).click(function() {
					opt.onClickMenu.call(self, childItem, parentItem);
				}).html(childItem[opt.textField]);
			}
			
			panel.panel({
				panelClass : opt.panelClass,
				headStyle : {
					padding : "5px 8px"
				},
				title : parentItem[opt.textField],
				content : ul,
				collapseAble : true,
				collapse : (opt.selected != null && opt.selected === i ? "open" : "close"),
				usePanelCollapse : true,
				paneElementSuffix : parentItem[opt.idField]
			});
		}
	}
	/**
	 * 查找子结点
	 */
	function _findChild(target, id) {
		let opt = $(target).data("accordion");
		let arr = [];
		if (opt.dataList && opt.dataList.length > 0) {
			for (let i = 0; i < opt.dataList.length; i++) {
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
		panelClass : $.bootstrapClass.DEFAULT,
		selected : null,								//选择的索引
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