/**
 * 显示一组复选框
 * 作者：黄平
 * 日期：2016-08-13
 */
(function($) {
	$.fn.checkboxList = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.checkboxList.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.checkboxList.defaults, options);
			$(self).data("checkboxList", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		var opt = jq.data("checkboxList");
		if (opt.onBeforeLoad.call(jq) === false) {
			return;
		}
		if (opt.url) {
			$.post(opt.url, opt.queryParams, function(data) {
				if (opt.loadFilter) {
					data = opt.loadFilter(data);
				}
				opt.data = data;
				_showCheckbox(jq);
			}, opt.dataType);
		} else {
			_showCheckbox(jq);
		}
	}
	
	/**
	 * 显示
	 * @param jq
	 * @returns
	 */
	function _showCheckbox(jq) {
		var opt = jq.data("checkboxList");
		if (!opt.data || opt.data.length == 0) {
			jq.loadSuccess();
			return;
		}
		$(opt.data).each(function(index, item) {
			jq.append($("<input type='checkbox'>").data("checkboxList-item", item).click(function() {
				if (this.checked) {
					opt.onCheck.call(this, item[opt.valueField], item);
				} else {
					opt.onUnCheck.call(this, item[opt.valueField], item);
				}
			}).attr({
				id : opt.name + "_" + index,
				name : opt.name,
				value : item[opt.valueField]
			}).css({
				"margin-left" : opt.spaceWidth
			}).prop("checked", item[opt.checkedField] == true ? true : false));
			jq.append($("<label>").attr("for", opt.name + "_" + index).html(item[opt.textField]));
			
			if (index != 0 && opt.rowMaxSize > 0 && index % (opt.rowMaxSize - 1) == 0) {
				jq.append("<br>");
			}
			
		});
		
		//设置选中
		if (opt.defaultValue) {
			
		}
		
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
		jq.loadSuccess();
	}
	
	/**
	 * 获取选中的对象
	 * @param jq
	 * @returns
	 */
	function _getCheckedObj(jq) {
		var chkArr = jq.find("input[type='checkbox']:checked");
		if (!chkArr || chkArr.length == 0) {
			return [];
		}
		return chkArr;
	}
	
	/**
	 * 获取复选框对象
	 * @param jq
	 * @param value
	 * @returns
	 */
	function _getCheckbox(jq, value) {
		var arr = [];
		if ($.type(value) == "string") {
			value = value.split(",");
		}
		$(jq.find("input[type='checkbox']")).each(function(index, item) {
			if ($.inArray($(item).val(), value)) {
				arr.push(item);
			}
		});
		return arr;
	}
	
	/**
	 * 获取选中的值
	 * @param jq
	 * @returns
	 */
	function _getCheckedValue(jq) {
		var checkedArr = _getCheckedObj(jq);
		var arr = [];
		$(checkedArr).each(function(index, item) {
			arr.push($(item).val());
		});
		return arr;
	}
	
	/**
	 * 设置选中
	 * @param jq
	 * @param value
	 * @returns
	 */
	function _setChecked(jq, value) {
		if ($.type(value) == "string") {
			value = value.split(",");
		}
		jq.find("input[type='checkbox']").prop("checked", false);
		$(value).each(function(index, item) {
			jq.find("input[type='checkbox'][value='"+ item +"']").prop("checked", true);
		});
	}
	
	/**
	 * 设置选中
	 * @param jq
	 * @param value
	 * @returns
	 */
	function _addChecked(jq, value) {
		if ($.type(value) == "string") {
			value = value.split(",");
		}
		$(value).each(function(index, item) {
			jq.find("input[type='checkbox'][value='"+ item +"']").prop("checked", true);
		});
	}
	
	/**
	 * 清除选中
	 * @param jq
	 * @returns
	 */
	function _clearChecked(jq) {
		jq.find("input[type='checkbox']").prop("checked", false);
	}
	
	
	$.fn.checkboxList.methods = {
		/**
		 * 获取选中的对象
		 * @returns
		 */
		getCheckedObj : function() {
			return _getCheckedObj(this);
		},
		/**
		 * 获取选中的值
		 * @returns
		 */
		getCheckedValue : function() {
			return _getCheckedValue(this);
		},
		/**
		 * 设置选中
		 * @param value
		 * @returns
		 */
		setChecked : function(value) {
			var self = this;
			return this.each(function() {
				_setChecked(self, value);
			});
		},
		/**
		 * 增加选中
		 * @param value
		 * @returns
		 */
		addChecked : function(value) {
			var self = this;
			return this.each(function() {
				_addChecked(self, value);
			});
		},
		/**
		 * 根据值，获取对象
		 */
		getCheckbox : function(value) {
			return _getCheckbox(this, value);
		},
		/**
		 * 清除选中
		 */
		clearChecked : function() {
			var jq = $(this);
			return jq.each(function() {
				_clearChecked(jq);
			});
		}
	};
	$.fn.checkboxList.event = {
		onBeforeLoad : function() {},
		onLoadSuccess : function(data) {},
		onCheck : function(value, record) {},
		onUnCheck : function(value, record) {}
	};
	$.fn.checkboxList.defaults = $.extend({}, $.fn.checkboxList.event, {
		textField : "text",
		valueField : "value",
		checkedField : "checked",
		defaultValue : null,
		name : "",
		url : null,
		queryParams : {},
		loadFilter : function(data) {
			return data;
		},
		dataType : "json",
		spaceWidth : 20,						//间隔
		rowMaxSize : 0,							//每行最多个数
		data : []								//数据，如果数据中有属性为checked=true，则选中该radio
	});
})(jQuery);