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
	let LIST_DATA = "listdata";
	let SELECT_DATA = "selectdata";
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
						opt.dataList = opt.loadFilter(data);
					}
					
					_createCombobox(jq, opt);
				}
			});
		} else {
			_createCombobox(jq, opt);
			//_createCombo(self);
		}
	}

	/**
	 * 
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createCombobox(jq, opt) {
		let panelSelector = $("<ul>").addClass("list-group");
		$(opt.dataList).each(function(index, item) {
			let li = $("<li>").data(LIST_DATA, item).addClass("list-group-item list-group-item-action").html(item[opt.textField]).appendTo(panelSelector);
			li.css({
				cursor : "pointer",
				padding : ".4rem 0.7rem"
			});
			li.click(function() {
				_clickItem(jq, this);
			});
		});
		let currOnAfterShowPanel = opt.onAfterShowPanel;
		jq.combo($.extend({}, opt, {
			panelSelector : panelSelector,
			icons : [{
				icon : "caret-down",
				onClick : function() {
					jq.combo("showPanel");
				}
			}],
			onAfterShowPanel : function() {
				currOnAfterShowPanel.call(jq);
				jq.combo("getPanel").card("body").find("li").show();
			}
		}));

		let panelBody = jq.combo("getPanel").card("body");
		let allLis = panelBody.find("li");
		//可以搜索
		jq.keyup(function() {
			let val = this.value;
			if (val == "") {
				//当输入内容为空时，则显示所有
				allLis.show();
				return;
			}
			
			$(allLis).each(function(index, item) {
				let liText = $(item).data(LIST_DATA)[opt.textField];
				if (liText.toString().indexOf(val) >= 0) {
					$(item).show();
				} else {
					$(item).hide();
				}
			});
		});

		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.dataList);
		}
	}

	/**
	 * 点击选项
	 * @param {*} jq 
	 * @param {*} target 
	 */
	function _clickItem(jq, target) {
		target = $(target);
		let opt = jq.data(pluginName);
		let panelBody = jq.combo("getPanel").card("body");
		let val = null;
		if (opt.multiple === false) {
			//不允许多选
			panelBody.find("li.active").removeClass("active");
			target.addClass("active");
			val = target.data(LIST_DATA);
			if (opt.onSelect) {
				opt.onSelect.call(jq, val[opt.valueField], val);
			}
		} else {
			target.toggleClass("active");
			let lis = panelBody.find("li.active");
			let arr = [];
			$(lis).each(function(index, item) {
				arr.push($(item).data(LIST_DATA));
			});
			val = arr;
		}

		_displayTextAndValue(jq, val);
	}

	/**
	 * 设置值和显示文字
	 * @param {*} jq 
	 * @param {*} data 
	 */
	function _displayTextAndValue(jq, data) {
		jq.data(SELECT_DATA, data);
		_displayText(jq);

		//
	}

	/**
	 * 显示实际的文字
	 * @param {*} jq 
	 */
	function _displayText(jq) {
		let opt = jq.data(pluginName);
		let datas = jq.data(SELECT_DATA);
		let arr = [];
		
		$(datas).each(function(index, item) {
			arr.push(item[opt.textField]);
		});
		jq.textbox("value", arr.join(opt.separator));

		//触发验证
		jq.validatebox("validate");
	}

	/**
	 * 获取选中的数据
	 * @param {*} jq 
	 */
	function _getSelectDatas(jq) {
		return jq.val();
	}

	/**
	 * 获取值
	 * @param {*} jq 
	 */
	function _getValues(jq) {
		let textValue = _getSelectDatas(jq);
		if (!textValue) {
			return [];
		}
		let opt = jq.data(pluginName);
		let textArr = textValue.split(opt.textField);
		let allRowData = _getAllData(jq);
		let arr = [];
		$(textArr).each(function(index, item) {
			let checkText = _checkText(opt, allRowData, item);
			if (checkText !== false) {
				arr.push(checkText);
			}
		});
		return arr;
	}

	function _checkText(opt, arr, text) {
		if (!text) {
			return false;
		}
		let val = _toObject(opt, text);
		for (let i = 0; i < arr.length; i++) {
			let v2 = _toObject(opt, arr[i]);
			if (v2[opt.textField] == val[opt.textField]) {
				return v2[opt.valueField];
			}
		}
		return false;
	}

	function _getAllData(jq) {
		let opt = jq.data(pluginName);
		return opt.dataList;
	}

	/**
	 * 选中值
	 * @param {*} jq 
	 * @param {*} values 
	 */
	function _selectValues(jq, values) {
		let panel = jq.combo("getPanel");
		let panelBody = panel.card("body");
		panelBody.find("li.active").removeClass("active");
		if (!values || values.length == 0) {
			jq.data(SELECT_DATA, []);
			return;
		}

		let opt = jq.data(pluginName);
		if (opt.multiple === false) {
			values = values[0];
		}
		//把输入的values转成对象形式
		let newValue = [];
		$(values).each(function(index, item) {
			newValue.push(_toObject(opt, item));
		});

		let lis = panelBody.find("li");
		
		$(lis).each(function(index, item) {
			let d = $(item).data(LIST_DATA);
			if (_checkExist(opt, newValue, d)) {
				$(item).addClass("active");
			}
		});

		if (opt.onSelect && newValue.length == 1) {
			opt.onSelect.call(jq, newValue[opt.valueField], newValue);
		}
		_displayTextAndValue(jq, newValue);
	}

	function _toObject(opt, value) {
		if ($.type(value) == "object") {
			return value;
		}
		return {
			[opt.valueField] : value,
			[opt.textField] : value
		};
	}

	/**
	 * 检查值是否存在数组中
	 * @param {*} opt 
	 * @param {*} arr 
	 * @param {*} value 
	 */
	function _checkExist(opt, arr, value) {
		if (!value) {
			return false;
		}
		let val = _toObject(opt, value);
		for (let i = 0; i < arr.length; i++) {
			let v2 = _toObject(opt, arr[i]);
			if (v2[opt.valueField] == val[opt.valueField]) {
				return true;
			}
		}
		return false;
	}

	//方法
	$.fn[pluginName].methods = $.extend({}, $.fn.combo.methods, {
		values : function(values) {
			let self = this;
			if (values === undefined) {
				return _getValues(self);
			} else {
				return $(this).each(function() {
					_selectValues(self, values);
				});
			}
		},
		value : function(value) {
			let self = this;
			if (value === undefined) {
				let getValue = _getValues(self);
				if (!getValue || getValue.length == 0) {
					return "";
				}
				return getValue[0];
			} else {
				return $(this).each(function() {
					_selectValues(self, [value]);
				});
			}
		}
	});
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当选择一行触发
		 * 只有单选的时候才有这个事件
		 * @param {*} value 
		 * @param {*} rowData 
		 */
		onSelect : function(value, rowData) {},
		/**
		 * 当从后端加载完数据后触发
		 * @param {*} data 
		 */
		onLoadSuccess : function(data) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, $.fn.combo.defaults, {
		valueField : "value",							//value值的字段名
		textField : "text",								//显示的文字的字段名
		className : undefined,							//样式
		value : null,									//选中的值
		url : undefined,								//请求的url
		method : "POST",								//请求方式
		queryParams : {},								//请求的参数
		dataType : "json",								//返回的数据类型
		dataList : [],										//需要显示的下拉数据（优先使用url请求的返回值）
		formatter : null,								//对行进行特殊处理(function(rowData, rowIndex) {})
		loadFilter : function(data) {},					//对url请求返回的数据做处理
		forceRemote : false,								//是否强制从远端获取数据（为true，则每次搜索都要从远端获取数据）
		separator : ","									//text文字分隔符
	});
})(jQuery);