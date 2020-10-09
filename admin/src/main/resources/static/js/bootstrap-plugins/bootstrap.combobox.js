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
	let SEARCH_TEXT_ROLE = "searchtext";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				throw new Error(pluginName + " 没有此方法。");
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
		jq.attr(PLUGIN_NAME, pluginName);
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
		let searchLi = $("<li>").css({
			padding : ".4rem 0.7rem"
		}).addClass("list-group-item list-group-item-action").appendTo(panelSelector);
		let searchText = $("<input>").attr("role", SEARCH_TEXT_ROLE).addClass("form-control").appendTo(searchLi);
		
		searchText.keyup(function() {
			let val = this.value;
			let panelBody = jq.combo("getPanel").card("body");
			let allLis = panelBody.find("li");
			if (val == "") {
				//当输入内容为空时，则显示所有
				allLis.show();
				return;
			}
			
			for (let i = 1; i < allLis.length; i++) {
				let liText = $(allLis[i]).data(LIST_DATA)[opt.textField];
				if (liText.toString().indexOf(val) >= 0) {
					$(allLis[i]).show();
				} else {
					$(allLis[i]).hide();
				}
			}
			$(allLis).each(function(index, item) {
				
			});
		});
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
			readonly : true,
			panelSelector : panelSelector,
			icons : [{
				icon : "x",
				onClick : function() {
					_clear(jq);
				}
			}, {
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

		/*
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

			//输入框中值转换为对象
			let valueArr = _toComboboxDataObjectFromField(opt, val.split(opt.separator), opt.textField);
			if (!valueArr || valueArr.length == 0) {
				jq.combo("getPanel").card("body").find("li.active").removeClass("active");
			} else {
				_selectValues(jq, valueArr);
			}
		});*/

		//设置默认值
		if (opt.value !== undefined && opt.value !== null) {
			_selectValues(jq, opt.value);
		}

		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.dataList);
		}

		$.tools.markSuccess(jq, pluginName);
	}

	/**
	 * 点击选项
	 * @param {*} jq 
	 * @param {*} target 
	 */
	function _clickItem(jq, target) {
		target = $(target);
		let opt = jq.data(pluginName);
		let panel = jq.combo("getPanel");
		let panelBody = panel.card("body");
		let val = null;
		if (opt.multiple === false) {
			//不允许多选
			panelBody.find("li.active").removeClass("active");
			target.addClass("active");
			val = target.data(LIST_DATA);
			panel.card("close");
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
		//输入框中的值
		let textValue = _getSelectDatas(jq);
		if (!textValue) {
			return [];
		}
		let opt = jq.data(pluginName);
		let textArr = textValue.split(opt.separator);

		//下拉框中所有的值
		let allRowData = _getAllData(jq);

		//转换成实际的对象
		let arr = _toComboboxDataObjectFromField(opt, textArr, opt.textField);
		return arr;
	}

	/**
	 * 获取下拉框所有的值
	 * @param {*} jq 
	 */
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
		let values2 = null;
		if (opt.multiple === false) {
			values2 = [values];
		} else {
			values2 = values;
		}
		//把输入的values转成对象形式
		let newValue = [];
		$(values2).each(function(index, item) {
			let obj = _toComboboxDataObjectFromField(opt, item, opt.valueField);
			if (obj != null) {
				newValue.push(obj);
			}
		});

		let lis = panelBody.find("li");
		//遍历，把存在的选项，高亮
		$(lis).each(function(index, item) {
			let itemData = $(item).data(LIST_DATA);
			let checkData = _getByField(newValue, itemData, opt.valueField);
			if (checkData != null) {
				$(item).addClass("active");
			}
		});

		if (opt.onSelect && newValue.length == 1) {
			opt.onSelect.call(jq, newValue[0][opt.valueField], newValue[0]);
		}
		_displayTextAndValue(jq, newValue);
	}

	/**
	 * 变为combobox的数据格式
	 * @param {*} opt 
	 * @param {*} value 
	 */
	function _toComboboxDataObjectFromField(opt, value, field) {
		if ($.type(value) == "object") {
			return value;
		} else if ($.type(value) == "array") {
			if (value.length == 0) {
				return [];
			}
			let newArr = [];
			for (let i = 0; i < value.length; i++) {
				let o = _toComboboxDataObjectFromField(opt, value[i], field);
				if (o != null) {
					newArr.push(o);
				}
			}
			return newArr;
		} else {
			//获取所有的数据，遍历
			for (let i = 0; i < opt.dataList.length; i++) {
				let obj = _getByField(opt.dataList, value, field);
				if (obj != null) {
					return obj;
				}
			}
			return null;
		}
	}

	/**
	 * 查询该值是否在数据中
	 * @param {*} arr 
	 * @param {*} value 
	 * @param {*} field 
	 */
	function _getByField(arr, value, field) {
		if (arr.length == 0 || value === undefined || value == null) {
			return null;
		}
		let val = value;
		if ($.type(value) == "object") {
			val = value[field];
		}
		for (let i = 0; i < arr.length; i++) {
			if (val === "") {
				if (arr[i][field] === "") {
					return arr[i];
				}
			} else if (val == arr[i][field]) {
				return arr[i];
			}
		}
		return null;
	}

	/**
	 * 清空
	 * @param {*} jq 
	 */
	function _clear(jq) {
		jq.combo("clear");
		jq.combo("getPanel").card("body").find("li.active").removeClass("active");
	}

	//方法
	$.fn[pluginName].methods = $.extend({}, $.fn.combo.methods, {
		values : function(values) {
			let self = this;
			if (values === undefined) {
				let val = _getValues(self);
				if (val == null) {
					return null;
				}
				let arr = [];
				let opt = self.data(pluginName);
				$(val).each(function(index, item) {
					arr.push(item[opt.valueField]);
				});
				return arr;
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
				let opt = self.data(pluginName);
				return getValue[0][opt.valueField];
			} else {
				return $(this).each(function() {
					_selectValues(self, [value]);
				});
			}
		},
		/**
		 * 清空
		 */
		clear : function() {
			let self = this;
			return $(this).each(function() {
				_clear(self);
			});
		},
		/**
		 * 获取自己
		 */
		get : function() {
			return $(this).each(function() {
			});
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