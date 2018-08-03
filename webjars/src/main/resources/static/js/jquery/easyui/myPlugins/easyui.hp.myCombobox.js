/**
 * 显示一个带搜索功能的组合框
 * 作者：黄平
 * 日期：2016-09-09
 */
(function($) {
	$.fn.myCombobox = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.myCombobox.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.combobox(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myCombobox.defaults, options);
			$(self).data("myCombobox", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		var opt = jq.data("myCombobox");
		var o = $.extend({}, opt, {
			icons : [{
				iconCls : "icon-clear",
				handler : function(e) {
					$(e.data.target).combobox("clear");
				}
			}],
			editable : false,
			onShowPanel : function() {
				if (opt.onShowPanel) {
					opt.onShowPanel.call();
				}
				var dropPanel = jq.myCombobox("panel");
				var searchText = _getSearchText(jq);
				searchText.val("");
				searchText.width(dropPanel.width() - 8);
				_getListDiv(jq).height(dropPanel.height() - searchText.height() - 10);
			},
			onLoadSuccess : function() {
				if (opt.onLoadSuccess) {
					opt.onLoadSuccess.call(this);
				}
				_addSearchText(jq);
			},
			value : opt.value || jq.val()
		});
		jq.combobox(o);
	}
	
	function _addSearchText(jq) {
		var opt = jq.data("myCombobox");
		//添加搜索框
		var searchTextDiv = $("<div role='comboboxSearchTextDiv'>");
		var searchText = $("<input type='text' role='comboboxSearchText'>").appendTo(searchTextDiv);
		var dropPanel = jq.myCombobox("panel").css({
			overflow : "hidden"
		});
		dropPanel.prepend(searchTextDiv);
		var oldList = dropPanel.find("div.combobox-item");
		var listDiv = $("<div role='comboboxListDiv'>").css({
			overflow : "auto"
		}).appendTo(dropPanel);
		listDiv.append(oldList);
		searchText.on("mousedown",function(e){e.stopPropagation();});
		
		searchText.keyup(function() {
			var value = this.value;
			var d = opt.loadFilter ? opt.loadFilter(opt.data) : opt.data;
			if (value == "") {
				//当输入内容为空时，则显示所有
				_getListDiv(jq).find("div").show();
				return;
			}
			var data = [];
			var list = _getListDiv(jq).find("div");
			$(list).each(function(item, index) {
				if ($(this).html().indexOf(value) >= 0) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});
		});
		
		jq.loadSuccess();
	}
	
	//添加
	function _showList(jq, data) {
		var opt = jq.data("myCombobox");
		var listPanel = _getListDiv(jq);
		listPanel.empty();
		if (!data || data.length == 0) {
			return;
		}
		jq.myCombobox("loadData", opt.unLoadFilter ? opt.unLoadFilter(data) : data);
		
	}
	
	/**
	 * 获取列表
	 */
	function _getSearchTextDiv(jq) {
		return jq.combobox("panel").find("div[role='comboboxSearchTextDiv']");
	}
	
	/**
	 * 获取搜索输入框
	 */
	function _getSearchText(jq) {
		return jq.combobox("panel").find("input[role='comboboxSearchText']");
	}
	
	/**
	 * 获取列表的div
	 */
	function _getListDiv(jq) {
		return jq.combobox("panel").find("div[role='comboboxListDiv']");
	}
	
	//方法
	$.fn.myCombobox.methods = {
		
	};
	
	//事件
	$.fn.myCombobox.event = {
		
	};
	
	//属性
	$.fn.myCombobox.defaults = $.extend({}, $.fn.myCombobox.event, $.fn.combobox.defaults, {
	
		unLoadFilter : function(data) {			//重新组装list
			return {
				code : 200,
				data :data
			};
		}
	});
})(jQuery);