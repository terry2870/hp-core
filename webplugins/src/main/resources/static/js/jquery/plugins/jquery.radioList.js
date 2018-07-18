/**
 * 显示一单选框组
 * 作者：黄平
 * 日期：2016-08-13
 */
(function($) {
	$.fn.radioList = function(options, param) {
		var self = this;
		
		if (typeof (options) == "string") {
			var method = $.fn.radioList.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.radioList.defaults, options);
			$(self).data("radioList", opt)
			_create(self);
		});
	};
	
	/**
	 * 创建
	 * @param jq
	 * @returns
	 */
	function _create(jq) {
		var opt = jq.data("radioList");
		if (opt.onBeforeLoad.call(jq) === false) {
			return;
		}
		if (opt.url) {
			$.post(opt.url, opt.queryParams, function(data) {
				if (opt.loadFilter) {
					data = opt.loadFilter(data);
				}
				opt.data = data;
				_showRadio(jq);
			}, opt.dataType);
		} else {
			_showRadio(jq);
		}
	}
	
	/**
	 * 显示
	 * @param jq
	 * @returns
	 */
	function _showRadio(jq) {
		var opt = jq.data("radioList");
		if (!opt.data || opt.data.length == 0) {
			jq.loadSuccess();
			return;
		}
		$(opt.data).each(function(index, item) {
			
			var radio = $("<input type='radio'>").data("radioList-item", item).click(function() {
				opt.onClick.call(this, item[opt.valueField], item);
			}).attr({
				id : opt.name + "_" + index,
				name : opt.name,
				value : item[opt.valueField]
			}).css({
				"margin-left" : opt.spaceWidth
			}).prop("checked", (item[opt.checkedField] || item[opt.valueField] == opt.defaultValue) ? true : false);
			jq.append(radio)
			/*if (item[opt.checkedField] || item[opt.valueField] == opt.defaultValue) {
				radio.click();
			}*/
			var label = $("<label>").attr("for", opt.name + "_" + index).html(item[opt.textField]);
			jq.append(label);
			
			if (opt.rowMaxSize > 0 && (index > 0 && index % (opt.rowMaxSize - 1) == 0)) {
				jq.append("<br>");
			}
		});
		
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
		jq.loadSuccess();
	}
	
	/**
	 * 获取选中的值
	 * @param jq
	 * @returns
	 */
	function _getCheckedValue(jq) {
		return _getCheckedObj(jq).val();
	}
	
	/**
	 * 获取选中的文本
	 * @param jq
	 * @returns
	 */
	function _getCheckedText(jq) {
		var opt = jq.data("radioList");
		return _getCheckedObj(jq).data("radioList-item")[opt.textField];
	}
	
	/**
	 * 获取选中的对象
	 * @param jq
	 * @returns
	 */
	function _getCheckedObj(jq) {
		return jq.find("input[type='radio']:checked");
	}
	
	/**
	 * 根据值，获取对象
	 * @param jq
	 * @returns
	 */
	function _getRadio(jq, value) {
		return jq.find("input[type='radio'][value='"+ value +"']");
	}
	
	/**
	 * 设置选中
	 * @param jq
	 * @param value
	 * @returns
	 */
	function _setChecked(jq, value) {
		_getRadio(jq, value).prop("checked", true);
	}
	
	/**
	 * 获取所有的对象
	 * @param jq
	 * @returns
	 */
	function _getAllRadio(jq) {
		return jq.find("input[type='radio']");
	}
	
	/**
	 * 获取所有的对象的数据
	 * @param jq
	 * @returns
	 */
	function _getAllRadioData(jq) {
		var radio = _getAllRadio(jq);
		if (!radio) {
			return [];
		}
		var arr = [];
		for (var i = 0; i < radio.length; i++) {
			arr.push($(radio[i]).data("radioList-item"));
		}
		return arr;
	}
	
	/**
	 * 点击
	 * @param jq
	 * @param value
	 * @returns
	 */
	function _click(jq, value) {
		if (!value) {
			return;
		}
		var obj = _getRadio(jq, value);
		obj.click();
	}
	
	
	$.fn.radioList.methods = {
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
		 * 获取选中的文本
		 * @returns
		 */
		getCheckedText : function() {
			return _getCheckedText(this);
		},
		/**
		 * 获取选中的值
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
		 * 根据值，获取对象
		 */
		getRadio : function(value) {
			return _getRadio(this, value);
		},
		/**
		 * 获取所有的对象
		 */
		getAllRadio : function() {
			return _getAllRadio(this);
		},
		/**
		 * 获取所有的对象的数据
		 */
		getAllRadioData : function() {
			return _getAllRadioData(this);
		},
		/**
		 * 选择
		 */
		click : function(value) {
			var jq = $(this);
			return jq.each(function() {
				_click(jq, value);
			});
		}
	};
	$.fn.radioList.event = {
		onBeforeLoad : function() {},
		onLoadSuccess : function(data) {},
		onClick : function(value, record) {}
	};
	$.fn.radioList.defaults = $.extend({}, $.fn.radioList.event, {
		textField : "text",
		valueField : "value",
		checkedField : "checked",
		name : "",
		url : null,
		defaultValue : null,
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