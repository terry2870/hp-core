/**
 * 显示一个带搜索功能的下拉框
 * 作者：黄平
 * 日期：2016-04-25
 */
(function($) {
	$.fn.combobox = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.combobox.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.combobox.defaults, options, {
				editable : false
			});
			self.data("combobox", opt);
			if (opt.url) {
				$.ajax({
					url : opt.url,
					data : opt.queryParams,
					type : opt.method,
					dataType : opt.dataType ? opt.dataType : "json",
					success : function(data) {
						if (!data) {
							return;
						}
						opt.data = data;
						if (opt.loadFilter) {
							opt.data = opt.loadFilter(data);
						}
						
						_createCombobox(self);
					}
				});
			} else {
				_createCombobox(self);
				//_createCombo(self);
			}
		});
	};

	/**
	 * 创建
	 */
	function _createCombobox(jq) {
		var opt = jq.data("combobox");
		if (opt.className) {
			jq.addClass(opt.className);
		}
		var panel = $("<div role='comboboxPanel'>").css({
			margin : 0,
			padding : 0
		});
		var searchText = $("<div role='comboboxSearchText'>").appendTo(panel);
		var text = $("<input type='text' style='width:100%'>").appendTo(searchText);
		//只有当输入框不为
		
		text.keyup(function() {
			if (opt.forceRemote === true) {
				$.ajax({
					url : opt.url,
					data : opt.queryParams,
					type : opt.method,
					dataType : opt.dataType ? opt.dataType : "json",
					success : function(data) {
						if (!data) {
							return;
						}
						opt.data = data;
						if (opt.loadFilter) {
							opt.data = opt.loadFilter(data);
						}
						_setList(jq, opt.data);
					}
				});
				return;
			}
			if (this.value == "") {
				//当输入内容为空时，则显示所有
				_setList(jq, opt.data);
				return;
			}
			var data = [];
			for (var i = 0; i < opt.data.length; i++) {
				if (opt.data[i][opt.textField].indexOf(this.value) >= 0) {
					data.push(opt.data[i]);
				}
			}
			_setList(jq, data);
		});
//		searchText.append(text);
		var listPanel = $("<div role='comboboxListPanel'>").css({
			height : opt.panelHeight,
			overflow : "auto"
		}).appendTo(panel);
		jq.combo($.extend({}, opt, {
			panelSelector : panel,
			panelHeight : opt.panelHeight ? opt.panelHeight + 29 : "auto"
		}));
		//当显示下拉的时候，清空以前输入，并且把下拉数据还原
		jq.click(function() {
			_getSearchText(jq).val("");
			_setList(jq, opt.data);
		});
		_setList(jq, opt.data);
		if (opt.value != undefined && opt.value != null) {
			_select(jq, opt.value);
		}
		
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
		
		//设置已经加载完成
		jq.attr("loadStatus", "1");
	}
	
	//添加
	function _setList(jq, data) {
		var opt = jq.data("combobox");
		var listPanel = _getListPanel(jq);
		listPanel.empty();
		if (!data || data.length == 0) {
			return;
		}
		//遍历，显示item
		$(data).each(function(index, item) {
			if (opt.maxlength > 0 && index > opt.maxlength) {
				return false;
			}
			var value = item[opt.valueField], text = item[opt.textField];
			if (opt.formatter) {
				var obj = opt.formatter(item, index);
				value = obj.value;
				text = obj.text;
			}
			$("<a href=\"#\">").data("combobox-item", item).attr({
				value : value,
				text : text
			}).addClass("list-group-item").css({
				"padding-top" : "5px"
			}).html(text).click(function() {
				_select(jq, value);
				jq.combo("hidePanel");
			}).appendTo(listPanel);
		});
	}
	
	//选中一条
	function _select(jq, value) {
		var opt = jq.data("combobox");
		var p = jq.combobox("getPanel");
		var valueObj = p.find("a[value='"+ value +"']");
		opt.onChange.call(jq, value, valueObj ? valueObj.data("combobox-item") : null);
		if (valueObj) {
			jq.val(valueObj.attr("text"));
			jq.attr("realValue", valueObj.attr("value"));
		}
	}
	
	/**
	 * 获取panel
	 */
	function _getPanel(jq) {
		return jq.combo("getPanel").find("div[role='comboboxPanel']");
	}
	
	/**
	 * 获取列表
	 */
	function _getListPanel(jq) {
		return jq.combo("getPanel").find("div[role='comboboxListPanel']");
	}
	
	/**
	 * 获取搜索输入框
	 */
	function _getSearchText(jq) {
		return jq.combo("getPanel").find("div[role='comboboxSearchText'] input");
	}
	
	/**
	 * 方法，继承与combo
	 */
	$.fn.combobox.methods = $.extend({}, $.fn.combo.methods, {
		/**
		 * 获取值
		 */
		getValue : function() {
			return $(this).attr("realValue");
		},
		/**
		 * 设置值
		 */
		setValue : function(value) {
			var jq = $(this);
			return jq.each(function() {
				_select(jq, value);
			});
		},
		/**
		 * 获取panle
		 */
		getPanel : function() {
			return _getPanel($(this));
		},
		/**
		 * 获取下拉框
		 */
		getListPanel : function() {
			return _getListPanel($(this));
		}
	});
	
	/**
	 * 事件，继承与combo
	 */
	$.fn.combobox.event = $.extend({}, $.fn.combo.event, {
		/**
		 * 当选择一行触发
		 */
		onChange : function(value, rowData) {},
		/**
		 * 当从后端加载完数据后触发
		 */
		onLoadSuccess : function(data) {}
	});
	
	/**
	 * 属性，继承与combo
	 */
	$.fn.combobox.defaults = $.extend({}, $.fn.combobox.event, $.fn.combo.defaults, {
		valueField : "value",							//value值的字段名
		textField : "text",								//显示的文字的字段名
		panelHeight : "auto", 							//下拉框高度
		panelWidth : undefined,							//下拉框宽度
		className : undefined,							//样式
		value : null,									//选中的值
		url : undefined,								//请求的url
		method : "POST",								//请求方式
		queryParams : {},								//请求的参数
		dataType : "json",								//返回的数据类型
		data : [],										//需要显示的下拉数据（优先使用url请求的返回值）
		formatter : null,								//对行进行特殊处理(function(rowData, rowIndex) {})
		loadFilter : function(data) {},					//对url请求返回的数据做处理
		maxlength : 0,									//最多显示的行数
		forceRemote : false								//是否强制从远端获取数据（为true，则每次搜索都要从远端获取数据）
	});
})(jQuery);