/**
 * 显示一个表格 继承与easyui的datagrid，使得可以自由选择表头
 * 作者：黄平
 * 日期：2014-05-08
 */
(function($){
	$.fn.myDatagrid = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myDatagrid.methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myDatagrid.defaults, options);
			self.data("myDatagrid", opt);
			if (opt.selectColumn == true) {
				var div = $("<div>").click(function(e1) {
					e1.stopPropagation();
				}).appendTo($(window.document.body));
				if (options && options.columns && options.columns[0] && options.columns[0].length > 0) {
					var divItem, chked = false;
					for (var i = 0; i < options.columns[0].length; i++) {
						if (options.columns[0][i].field == "" || options.columns[0][i].title == "") {
							continue;
						}
						chked = false;
						if (!options.columns[0][i].hidden) {
							chked = true;
						} else if (options.columns[0][i].hidden == true) {
							chked = false;
						}
						divItem = $("<div class='menu-item'>").hover(function() {
							$(this).addClass("menu-active");
						}, function() {
							$(this).removeClass("menu-active");
						}).append($("<input style='cursor:pointer' id='checkbox_"+ options.columns[0][i].field +"' type='checkbox'>").attr("checked", chked).data("checkboxData", options.columns[0][i])).append($("<label>").attr("for", "checkbox_" + options.columns[0][i].field).css("cursor", "pointer").text(options.columns[0][i].title)).appendTo(div);
					}
				}
				div.panel({
					style : {
						position : "absolute"
					},
					closed : true
				});
				$("input", div).click(function() {
					var data = $(this).data("checkboxData");
					var checked = div.find("input:checked");
					if (!this.checked && checked.length == 0) {
						this.checked = true;
						return;
					}
					for (var i = 0; i < options.columns[0].length; i++) {
						if (data.field == options.columns[0][i].field) {
							$(self).datagrid(this.checked ? "showColumn" : "hideColumn", data.field);
							return;
						}
					}
				});
				$(document).click(function() {
					div.panel("close");
				});
				var o = $.extend({}, opt, {
					striped : false,
					onHeaderContextMenu : function(e, field) {
						e.preventDefault();
						div.panel("move", {
							left : e.clientX,
							top : e.clientY
						});
						div.panel("open");
						div.panel("resize", {
							width : opt.menuWidth
						});
					},
					onClickRow : function(rowIndex, rowData) {
						if (options.onClickRow) {
							options.onClickRow(rowIndex, rowData);
						}
						div.panel("close");
					}
				});
			}
			$(self).datagrid(o);
			if (opt.enableColumnMove === true) {
				$(self).myDatagrid("columnMoving");
			}
		});
	};
	
	/**
	 * 根据行号，查询对象
	 * @param jq
	 * @param index
	 * @returns
	 */
	function _getRowDataByIndex(jq, index) {
		var rows = jq.datagrid("getRows");
		return rows[index];
	}
	
	$.fn.myDatagrid.methods = {
		/**
		 * 根据id，或者值
		 */
		getRowDataById : function(id) {
			var jq = $(this);
			var opt = jq.data("myDatagrid");
			var rows = $(this).datagrid("getRows");
			if (!rows || rows.length == 0) {
				return {};
			}
			for (var i = 0 ; i < rows.length; i++) {
				if (rows[i][opt.idField] == id) {
					return rows[i];
				}
			}
			return {};
		},

		getRowDataByIndex : function(index) {
			return _getRowDataByIndex($(this), index);
		},
		columnMoving : function() {
			return this.each(function() {
				var target = this;
				var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
				cells.draggable({
					revert : true,
					cursor : 'pointer',
					edge : 5,
					proxy : function(source) {
						var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
						p.html($(source).text());
						p.hide();
						return p;
					},
					onBeforeDrag : function(e) {
						e.data.startLeft = $(this).offset().left;
						e.data.startTop = $(this).offset().top;
					},
					onStartDrag : function() {
						$(this).draggable('proxy').css({
							left : -10000,
							top : -10000
						});
					},
					onDrag : function(e) {
						$(this).draggable('proxy').show().css({
							left : e.pageX + 15,
							top : e.pageY + 15
						});
						return false;
					}
				}).droppable({
					accept : 'td[field]',
					onDragOver : function(e, source) {
						var columns = $(target).datagrid('options').columns;
						var frozenColumns = $(target).datagrid('options').frozenColumns;
						var columnsClone = frozenColumns[0].slice(0);
						columnsClone = columnsClone.concat(columns[0]);
						var toField = $(this).attr('field');
						var tdData = null;
						for (var i = 0; i < columnsClone.length; i++) {
							if (columnsClone[i].field == toField) {
								tdData = columnsClone[i];
								break;
							}
						}
						if (tdData && tdData.checkbox == true) {
							$(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
							$(this).css('border-left', 0);
							return;
						}
						$(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
						$(this).css('border-left', '1px solid #ff0000');
					},
					onDragLeave : function(e, source) {
						$(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
						$(this).css('border-left', 0);
					},
					onDrop : function(e, source) {
						$(this).css('border-left', 0);
						var columns = $(target).datagrid('options').columns;
						var frozenColumns = $(target).datagrid('options').frozenColumns;
						var columnsClone = frozenColumns[0].slice(0);
						columnsClone = columnsClone.concat(columns[0]);
						var fromField = $(source).attr('field');
						var toField = $(this).attr('field');
						var tdData = null;
						for (var i = 0; i < columnsClone.length; i++) {
							if (columnsClone[i].field == toField) {
								tdData = columnsClone[i];
								break;
							}
						}
						if (tdData && tdData.checkbox == true) {
							return;
						}
						setTimeout(function() {
							moveField(fromField, toField);
							$(target).myDatagrid();
							$(target).myDatagrid('columnMoving');
						}, 0);
					}
				});
				// move field to another location
				function moveField(from, to) {
					var columns = $(target).datagrid('options').columns;
					var cc = columns[0];
					var c = _remove(from);
					if (c) {
						_insert(to, c);
					}
					function _remove(field) {
						for (var i = 0; i < cc.length; i++) {
							if (cc[i].field == field) {
								var c = cc[i];
								cc.splice(i, 1);
								return c;
							}
						}
						return null;
					}
					function _insert(field, c) {
						var newcc = [];
						for (var i = 0; i < cc.length; i++) {
							if (cc[i].field == field) {
								newcc.push(c);
							}
							newcc.push(cc[i]);
						}
						columns[0] = newcc;
					}
				}
			});
		}
	};
	$.fn.myDatagrid.events = {
		
	};
	$.fn.myDatagrid.defaults = $.extend({}, $.fn.myDatagrid.events, {
		menuWidth : 120,
		enableColumnMove : false,	//移动列
		selectColumn : true			//选择列
	});
})(jQuery);
