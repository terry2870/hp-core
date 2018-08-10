/**
 * 显示一个分页控件
 * 作者：黄平
 * 日期：2015-07-27
 */
(function($) {
	$.fn.pagination = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn.pagination.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn.pagination.defaults, options);
			self.data("pagination", opt);
			self.addClass("row clearfix").attr("role", "pagination");
			let leftDiv = $("<div>").addClass("col-md-4 column").appendTo(self);
			let leftSpan = $("<span>").addClass("pull-left").css({
				"height" : "44px",
				"line-height" : "44px",
				"margin-left" : "15px"
			}).appendTo(leftDiv);
			let totalPage = _getTotalPage(opt.totalCount, opt.pageSize);
			leftSpan.html("共" + opt.totalCount + "条记录..&nbsp;&nbsp;&nbsp;当前第" + opt.currentPage + "页..总共" + totalPage + "页");
			/*let selectPageDiv = $("<div>").addClass("col-md-2 column").appendTo(self);
			let selectInput = ($("<input type='number'>").css({
				height : "30px",
				width : "50px"
			})).appendTo(selectPageDiv);
			selectPageDiv.append("&nbsp;");
			let selectBtn = $("<input type='button' value='GO' class='btn btn-primary btn-sm'>").appendTo(selectPageDiv).click(function() {
				let newNum = selectInput.val();
				if (!newNum) {
					return;
				}
				_selectPage(self, newNum);
			});
			*/
			let pageDiv = $("<div>").addClass("col-md-8 column").appendTo(self);
			
			let ul = $("<ul>").addClass("pagination pull-right").css("margin", "5px").appendTo(pageDiv);
			let previousLi = $("<li>").appendTo(ul);
			if (opt.currentPage == 1) {
				previousLi.addClass("disabled");
			}
			let previousA = $("<a>").attr({
				"aria-label" : opt.currentPage - 1,
				pageNum : opt.currentPage - 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(opt.previousPageText)).appendTo(previousLi);
			
			let firstLi = $("<li>").appendTo(ul);
			if (opt.currentPage == 1) {
				firstLi.addClass("active");
			}
			let firstA = $("<a>").attr({
				"aria-label" : 1,
				pageNum : 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(1)).appendTo(firstLi);
			if (opt.currentPage > 5) {
				$("<li>").append($("<a>").append($("<span>").attr("aria-hidden",false).html("..."))).appendTo(ul);
			}
			for (let i = opt.currentPage - 5; i <= opt.currentPage; i++) {
				if (i <= 1 || i >= totalPage) {
					continue;
				}
				let pageLi = $("<li>").appendTo(ul);
				if (i == opt.currentPage) {
					pageLi.addClass("active");
				}
				let pageA = $("<a>").attr({
					"aria-label" : i,
					pageNum : i,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(i)).appendTo(pageLi);
			}
			for (let i = opt.currentPage + 1; i < opt.currentPage + 5 && i < totalPage; i++) {
				let pageLi = $("<li>").appendTo(ul);
				if (i == opt.currentPage) {
					pageLi.addClass("active");
				}
				let pageA = $("<a>").attr({
					"aria-label" : i,
					pageNum : i,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(i)).appendTo(pageLi);
			}
			if (opt.currentPage + 5 < totalPage) {
				$("<li>").append($("<a>").append($("<span>").attr("aria-hidden",false).html("..."))).appendTo(ul);
			}
			if (totalPage > 1) {
				let lastLi = $("<li>").appendTo(ul);
				if (opt.currentPage == totalPage) {
					lastLi.addClass("active");
				}
				let lastA = $("<a>").attr({
					"aria-label" : totalPage,
					pageNum : totalPage,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(totalPage)).appendTo(lastLi);
			}
			
			let nextLi = $("<li>").appendTo(ul);
			if (opt.currentPage == totalPage) {
				nextLi.addClass("disabled");
			}
			let nextA = $("<a>").attr({
				"aria-label" : opt.currentPage + 1,
				pageNum : opt.currentPage + 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(opt.nextPageText)).appendTo(nextLi);
			
			ul.find("a").click(function() {
				let pageNum = $(this).attr("pageNum");
				if (!pageNum) {
					return;
				}
				_selectPage(self, $(this).attr("pageNum"));
			});
		});
	};
	
	/**
	 * 选择页数
	 */
	function _selectPage(jq, pageNum) {
		let opt = jq.data("pagination");
		let a = jq.find("a[pageNum='"+ pageNum +"']");
		let li = a.parent();
		if (li.hasClass("active") || li.hasClass("disabled")) {
			return;
		}
		if (opt.onSelectPage) {
			opt.onSelectPage.call(jq, pageNum, opt.pageSize);
		}
		jq.empty();
		jq.pagination($.extend({}, jq.data("pagination"), {
			currentPage : parseInt(pageNum)
		}));
	}
	
	
	/**
	 * 根据总条数，计算总页数
	 */
	function _getTotalPage(totalCount, pageSize) {
		if (totalCount == 0 || pageSize == 0) {
			return 1;
		}
		let tmp = parseInt(totalCount / pageSize);
		if (totalCount % pageSize == 0) {
			return tmp;
		} else {
			return tmp + 1;
		}
	}
	
	$.fn.pagination.methods = {
		/**
		 * 选择页数
		 * @param pageNum
		 */
		selectPage : function(pageNum) {
			_selectPage(this, pageNum);
		},
		/**
		 * 获取分页对象
		 */
		getPagination : function() {
			return this;
		}
	};
	
	$.fn.pagination.event = {
		onSelectPage : function(currentPage, pageSize) {
			//alert("currentPage= " + currentPage + ",pageSize= " + pageSize);
		}
	};
	$.fn.pagination.defaults = $.extend({}, $.fn.pagination.event, {
		//pageType : "backgroundPage", //前台分页-frontPage；后台分页-backgroundPage
		totalCount : 0,
		pageSize : 10,
		currentPage : 1,
		previousPageText : "&laquo;",
		nextPageText : "&raquo;"
	});
})(jQuery);