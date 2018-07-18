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
			return;
		}
		var p;
		$(opt.data).each(function(index, item) {
			if (index == 0 || (opt.rowMaxSize > 0 && index % opt.rowMaxSize == 0)) {
				p = $("<div>").appendTo(jq);
			}
			p.append($("<input type='radio'>").click(function() {
				opt.onClick.call(this, item[opt.valueField], item);
			}).attr({
				id : opt.name + "_" + index,
				name : opt.name,
				value : item[opt.valueField]
			}).css({
				"margin-left" : opt.spaceWidth
			}).prop("checked", item[opt.checkedField] == true ? true : false));
			p.append($("<label>").attr("for", opt.name + "_" + index).html(item[opt.textField]));
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
	function _getValue(jq) {
		return _getChecked(jq).val();
	}
	
	/**
	 * 获取选中的对象
	 * @param jq
	 * @returns
	 */
	function _getChecked(jq) {
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
	function _setValue(jq, value) {
		_getRadio(jq, value).prop("checked", true);
	}
	
	
	$.fn.radioList.methods = {
		/**
		 * 获取选中的对象
		 * @returns
		 */
		getChecked : function() {
			return _getChecked(this);
		},
		/**
		 * 获取选中的值
		 * @returns
		 */
		getValue : function() {
			return _getValue(this);
		},
		/**
		 * 获取选中的值
		 * @param value
		 * @returns
		 */
		setValue : function(value) {
			var self = this;
			return this.each(function() {
				_setValue(self, value);
			});
		},
		/**
		 * 根据值，获取对象
		 */
		getRadio : function(value) {
			return _getRadio(this, value);
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