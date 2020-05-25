/**
 * 一个分页插件
 * 作者：黄平
 * 日期：2020-05-24
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "pagination";
	let MAX_SHOW_PAGE = 5;
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			opt.totalPage = _getTotalPage(opt.total, opt.pageSize);
			self.data(pluginName, opt);
			_create(self, opt);
		});
	};

	/**
	 * 创建
	 * @param {*} jq 
	 */
	function _create(jq, opt) {
		let ul = $("<ul>").addClass("pagination justify-content-end").appendTo(jq);
		
		//第一页
		_createItem(jq, opt.firstPageText, opt.currentPage == 1, 1);

		//上一页
		_createItem(jq, opt.previousPageText, opt.currentPage == 1, (opt.currentPage - 1 <= 0 ? 1 : opt.currentPage - 1));

		let showPageArr = [];
		let ceil = Math.ceil(MAX_SHOW_PAGE / 2);
		let floor = Math.floor(MAX_SHOW_PAGE / 2);
		let start = 0;
		if (opt.currentPage - ceil <= 0) {
			start = 1;
		} else if (opt.currentPage + floor >= opt.totalPage) {
			start = opt.totalPage - MAX_SHOW_PAGE + 1;
		} else {
			start = opt.currentPage - floor;
		}

		while (true) {
			if (showPageArr.length >= MAX_SHOW_PAGE || start > opt.totalPage) {
				break;
			}
			showPageArr.push(start++);
		}

		if (showPageArr[0] > 1) {
			showPageArr.unshift("...");
		}
		if (showPageArr[showPageArr.length - 1] < opt.totalPage) {
			showPageArr.push("...");
		}

		for (let i = 0; i < showPageArr.length; i++) {
			if (showPageArr[i] == "...") {
				_createItem(jq, "...", true, 1);
			} else {
				_createItem(jq, showPageArr[i], false, showPageArr[i]);
			}
		}

		//下一页
		_createItem(jq, opt.nextPageText, opt.currentPage == opt.totalPage, (opt.currentPage + 1 > opt.totalPage ? opt.totalPage : opt.currentPage + 1));

		//最后一页
		_createItem(jq, opt.lasrPageText, opt.currentPage == opt.totalPage, opt.totalPage);
	}

	/**
	 * 生成每个分页按钮
	 * @param {*} jq 
	 * @param {*} text 
	 * @param {*} disabled 
	 * @param {*} pageNum 
	 */
	function _createItem(jq, text, disabled, pageNum) {
		let opt = jq.data(pluginName);
		let ul = _getUL(jq);
		if (pageNum < 1 || pageNum > opt.totalPage) {
			return;
		}
		let li = $("<li>").addClass("page-item").appendTo(ul);
		li.attr("pagenum", pageNum);
		if (opt.currentPage == pageNum) {
			li.addClass("active");
		}
		if (disabled) {
			li.addClass("disabled");
		}
		let a = $("<a>").addClass("page-link").attr("href", "#").appendTo(li);
		if (disabled) {
			a.attr("aria-disabled", "true");
		} else {
			a.click(function() {
				let parentLI = $(this).parent();
				if (parentLI.hasClass("active") || parentLI.hasClass("disabled")) {
					return;
				}
				_selectPage(jq, parentLI.attr("pagenum"));
			});
		}
		a.text(text);
	}

	/**
	 * 获取ul
	 * @param {*} jq 
	 */
	function _getUL(jq) {
		return jq.find("ul");
	}

	/**
	 * 跳转页数
	 * @param {*} jq 
	 * @param {*} pageNum 
	 */
	function _selectPage(jq, pageNum) {
		let opt = jq.data(pluginName);
		if (opt.onSelectPage) {
			opt.onSelectPage.call(jq, pageNum, opt.pageSize);
		}
		jq.empty();
		jq.pagination($.extend({}, opt, {
			currentPage : parseInt(pageNum)
		}));
	}

	/**
	 * 根据总条数，计算总页数
	 * @param {*} total 
	 * @param {*} pageSize 
	 */
	function _getTotalPage(total, pageSize) {
		if (total == 0 || pageSize == 0) {
			return 1;
		}
		let tmp = parseInt(total / pageSize);
		if (total % pageSize == 0) {
			return tmp;
		} else {
			return tmp + 1;
		}
	}

	/**
	 * 获取UL
	 * @param {*} jq 
	 */
	function _getUL(jq) {
		return jq.find("ul.pagination");
	}
	
	//方法
	$.fn[pluginName].methods = {
		/**
		 * 选择页数
		 * @param {*} pageNum 
		 */
		selectPage : function(pageNum) {
			let jq = this;
			return this.each(function() {
				_selectPage(jq, pageNum);
			});
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 选择页数
		 * @param {*} currentPage 
		 * @param {*} pageSize 
		 */
		onSelectPage : function(currentPage, pageSize) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		total : 0,
		pageSize : 20,
		currentPage : 1,
		firstPageText : "第一页",
		previousPageText : "上一页",
		nextPageText : "下一页",
		lasrPageText : "最后页"
	});
})(jQuery);