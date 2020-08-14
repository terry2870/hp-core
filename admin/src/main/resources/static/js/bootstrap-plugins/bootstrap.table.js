/**
 * 一个表格插件
 * 作者：黄平
 * 日期：2020-05-24
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "table";
	let CHECK_ALL_ROLE_NAME = "check_all";
	let CHECK_BOX_ROLE_NAME = "check_row";
	let ROW_DATA_NAME = "table_row_data";
	let ROW_INDEX_NAME = "row_index";
	let LAST_QUERY_PARAM = "last_query_param";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			createTable(self);
		});
	};
	
	/**
	 * 生成表格
	 * @param {*} jq 
	 */
	function createTable(jq) {
		let opt = jq.data(pluginName);
		
		let header = opt.header;
		if (!header) {
			header = {
				html : opt.title,
				style : {
					color : "white"
				}
			};
		};

		let body = _initTable(jq, opt);
		let footer = _initPagination(opt);
		footer = footer ? {
			html : footer
		} : null;
		jq.card($.extend({}, opt, {
			width : opt.width ? opt.width : "100%",
			height : opt.height ? opt.height : "100%",
			top : opt.top ? opt.top : 0,
			left : opt.left ? opt.left : 0,
			header : header,
			body : {
				html : body
			},
			footer : footer,
			closeable : false
		}));

		//生成数据
		_createRows(jq, opt);
	}

	/**
	 * 初始化分页
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _initPagination(opt) {
		if (opt.pagination !== true) {
			return null;
		}
		return $("<nav>");
	}

	/**
	 * 初始化table
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _initTable(jq, opt) {
		jq.empty();
		let table = $("<table>").addClass("table");
		//设置斑马纹
		if (opt.striped === true) {
			table.addClass("table-striped");
		}
		//设置鼠标悬停
		if (opt.hover === true) {
			table.addClass("table-hover");
		}
		//dark模式
		if (opt.dark === true) {
			table.addClass("table-dark");
		}
		//边框bordered
		if (opt.bordered === true) {
			table.addClass("table-bordered");
		}
		//sm
		if (opt.small === true) {
			table.addClass("table-sm");
		}

		//生成表头
		_createThead(jq, table, opt);

		//生成body
		_createTbody(table);
		return table;
	}

	/**
	 * 创建thead
	 * @param {*} jq
	 * @param {*} table 
	 * @param {*} opt 
	 */
	function _createThead(jq, table, opt) {
		if (!opt.columns || opt.columns.length == 0) {
			return;
		}
		let thead = $("<thead>").appendTo(table);
		let tr = $("<tr>").appendTo(thead);
		$(opt.columns).each(function(index, item) {
			let th = $("<th>").attr("scope", "col").appendTo(tr);
			th.css({
				"text-align" : item.align ? item.align : "left",
				width : item.width ? item.width : "auto"
			});
			if (item.checkbox === true) {
				//有复选框
				let checkbox = $("<input type='checkbox' role='"+ CHECK_ALL_ROLE_NAME +"'>").appendTo(th);
				checkbox.css({
					cursor : "pointer"
				});
				checkbox.click(function() {
					_checkAll(jq, this.checked);
				});
			} else {
				th.append(item.title);
			}
		});
	}

	/**
	 * 生成tbody
	 * @param {*} table 
	 */
	function _createTbody(table) {
		$("<tbody>").appendTo(table);
	}

	/**
	 * 生成数据
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createRows(jq, opt) {
		//生成行
		if (opt.url) {
			//从远端获取数据生成
			_getTableDataContentFromRemote(jq, opt, {}, {
				currentPage : 1,
				pageSize : opt.pageSize,
				sortName : opt.sortName,
				sortOrder : opt.sortOrder
			}, function(data) {
				let totalData = _getTotalAndData(data);
				_createPagination(jq, opt, totalData.total);
			});
		} else {
			//从本地数据生成
			_createTableFromData(jq, opt.data);
			let totalData = _getTotalAndData(data);
			_createPagination(jq, opt, totalData.total);
		}
	}

	/**
	 * 获取总条数和rows
	 * @param {*} data
	 */
	function _getTotalAndData(data) {
		let total = 0;
		let rows = [];
		if ($.type(data) === "object") {
			total = data.total;
			rows = data.rows;
		} else {
			total = data ? data.length : 0;
		}
		return {
			rows : rows,
			total : total
		};
	}
	
	/**
	 * 全选
	 * @param {*} jq 
	 * @param {*} checked 
	 */
	function _checkAll(jq, checked) {
		let opt = jq.data(pluginName);
		//只能单选，该无效
		if (opt.singleSelect === true) {
			this.checked = false;
			return;
		}
		
		_getTbody(jq).find("input[role='"+ CHECK_BOX_ROLE_NAME +"']").prop("checked", checked);
	}

	/**
	 * 生成分页
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} total 
	 * @param {*} currentPage 
	 */
	function _createPagination(jq, opt, total, currentPage) {
		if (opt.pagination !== true) {
			return null;
		}
		
		let page = _getPagination(jq);
		page.empty();
		page.pagination({
			total : total ? total : opt.total,
			pageSize : opt.pageSize,
			currentPage : currentPage ? currentPage : 1,
			onSelectPage : function(currentPage, pageSize) {
				let lastQueryParam = jq.data(LAST_QUERY_PARAM);
				_getTableDataContentFromRemote(jq, opt, lastQueryParam, {
					currentPage : currentPage
				});
			}
		});
	}

	/**
	 * 从远端获取数据生成table
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} otherParam 
	 * @param {*} pageParams 
	 * @param {*} callback 
	 */
	function _getTableDataContentFromRemote(jq, opt, otherParam, pageParams, callback) {
		if (!opt.url) {
			return;
		}
		
		if (opt.onBeforeLoad) {
			let onBeforeLoadResult = opt.onBeforeLoad();
			if (onBeforeLoadResult === false) {
				//不在加载
				return;
			}
		}
		
		if (!pageParams) {
			pageParams = {};
		}
		
		let lastQueryParam = $.extend({}, otherParam, pageParams);
		jq.data(LAST_QUERY_PARAM, lastQueryParam);
		
		let params = $.extend({}, opt.queryParams, lastQueryParam, {
			page : pageParams.currentPage ? pageParams.currentPage : opt.currentPage,
			rows : pageParams.pageSize ? pageParams.pageSize : opt.pageSize,
			sort : pageParams.sortName ? pageParams.sortName : opt.sortName,
			order : pageParams.sortOrder ? pageParams.sortOrder : opt.sortOrder
		});
		
		$.post(opt.url, params, function(data) {
			if (!data) {
				return;
			}
			if (opt.loadFilter) {
				data = opt.loadFilter(data);
			}

			_createTableFromData(jq, data);
			
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess(data);
			}
			
			if (callback) {
				callback(data);
			}
		}, opt.dataType);
	}

	/**
	 * 通过数据，创建table
	 * @param {*} jq 
	 * @param {*} data 
	 */
	function _createTableFromData(jq, data) {
		if (!data) {
			return;
		}
		let total = 0;
		let dataList = [];
		if ($.type(data) === "object") {
			total = data.total;
			dataList = data.rows;
		} else if ($.type(data) === "array") {
			total = data.length;
			dataList = data;
		}
		
		let table = _getTable(jq);
		let opt = jq.data(pluginName);
		let tbody = _getTbody(jq);
		tbody.empty();
		for (let i = 0; i < dataList.length; i++) {
			let row = dataList[i];
			if (opt.formatterRow) {
				let formatterRowResult = opt.formatterRow(i, row);
				if (formatterRowResult) {
					tbody.append(formatterRowResult);
					continue;
				}
			}
			let tr = $("<tr>").data(ROW_DATA_NAME, row).attr(ROW_INDEX_NAME, i).appendTo(tbody);

			if (opt.rowStyler) {
				let rowStylerResult = opt.rowStyler(i, row);
				if (rowStylerResult) {
					if ($.type(rowStylerResult) === "object") {
						tr.css(rowStylerResult);
					} else if ($.type(rowStylerResult) === "string") {
						tr.addClass(rowStylerResult);
					}
				}
			}

			if (opt.onClickRow) {
				tr.click(function() {
					opt.onClickRow.call(tr.get(0), i, row);
				});
			}
			for (let j = 0; j < opt.columns.length; j++) {
				let column = opt.columns[j];
				let td = $("<td>").appendTo(tr);
				td.css({
					"text-align": column.align ? column.align : "left"
				});
				if (column.checkbox === true) {
					//有复选框
					let chk = $("<input type='checkbox' role='"+ CHECK_BOX_ROLE_NAME +"'>").appendTo(td);
					chk.css({
						cursor : "pointer"
					});
					chk.click(function() {
						_ckickChk(jq, this);
					});
				} else {
					td.append(_getColumnData(column, row[column.field], row, i));
				}
			}
		}

		//生成分页
		//_createPagination(jq, opt, total);
	}

	/**
	 * 获取单元格数据
	 * @param {*} column 
	 * @param {*} value 
	 * @param {*} rowData 
	 * @param {*} rowIndex 
	 */
	function _getColumnData(column, value, rowData, rowIndex) {
		if (column.formatter) {
			return column.formatter(value, rowData, rowIndex);
		} else {
			return value;
		}
	}

	/**
	 * 点击复选框
	 * @param {*} jq 
	 * @param {*} target 
	 */
	function _ckickChk(jq, target) {
		let opt = jq.data(pluginName);
		let checked = $(target).get(0).checked;
		let row = $(target).parent().parent();
		let rowIndex = row.attr(ROW_INDEX_NAME);
		let rowData = row.data(ROW_DATA_NAME);
		//阻止冒泡
		if (event) {
			event.stopPropagation();
		}
		
		if (checked) {
			//选中
			if (opt.singleSelect === true) {
				//只能单选
				let checkRows = _getCheckedRows(jq);
				for (let i = 0; i < checkRows.length; i++) {
					if (i == rowIndex) {
						continue;
					}
					$(checkRows).find(">td>input[role='"+ CHECK_BOX_ROLE_NAME +"']").prop("checked", false);
				}
			} else {
				//可以多选
				let checkRows = _getCheckedRows(jq);
				let allRows = _getAllRows(jq);
				if (allRows.length !=0 && allRows.length == checkRows.length) {
					//把上面的全选按钮也勾选上
					_getTHead(jq).find("input[role='"+ CHECK_ALL_ROLE_NAME +"']").prop("checked", true);
				}
			}
			if (opt.onCheck) {
				opt.onCheck.call(target, rowIndex, rowData);
			}
		} else {
			//取消选中
			if (opt.singleSelect === true) {
				//只能单选
				//do nothing
			} else {
				//可以多选
				//把上面的全选按钮也取消
				_getTHead(jq).find("input[role='"+ CHECK_ALL_ROLE_NAME +"']").prop("checked", false);
			}
			if (opt.onUnCheck) {
				opt.onUnCheck.call(target, rowIndex, rowData);
			}
		}
	}

	/**
	 * 获取table
	 * @param {*} jq 
	 */
	function _getTable(jq) {
		return jq.find(">.card-body>table");
	}

	/**
	 * 获取tbody
	 * @param {*} jq 
	 */
	function _getTbody(jq) {
		return _getTable(jq).find(">tbody");
	}

	/**
	 * 获取thead
	 * @param {*} jq 
	 */
	function _getTHead(jq) {
		return _getTable(jq).find(">thead");
	}

	/**
	 * 获取所有的行
	 * @param {*} jq 
	 */
	function _getAllRows(jq) {
		return _getTbody(jq).find(">tr");
	}

	/**
	 * 获取一行
	 * @param {*} jq
	 * @param {*} index 
	 */
	function _getRow(jq, index) {
		let rows = _getAllRows(jq);
		if (!rows || rows.length == 0) {
			return null;
		}
		return rows[index];
	}

	/**
	 * 获取所有行的数据
	 * @param {*} jq 
	 */
	function _getAllData(jq) {
		let rows = _getAllRows(jq);
		if (!rows || rows.length == 0) {
			return [];
		}

		let arr = [];
		for (let i = 0; i < rows.length; i++) {
			arr.push($(rows[i]).data(ROW_DATA_NAME));
		}
		return arr;
	}

	/**
	 * 获取一行数据
	 * @param {*} jq 
	 * @param {*} index 
	 */
	function _getData(jq, index) {
		let rows = _getAllData(jq);
		if (!rows || rows.length == 0) {
			return null;
		}
		return rows[index];
	}

	/**
	 * 获取所有选中的行
	 * @param {*} jq 
	 */
	function _getCheckedRows(jq) {
		return _getAllRows(jq).has("input[role='"+ CHECK_BOX_ROLE_NAME +"']:checked");
	}

	/**
	 * 获取所有选中行的数据
	 * @param {*} jq 
	 */
	function _getCheckData(jq) {
		let rows = _getCheckedRows(this);
		if (!rows || rows.length == 0) {
			return [];
		}

		let arr = [];
		for (let i = 0; i < rows.length; i++) {
			arr.push($(rows[i]).data(ROW_DATA_NAME));
		}
		return arr;
	}

	/**
	 * 获取分页对象
	 * @param {*} jq 
	 */
	function _getPagination(jq) {
		return jq.find(">.card-footer>nav");
	}

	/**
	 * 从远端加载数据
	 * @param {*} jq 
	 * @param {*} param 
	 */
	function _load(jq, param) {
		let opt = jq.data(pluginName);
		_getTableDataContentFromRemote(jq, opt, param, {
			currentPage : 1
		}, function(data) {
			//分页重新加载
			let totalData = _getTotalAndData(data);
			_createPagination(jq, opt, totalData.total);
		});
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 获取表格中的行
		 */
		getRows : function() {
			return _getAllRows(this);
		},
		/**
		 * 获取一行
		 * @param {*} index 
		 */
		getRow : function(index) {
			return _getRow(this, index);
		},
		/**
		 * 获取所有行的数据
		 */
		getAllData : function() {
			return _getAllData(this);
		},
		/**
		 * 获取指定的行数据
		 * @param {}} index 
		 */
		getData : function(index) {
			return _getData(this, index);
		},
		/**
		 * 获取选中的行
		 */
		getCheckedRows : function() {
			return _getCheckedRows(this);
		},
		/**
		 * 获取选中的data
		 */
		getCheckData : function() {
			return _getCheckData(this);
		},
		/**
		 * 选中一行
		 * @param {*} index 
		 */
		checkRow : function(index) {
			let row = _getRow(this, index);
			let chk = $(row).find("input[role='"+ CHECK_BOX_ROLE_NAME +"']");
			chk.prop("checked", true);
			_ckickChk(this, chk);
		},
		/**
		 * 取消选中一行
		 * @param {*} index 
		 */
		unCheckRow : function(index) {
			let row = _getRow(this, index);
			let chk = $(row).find("input[role='"+ CHECK_BOX_ROLE_NAME +"']");
			chk.prop("checked", false);
			_ckickChk(this, chk);
		},
		/**
		 * 从远端从新加载数据
		 * @param {*} param 
		 */
		load : function(param) {
			let self = this;
			return this.each(function() {
				_load(self, param);
			});
		}
	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当从远端加载数据之前
		 * 如果返回false，则阻止从远端加载
		 */
		onBeforeLoad : function() {},
		/**
		 * 当加载成功时
		 * @param {*} data
		 */
		onLoadSuccess : function(data) {},
		/**
		 * 当选中复选框时
		 * @param {*} rowIndex 
		 * @param {*} rowData 
		 */
		onCheck : function(rowIndex, rowData) {},
		/**
		 * 当取消选中复选框时
		 * @param {*} rowIndex 
		 * @param {*} rowData 
		 */
		onUnCheck : function(rowIndex, rowData) {},
		/**
		 * 当点击一行时
		 * @param {*} rowIndex 
		 * @param {*} rowData 
		 */
		onClickRow : function(rowIndex, rowData) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		cardStyle : $.bootstrapClass.PRIMARY,
		title : "",						//表格标题
		url : "",						//远端调用的url
		queryParams : {},				//调用url传递的参数
		type : "POST",					//提交方式
		dataType : "json",				//返回的类型
		loadFilter : function(data) {	//对返回值进行特殊处理
			return data;
		},
		striped : false,				//是否显示条纹
		hover : true,					//是否悬停显示
		dark : false,					//dark模式
		bordered : true,				//是否显示边框
		small : false,					//small模式
		columns : [],					//表格列字段
		singleSelect : false,			//是否只能单选
		pageSize : 10,					//分页时，每页条数
		pagination : false,				//是否分页
		currentPage : 1,				//当前页数
		sortName : "",					//排序字段
		sortOrder : "ASC",				//排序方式(ASC，DESC)
		data : null,					//如果不用url，则可以直接传入data
		singleSelect : false,			//是否单选
		idField : null,					//表示id值的字段
		/**
		 * 对行进行格式化
		 * @param {*} rowIndex 
		 * @param {*} rowData 
		 */
		formatterRow : function(rowIndex, rowData) {},
		/**
		 * 对行样式进行处理
		 * 如果返回值是对象，则应用在css属性里面
		 * 如果返回值是string，则应用在class里面
		 * @param rowIndex
		 * @param rowData
		 */
		rowStyler : function(rowIndex, rowData) {}
	});

	/**
	 * 其中，columns是一数组，每个数组包含字段
	 * width=null				列宽
	 * title=""					列标题
	 * field=""					列属性
	 * align="left"				对齐
	 * sortable=false			是否可以排序
	 * sortcolumn=[field]		排序字段名称（默认为该字段的field）
	 * checkbox=false,			是否显示复选框
	 * formatter : function(value, rowData, rowIndex) {	//对列进行格式化
			如果返回值是jquery对象，则直接把该对象放在该列
			如果是返回值是string，则直接放在html()里面
	   }
	 * styler : function(value, rowData, rowIndex) {		//对列的样式进行格式化
			如果返回值是对象，则应用在css属性里面
			如果返回值是string，则应用在class里面
	   },
	 */

})(jQuery);