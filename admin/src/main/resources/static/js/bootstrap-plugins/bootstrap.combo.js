/**
 * 一个带下拉面板的输入框
 * 作者：黄平
 * 日期：2020-08-03
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "combo";
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
			_create(self, opt);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _create(jq, opt) {
		//创建textbox
		jq.textbox($.extend({}, opt, {
			//iconCls : "glyphicon-chevron-down"
		}));
		
		//创建panel
		var panel = _createPanel(jq);
		
		jq.click(function() {
			_showPanel(jq);
		});
		$(document).click(function(e) {
			if (e.target != jq.get(0) && e.target != panel.get(0) && e.target != panel.find(e.target).get(0)) {
				panel.panel("hide");
			}
		});
	}

	//方法
	$.fn[pluginName].methods = {

	};
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 当显示下拉panel之前触发，如果该方法返回false，则阻止showPanel
		 */
		onBeforeShowPanel : function() {},
		/**
		 * 当显示完下拉panel后触发
		 */
		onAfterShowPanel : function() {},
		/**
		 * 当隐藏下拉panel之前触发（如果该方法返回false，则阻止hidePanel）
		 */
		onBeforeHidePanel : function() {},
		/**
		 * 当隐藏完下拉panel后触发
		 */
		onAfterHidePanel : function() {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		panelSelector : undefined,						//panel对象的选择器
		panelHeight : "auto", 							//下拉框高度
		panelWidth : undefined							//下拉框宽度
	});
})(jQuery);