/**
 * 显示一个下拉的表格
 * 作者：黄平
 * 日期：2016-07-06
 */
(function($) {
	$.fn.combotable = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.combotable.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.combotable.defaults, options, {
				editable : false
			});
			self.data("combotable", opt);
			_createCombotable(self);
		});
	};

	/**
	 * 创建
	 */
	function _createCombotable(jq) {
		var opt = jq.data("combotable");
		var table = $("<div role=\"combotable_table\">").appendTo($("body"));
		table.table($.extend({}, opt.table, {
			onLoadSuccess : function(data) {
				if (opt.table.onLoadSuccess) {
					opt.table.onLoadSuccess(data);
				}
				
				//显示下拉
				jq.combo($.extend({}, opt, {
					panelSelector : table
				}));
				
				//设置初始值
				_setValue(jq, opt.value);
				
				//设置已经加载完成
				jq.attr("loadStatus", "1");
			},
			onCheck : function(rowIndex, rowData) {
				if (opt.table.onCheck) {
					opt.table.onCheck(rowIndex, rowData);
				}
				jq.val(_getText(jq));
			},
			onUnCheck : function(rowIndex, rowData) {
				if (opt.table.onUnCheck) {
					opt.table.onUnCheck(rowIndex, rowData);
				}
				jq.val(_getText(jq));
			},
			singleSelect : !opt.multiple
		}));
	}
	
	/**
	 * 获取表格对象
	 */
	function _getTable(jq) {
		var panel = jq.combo("getPanel");
		return panel.find("div[role='combotable_table']");
	}
	
	/**
	 * 获取值
	 */
	function _getValue(jq) {
		var opt = jq.data("combotable");
		var table = _getTable(jq);
		var data = table.table("getChecked");
		var arr = [];
		for (var i = 0; i < data.length; i++) {
			arr.push(data[i][opt.valueField]);
		}
		return arr;
	}
	
	/**
	 * 设置值
	 */
	function _setValue(jq, value) {
		if (!value) {
			return;
		}
		var opt = jq.data("combotable");
		var table = _getTable(jq);
		var tableData = table.table("getData");
		
		//设置初始值
		if ($.type(value) == "string") {
			value = value.split(",");
		}
		for (var i = 0; i < value.length; i++) {
			for (var j = 0; j < tableData.length; j++) {
				if (value[i] == tableData[j][opt.valueField]) {
					table.table("checkRow", j);
					break;
				}
			}
		}
	}
	
	/**
	 * 获取名称
	 */
	function _getText(jq) {
		var opt = jq.data("combotable");
		var table = _getTable(jq);
		var data = table.table("getChecked");
		var arr = [];
		for (var i = 0; i < data.length; i++) {
			arr.push(data[i][opt.textField]);
		}
		return arr;
	}
	
	/**
	 * 方法，继承与combo
	 */
	$.fn.combotable.methods = $.extend({}, $.fn.combo.methods, {
		/**
		 * 获取表格对象
		 */
		getTable : function() {
			return _getTable(this);
		},
		/**
		 * 获取值
		 */
		getValue : function() {
			var jq = $(this);
			return _getValue(jq);
		},
		/**
		 * 设置值
		 */
		setValue : function(value) {
			var jq = $(this);
			return jq.each(function() {
				return _setValue(jq, value);
			});
		},
		/**
		 * 获取显示内容
		 */
		getText : function() {
			return _getText(this);
		}
	});
	
	/**
	 * 事件，继承与combo
	 */
	$.fn.combotable.event = $.extend({}, $.fn.combo.event, {
		
	});
	
	/**
	 * 属性，继承与combo
	 */
	$.fn.combotable.defaults = $.extend({}, $.fn.combotable.event, $.fn.combo.defaults, {
		valueField : "value",							//value值的字段名
		textField : "text",								//显示的文字的字段名
		className : undefined,							//样式
		value : undefined,								//选中的值
		table : {},										//table属性
		multiple : false								//是否可以多选
	});
})(jQuery);