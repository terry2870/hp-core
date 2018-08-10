/**
 * 显示一个表格控件 
 * 依赖于bootstrap的table 
 * 作者：黄平 
 * 日期：2016-04-13
 */
(function($) {
	$.fn.table = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn.table.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn.table.defaults, $.fn.table.events, options);
			if (!opt.columns || opt.columns.length == 0) {
				return;
			}
			self.data("table", opt);
			
			_createtTable.call(self);
		});
	};
	/**
	 * 生成（适用于bootstrap）
	 */
	function _createtTable() {
		let jq = this;
		let opt = $(jq).data("table");
		
		jq.attr("randomCode", Math.random());
		
		//清空table
		jq.empty();
		
		let table, thead, headTR, th;
		table = $("<table>").addClass("table").appendTo(body).attr("tableRole", "table");
		if (opt.columnAutoWidth !== true) {
			table.css({
				"table-layout" : "fixed",
				"word-wrap" : "break-word",
				"word-break" : "break-all",
				//"white-space" : "nowrap",
				"text-overflow" : "ellipsis"
			});
		}
		//设置边框
		if (opt.border === true) {
			table.addClass("table-bordered");
		}
		//设置悬停
		if (opt.hover === true) {
			table.addClass("table-hover");
		}
		//设置斑马纹
		if (opt.striped === true) {
			table.addClass("table-striped");
		}
		thead = $("<thead>").appendTo(table);
		headTR = $("<tr>").appendTo(thead);
		//设置table的头
		$(opt.columns).each(function(index, item) {
			th = $("<th>").css({
				"text-align" : item.align ? item.align : "left",
				width : item.width ? item.width : "auto"
			}).appendTo(headTR);
			if (item.checkbox === true) {
				$("<input type=\"checkbox\" id=\"bootstrap-table-checkAll\" />").click(function() {
					//点击全选
					if (opt.singleSelect === true) {
						this.checked = false;
						return;
					}
					_clickCheckAll(jq, this.checked);
				}).appendTo(th);
			} else {
				th.html(item.title);
			}
		});
		
		jq.panel({
			panelClass : opt.panelClass,
			title : opt.title,
			content : table,
			collapseAble : opt.collapseAble,
			showFooter : opt.pagination === true
		});
		
		if (opt && opt.url) {
			_loadRemote(jq);
		} else {
			//显示正在加载
			_showLoading(jq);
			//生成tbody
			_createTbody(jq);
			
			//处理onLoadSuccess事件
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, opt.data);
			}
			
			//分页
			_createPage(jq);
			
			//隐藏正在加载
			_hideLoading(jq);
			
			//设置已经加载完成
			jq.attr("loadStatus", "1");
		}
		
	}
	
	/**
	 * 从远端加载数据
	 */
	function _loadRemote(jq, param) {
		//显示正在加载
		_showLoading(jq);
		let opt = $(jq).data("table");
		
		_removeTbody(jq);
		if (!opt || !opt.url) {
			//隐藏正在加载
			_hideLoading(jq);
			return;
		}
		if (opt.onBeforeLoad) {
			if (opt.onBeforeLoad.call(jq) === false) {
				//隐藏正在加载
				_hideLoading(jq);
				return;
			}
		}
		let lastQueryParam = $.extend({}, opt.queryParams, jq.data("lastQueryParam"), param);
		jq.data("lastQueryParam", lastQueryParam);
		$.ajax($.extend({}, {
			url : opt.url,
			data : $.extend({}, lastQueryParam, {
				pageSize : (opt.pagination === true ? opt.pageSize : 0),
                currentPage : (opt.pagination === true ? (param && param.currentPage ? param.currentPage : 0) : 0)
			}),
			type : opt.type,
			dataType : opt.dataType,
			success : function(json) {
				if (!json) {
					//隐藏正在加载
					_hideLoading(jq);
					return;
				}
				opt.data = json;
				if (opt.loadFilter) {
					opt.data = opt.loadFilter(json);
				}
				//生成tbody
				_createTbody(jq);
				
				//处理onLoadSuccess事件
				if (opt.onLoadSuccess) {
					opt.onLoadSuccess.call(jq, opt.data);
				}
				
				//分页
                _createPage(jq, (opt.pagination === true ? (param && param.currentPage ? param.currentPage : 0) : 0));
				//隐藏正在加载
				_hideLoading(jq);
				
				//设置已经加载完成
				jq.attr("loadStatus", "1");
			},
			error : function() {
				//隐藏正在加载
				_hideLoading(jq);
			},
			dataType : "json"
		}));
	}
	
	
	/**
	 * 取去总条数和行记录
	 */
	function _getTotalAndData(jq) {
		let opt = jq.data("table");
		let d = opt.data;
		let total = 0;
		if ($.type(d) === "object") {
			total = d.total;
			d = d.rows;
		} else {
			total = d ? d.length : 0;
		}
		return {
			rows : d,
			total : total
		};
	}
	
	/**
	 * 显示正在加载的效果
	 */
	function _showLoading(jq) {
		let div = $("<div>").attr("tableSelector", jq.attr("randomCode")).addClass("modal-backdrop fade in").appendTo($("body")).hide();
		let tableOffset = jq.offset();
		let tableWidth = jq.outerWidth(true), tableHeight = jq.outerHeight(true);
		div.append($("<div>").addClass("table-loading").css({
			left : (tableWidth - 124) / 2,
			top : (tableHeight - 124) / 2
		}));
		div.css({
			width : tableWidth,
			height : tableHeight,
			left : tableOffset.left,
			top : tableOffset.top
		});
		div.show();
	}
	
	/**
	 * 隐藏正在加载效果
	 */
	function _hideLoading(jq) {
		$("body").find("div[tableSelector='"+ jq.attr("randomCode") +"']").remove();
	}
	
	/**
	 * 获取body部分
	 */
	function _getBody(jq) {
		return jq.panel("getBodyObject");
		//return jq.find("div.panel-body[tableRole='body']");
	}
	
	/**
	 * 获取table
	 */
	function _getTable(jq) {
		return jq.find("table.table[tableRole='table']");
	}
	
	/**
	 * 获取tbody
	 */
	function _getTbody(jq) {
		return jq.find("table.table tbody");
	}
	
	/**
	 * 获取表头
	 */
	function _getThead(jq) {
		return jq.find("table.table thead");
	}
	
	/**
	 * 获取分页对象
	 */
	function _getPagination(jq) {
		return jq.find("div[role='pagination']");
	}
	
	/**
	 * 销毁表格tbody
	 */
	function _removeTbody(jq) {
		_getTbody(jq).remove();
	}
	
	/**
	 * 生成分页部分
	 */
    function _createPage(jq, currentPage) {
		let opt = jq.data("table");
		let totalAndRow = _getTotalAndData(jq);
		if (opt.pagination !== true) {
			return;
		}
		_getPagination(jq).remove();
		let pageDiv = $("<div>").appendTo(jq.panel("getFooterObject"));
		jq.panel("setFooter", pageDiv);
		pageDiv.pagination({
			totalCount : totalAndRow.total,
			currentPage : currentPage ? currentPage : 1,
			pageSize : opt.pageSize,
			onSelectPage : function(currentPage, pageSize) {
				//显示正在加载
				_showLoading(jq);
				let lastQueryParam = jq.data("lastQueryParam");
				$.ajax($.extend({}, {
					url : opt.url,
					data : $.extend({}, lastQueryParam, {
						currentPage : currentPage,
						pageSize : pageSize
					}),
					type : opt.type,
					dataType : opt.dataType,
					success : function(json) {
						if (!json) {
							return;
						}
						opt.data = json;
						if (opt.loadFilter) {
							opt.data = opt.loadFilter(json);
						}
						_loadLoaclData(jq);
						
						//隐藏正在加载
						_hideLoading(jq);
					},
					error : function() {
						//隐藏正在加载
						_hideLoading(jq);
					},
					dataType : "json"
				}));
			}
		});
		
		
		
		
		
		
		if (opt.pagination === true) {
			
			
		}
	}
	
	/**
	 * 生成body部分
	 * @param table
	 * @param opt
	 */
	function _createTbody(jq) {
		let opt = jq.data("table");
		if (!opt.data) {
			return;
		}
		let totalAndRow = _getTotalAndData(jq);
		if (!totalAndRow.rows || totalAndRow.rows.length == 0) {
			return;
		}
		let tbody = $("<tbody>").css({
			overflow : "auto"
		}).appendTo(_getTable(jq));
		//设置table的主体
		$(totalAndRow.rows).each(function(rowIndex, rowData) {
			if (opt.formatterRow) {
				let formatterRowResult = opt.formatterRow(rowData, rowIndex);
				if (formatterRowResult) {
					tbody.append(formatterRowResult);
					return true;
				}
			}
			let bodyTR = $("<tr>").data("rowData", rowData).appendTo(tbody).css("cursor", "pointer");
			if (opt.idField) {
				bodyTR.attr("table_rowDataId", rowData[opt.idField]);
			}
			bodyTR.click(function() {
				let chk = bodyTR.find("input:checkbox[name='bootstrap-table-checkbox']");
				if (chk) {
					//chk.prop("checked", !chk.prop("checked"));
					//_clickChk.call(chk.get(0), jq, rowIndex, rowData);
					chk.trigger("click");
					//_clickChk.call(chk.get(0), jq, rowIndex, rowData);
				}
			});
			if (opt.onClickRow) {
				bodyTR.click(function() {
					opt.onClickRow.call(jq, rowIndex, rowData);
				});
			}
			//行样式
			if (opt.rowStyler) {
				let rowStyler = opt.rowStyler(rowData, rowIndex);
				if ($.type(rowStyler) === "object") {
					bodyTR.css(rowStyler);
				} else if ($.type(rowStyler) === "string") {
					bodyTR.addClass(rowStyler);
				}
			}
			$(opt.columns).each(function(columnIndex, columnData) {
				let bodyTD = $("<td>").css({
					"text-align" : columnData.align ? columnData.align : "left"
				}).appendTo(bodyTR);
				//列样式
				if (columnData.styler) {
					let styler = columnData.styler(rowData[columnData.field], rowData, rowIndex);
					if ($.type(styler) === "object") {
						bodyTD.css(styler);
					} else if ($.type(styler) === "string") {
						bodyTD.addClass(styler);
					}
				}
				if (columnData.checkbox === true) {
					$("<input type=\"checkbox\" name=\"bootstrap-table-checkbox\" id=\"bootstrap-table-checkbox_"+ rowIndex +"\" />").click(function(event) {
						_clickChk.call(this, jq, rowIndex, rowData);
						
					}).appendTo(bodyTD);
				} else {
					if (columnData.formatter) {
						let formatter = columnData.formatter(rowData[columnData.field], rowData, rowIndex);
						if ($.type(formatter) === "object") {
							bodyTD.append(formatter);
						} else {
							bodyTD.html(formatter);
						}
					} else {
						bodyTD.html(rowData[columnData.field]);
					}
				}
			});
		});
	}
	
	/**
	 * 获取选中的行数据
	 */
	function _clickChk(jq, rowIndex, rowData) {
		//阻止冒泡
		event.stopPropagation();
		//点击选择，当不是全部都选中时候，把上面的全选框去掉选中状态
		let opt = jq.data("table");
		let thead = _getThead(jq);
		if (!this.checked) {
			//当有一个没有选中时，去掉全选框
			thead.find("#bootstrap-table-checkAll").prop("checked", false);
			
			if (opt.onUnCheck) {
				opt.onUnCheck.call(jq, rowIndex, rowData);
			}
		} else {
			if (opt.singleSelect === true) {
				//当是只能单选时
				_clickCheckAll(jq, false);
				//this.checked = true;
				this.checked = true;
			} else {
				let chedkeds = _getTbody(jq).find("input:checked[name='bootstrap-table-checkbox']");
				let totalAndRow = _getTotalAndData(jq);
				thead.find("#bootstrap-table-checkAll").prop("checked", chedkeds.length == totalAndRow.rows.length);
			}
			
			if (opt.onCheck) {
				opt.onCheck.call(jq, rowIndex, rowData);
			}
		}
	}
	
	/**
	 * 获取选中的行数据
	 */
	function _getCheckedData(jq) {
		let arr = [];
		let checked = jq.find("input:checked[name='bootstrap-table-checkbox']");
		if (!checked || checked.length == 0) {
			return arr;
		}
		$(checked).each(function(index, item) {
			arr.push($(item).parent().parent().data("rowData"));
		});
		return arr;
	}
	
	/**
	 * 点击全选
	 */
	function _clickCheckAll(jq, checked) {
		let opt = jq.data("table");
		if (checked === true && opt.singleSelect === true) {
			//如果只能单选，那就不处理
			return;
		}
		let rows = _getRows(jq);
		for (let i = 0; i < rows.length; i++) {
			if (checked === true) {
				_checkRow(jq, i);
			} else {
				_unCheckRow(jq, i);
			}
		}
		
		let check = jq.find("input:checkbox[name='bootstrap-table-checkbox']");
		check.prop("checked", checked);
	}
	
	/**
	 * 加载本地数据
	 */
	function _loadLoaclData(jq) {
		let opt = jq.data("table");
		_removeTbody(jq);
		//opt.data = data;
		_createTbody(jq);
		
		//处理onLoadSuccess事件
		if (opt.onLoadSuccess) {
			opt.onLoadSuccess.call(jq, opt.data);
		}
	}
	
	/**
	 * 选中一行
	 */
	function _checkRow(jq, index) {
		let row = $(_getRow(jq, index));
		let chk = row.find("input:checkbox[name='bootstrap-table-checkbox']");
		chk.prop("checked", true);
		_clickChk.call(chk.get(0), jq, index, row.data("rowData"));
	}
	
	/**
	 * 反选中一行
	 */
	function _unCheckRow(jq, index) {
		let row = $(_getRow(jq, index));
		let chk = row.find("input:checkbox[name='bootstrap-table-checkbox']");
		chk.prop("checked", false);
		_clickChk.call(chk.get(0), jq, index, row.data("rowData"));
	}
	
	/**
	 * 获取表格中的每一行对象
	 */
	function _getRows(jq) {
		return jq.find("table tbody tr");
	}
	
	/**
	 * 获取表格中的一行对象
	 */
	function _getRow(jq, index) {
		return _getRows(jq)[index];
	}
	
	/**
	 * 获取表格中的每一行数据
	 */
	function _getData(jq) {
		let rows = _getRows(jq);
		if (rows) {
			let data = [];
			for (let i = 0; i < rows.length; i++) {
				data.push($(rows[i]).data("rowData"));
			}
			return data;
		}
		return [];
	}
	
	function _reload(jq, param) {
		let page = _getPagination(jq).data("pagination");
		_loadRemote(jq, $.extend({}, param, {
			currentPage : page.currentPage
		}));
	}
	
	/**
	 * 获取表格中的一行数据
	 */
	function _getRowData(jq, index) {
		return _getRow(jq, index).data("rowData");
	}
	
	$.fn.table.methods = {
		/**
		 * 获取选中的行数据
		 */
		getChecked : function() {
			return _getCheckedData(this);
		},
		/**
		 * 加载本地数据
		 * @param data
		 */
		loadLoaclData : function(data) {
			let jq = this;
			return jq.each(function() {
				_loadLoaclData(jq);
			});
		},
		/**
		 * 从远程再去请求数据
		 * @param param
		 */
		load : function(param) {
			let jq = this;
			_loadRemote(jq, param);
		},
		/**
		 * 从远端请求数据（并且留在当前页）
		 */
		reload : function(param) {
			let jq = this;
			return this.each(function() {
				_reload(jq, param);
			});
		},
		/**
		 * 获取表格中的每一行数据
		 */
		getRows : function() {
			return _getRows(this);
		},
		/**
		 * 获取表格中的一行对象
		 * @param index
		 */
		getRow : function(index) {
			return _getRow(this, index);
		},
		/**
		 * 获取表格中的每一行数据data
		 */
		getData : function() {
			return _getData(this);
		},
		/**
		 * 获取表格中的一行数据
		 * @param index
		 */
		getRowData : function(index) {
			return _getRowData(this, index);
		},
		/**
		 * 选中一行
		 * @param param
		 */
		checkRow : function(index) {
			let jq = this;
			return jq.each(function() {
				_checkRow(jq, index);
			});
		},
		/**
		 * 反选中一行
		 * @param param
		 */
		unCheckRow : function(index) {
			let jq = this;
			return jq.each(function() {
				_unCheckRow(jq, index);
			});
		}
	};
	$.fn.table.events = {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {},
		
		/**
		 * 加载之前执行，如果返回false，则不去远端调用
		 * 
		 */
		onBeforeLoad : function() {},
		
		/**
		 * 当选中复选框时
		 * @param rowIndex
		 * @param rowData
		 */
		onCheck : function(rowIndex, rowData) {},
		/**
		 * 当去掉选中复选框时
		 * @param rowIndex
		 * @param rowData
		 */
		onUnCheck : function(rowIndex, rowData) {},
		/**
		 * 当点击一行时
		 * @param rowIndex
		 * @param rowData
		 */
		onClickRow : function(rowIndex, rowData) {}
		
	};
	$.fn.table.defaults = $.extend({}, $.fn.table.events, {
		columnAutoWidth : false,//列是否自动宽度
		scrollBar : true,		//是否自动滚动条
		url : "",				//远端调用的url
		queryParams : {},		//调用url传递的参数
		type : "POST",			//提交方式
		dataType : "json",		//返回的类型
		pagination : false,		//是否分页
		pageSize : 10,			//分页时，每页条数
		data : null,			//如果不用url，则可以直接传入data
		striped : false,		//是否显示条纹
		panelClass : $.bootstrapClass.DEFAULT,	//标题面板 的样式 （primary,success,info,warning,danger）
		title : "",						//表格标题
		hover : true,					//是否悬停显示
		border : true,					//是否显示边框
		columns : [],					//表格列字段
		idField : null,					//表示id值的字段
		singleSelect : false,			//是否单选
		collapseAble : false,			//是否可折叠
		loadFilter : function(data) {	//对返回值进行特殊处理
			return data;
		},
		/**
		 * 对行进行格式化
		 */
		formatterRow : function(rowData, rowIndex) {
			
		},
		
		/**
		 * 对行样式进行处理
		 * 如果返回值是对象，则应用在css属性里面
		 * 如果返回值是string，则应用在class里面
		 * @param rowData
		 * @param rowIndex
		 */
		rowStyler : function(rowData, rowIndex) {
			return null;
		}
	});
	/**
	 * 其中，columns是一数组，每个数组包含字段
	 * width=null		列宽
	 * title=""			列标题
	 * field=""			列属性
	 * align="left"	对齐
	 * checkbox=false,	是否显示复选框
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