/**
 * 显示一个分页控件
 * 作者：黄平
 * 日期：2015-07-27
 */
(function($) {
	$.fn.pagination = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.pagination.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.pagination.defaults, options);
			self.data("pagination", opt);
			self.addClass("row clearfix").attr("role", "pagination");
			var leftDiv = $("<div>").addClass("col-md-4 column").appendTo(self);
			var leftSpan = $("<span>").addClass("pull-left").css({
				"height" : "44px",
				"line-height" : "44px",
				"margin-left" : "15px"
			}).appendTo(leftDiv);
			var totalPage = _getTotalPage(opt.totalCount, opt.pageSize);
			leftSpan.html("共" + opt.totalCount + "条记录..&nbsp;&nbsp;&nbsp;当前第" + opt.currentPage + "页..总共" + totalPage + "页");
			var selectPageDiv = $("<div>").addClass("col-md-2 column").appendTo(self);
			var selectInput = ($("<input type='number'>").css({
				height : "30px",
				width : "50px"
			})).appendTo(selectPageDiv);
			selectPageDiv.append("&nbsp;");
			var selectBtn = $("<input type='button' value='GO' class='btn btn-primary btn-sm'>").appendTo(selectPageDiv).click(function() {
				var newNum = selectInput.val();
				if (!newNum) {
					return;
				}
				_selectPage(self, newNum);
			});
			//leftSpan.html("共" + opt.totalCount + "条记录");
			var pageDiv = $("<div>").addClass("col-md-8 column").appendTo(self);
			
			var ul = $("<ul>").addClass("pagination pull-right").css("margin", "5px").appendTo(pageDiv);
			var previousLi = $("<li>").appendTo(ul);
			if (opt.currentPage == 1) {
				previousLi.addClass("disabled");
			}
			var previousA = $("<a>").attr({
				"aria-label" : opt.currentPage - 1,
				pageNum : opt.currentPage - 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(opt.previousPageText)).appendTo(previousLi);
			
			var firstLi = $("<li>").appendTo(ul);
			if (opt.currentPage == 1) {
				firstLi.addClass("active");
			}
			var firstA = $("<a>").attr({
				"aria-label" : 1,
				pageNum : 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(1)).appendTo(firstLi);
			if (opt.currentPage > 5) {
				$("<li>").append($("<a>").append($("<span>").attr("aria-hidden",false).html("..."))).appendTo(ul);
			}
			for (var i = opt.currentPage - 5; i <= opt.currentPage; i++) {
				if (i <= 1 || i >= totalPage) {
					continue;
				}
				var pageLi = $("<li>").appendTo(ul);
				if (i == opt.currentPage) {
					pageLi.addClass("active");
				}
				var pageA = $("<a>").attr({
					"aria-label" : i,
					pageNum : i,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(i)).appendTo(pageLi);
			}
			for (var i = opt.currentPage + 1; i < opt.currentPage + 5 && i < totalPage; i++) {
				var pageLi = $("<li>").appendTo(ul);
				if (i == opt.currentPage) {
					pageLi.addClass("active");
				}
				var pageA = $("<a>").attr({
					"aria-label" : i,
					pageNum : i,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(i)).appendTo(pageLi);
			}
			if (opt.currentPage + 5 < totalPage) {
				$("<li>").append($("<a>").append($("<span>").attr("aria-hidden",false).html("..."))).appendTo(ul);
			}
			if (totalPage > 1) {
				var lastLi = $("<li>").appendTo(ul);
				if (opt.currentPage == totalPage) {
					lastLi.addClass("active");
				}
				var lastA = $("<a>").attr({
					"aria-label" : totalPage,
					pageNum : totalPage,
					href : "#"
				}).append($("<span>").attr("aria-hidden",false).html(totalPage)).appendTo(lastLi);
			}
			
			var nextLi = $("<li>").appendTo(ul);
			if (opt.currentPage == totalPage) {
				nextLi.addClass("disabled");
			}
			var nextA = $("<a>").attr({
				"aria-label" : opt.currentPage + 1,
				pageNum : opt.currentPage + 1,
				href : "#"
			}).append($("<span>").attr("aria-hidden",false).html(opt.nextPageText)).appendTo(nextLi);
			
			ul.find("a").click(function() {
				var pageNum = $(this).attr("pageNum");
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
		var opt = jq.data("pagination");
		var a = jq.find("a[pageNum='"+ pageNum +"']");
		var li = a.parent();
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
		var tmp = parseInt(totalCount / pageSize);
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