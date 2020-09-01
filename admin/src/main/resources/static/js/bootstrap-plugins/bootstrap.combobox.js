/**
 * 下拉框
 * 作者：黄平
 * 日期：2020-08-21
 * 继承与：combo
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "combobox";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method) {
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create(self, opt);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _create(jq, opt) {
		if (opt.className) {
			jq.addClass(opt.className);
		}

		_createPanelContent(jq, opt);
	}

	/**
	 * 创建panel内容
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createPanelContent(jq, opt) {
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
	}

	//方法
	$.fn[pluginName].methods = {

	};
	
	//事件
	$.fn[pluginName].events = {
		
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, $.fn.combo.defaults, {
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