/**
 * 一个表格插件
 * 作者：黄平
 * 日期：2020-05-24
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "table";
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
			
		});
	};
	
	//方法
	$.fn[pluginName].methods = {

	};
	
	//事件
	$.fn[pluginName].events = {
		
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		columnAutoWidth : false,//列是否自动宽度
		url : "",				//远端调用的url
		queryParams : {},		//调用url传递的参数
		type : "POST",			//提交方式
		dataType : "json",		//返回的类型
		pagination : false,		//是否分页
		pageSize : 10,			//分页时，每页条数
		data : null,			//如果不用url，则可以直接传入data
		striped : false,		//是否显示条纹
		panelClass : "panel-default",	//标题面板 的样式 （primary,success,info,warning,danger）
		title : "",						//表格标题
		label : "",                     //表格上方标签区域
		button : "",                     //表格上方按钮
		hover : true,					//是否悬停显示
		border : true,					//是否显示边框
		columns : [],					//表格列字段
		idField : null,					//表示id值的字段
		singleSelect : false,			//是否单选
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
		},
		/**
		 * 表格中是否存在表头单元格合并的情况
		 */
		mergeHeads : false,
		/**
		 * 需要合并单元格的列名 合并行
		 */
		mergeRowsField : "",

		/**
		 * 当前页码
		 */
		currentPage : ""
	});
})(jQuery);