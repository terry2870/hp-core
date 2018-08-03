/**
 * 显示一个下拉框
 * 作者：黄平
 * 日期：2016-04-25
 * 依赖textbox,panel
 */
(function($) {
	$.fn.combo = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.combo.methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.combo.defaults, options);
			self.data("combo", opt);
			_createCombo(self);
		});
	};

	/**
	 * 创建
	 */
	function _createCombo(jq) {
		var opt = jq.data("combo");
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
	
	/**
	 * 创建panel
	 */
	function _createPanel(jq) {
		var opt = jq.data("combo");
		var div = $("<div comboText='"+ jq.selector +"'>").appendTo($("body")).css({
			position : "absolute",
			overflow : "auto"
		}).hide();
		
		var po = jq.offset();
		div.css({
			left : po.left,
			top : po.top + jq.outerHeight(true),
			//width : (opt.panel && opt.panel.style && opt.panel.style.width) ? opt.panel.style.width : jq.outerWidth(true),
			width : (opt.panelWidth) ? opt.panelWidth : jq.outerWidth(true),
			//height : (opt.panel && opt.panel.style && opt.panel.style.height) ? opt.panel.style.height : "auto"
			height : (opt.panelHeight) ? opt.panelHeight : "auto"
		});
		//div.append(opt.panelSelector);
		
		
		div.panel($.extend({}, $.fn.panel.defaults, {
			selector : opt.panelSelector,
			showFooter : false,
			bodyStyle : {
				padding : 0,
				border : 0
			}
		}));
		return div;
	}
	
	/**
	 * 获取panel
	 */
	function _getPanel(jq) {
		return $("body").find("div[comboText='"+ $(jq).selector +"']");
	}
	
	/**
	 * 隐藏或显示下拉
	 */
	/*function _togglePanel(jq) {
		$("body").find("div[comboText='"+ $(jq).selector +"']").slideToggle();
	}*/
	
	/**
	 * 隐藏下拉
	 */
	function _hidePanel(jq) {
		var opt = jq.data("combo");
		if (opt.onBeforeHidePanel) {
			if (opt.onBeforeHidePanel.call(jq) === false) {
				return;
			}
		}
		$("body").find("div[comboText='"+ $(jq).selector +"']").hide();
		//$("body").find("div[comboText='"+ $(jq).selector +"']").hide();
		if (opt.onAfterHidePanel) {
			opt.onAfterHidePanel.call(jq);
		}
	}
	
	/**
	 * 显示下拉
	 */
	function _showPanel(jq) {
		var opt = jq.data("combo");
		if (opt.onBeforeShowPanel) {
			if (opt.onBeforeShowPanel.call(jq) === false) {
				return;
			}
		}
		var panel = _getPanel(jq);
		var po = jq.offset();
		panel.css({
			left : po.left,
			top : po.top + jq.outerHeight(true),
			//width : (opt.panel && opt.panel.style && opt.panel.style.width) ? opt.panel.style.width : jq.outerWidth(true),
			width : (opt.panelWidth) ? opt.panelWidth : jq.outerWidth(true),
			//height : (opt.panel && opt.panel.style && opt.panel.style.height) ? opt.panel.style.height : "auto"
			height : (opt.panelHeight) ? opt.panelHeight : "auto"
		});
		panel.show();
		//$("body").find("div[comboText='"+ $(jq).selector +"']").panel("show", "slide");
		if (opt.onAfterShowPanel) {
			opt.onAfterShowPanel.call(jq);
		}
	}
	
	$.fn.combo.methods = $.extend({}, $.fn.textbox.methods, {
		/**
		 * 获取panel
		 */
		getPanel : function() {
			return _getPanel(this);
		},
		/**
		 * 隐藏panel
		 */
		hidePanel : function() {
			var self = $(this);
			return self.each(function() {
				_hidePanel(self);
			});
		},
		/**
		 * 显示panel
		 */
		showPanel : function() {
			var self = $(this);
			return self.each(function() {
				_showPanel(self);
			});
		}
		/**
		 * 隐藏或显示panel
		 */
		/*togglePanel : function() {
			var self = $(this);
			return self.each(function() {
				_togglePanel(self);
			});
		}*/
	});
	
	$.fn.combo.event = $.extend({}, $.fn.textbox.event, {
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
	});
	$.fn.combo.defaults = $.extend({}, $.fn.combo.event, $.fn.textbox.defaults, {
		panelSelector : undefined,						//panel对象的选择器
		panelHeight : "auto", 							//下拉框高度
		panelWidth : undefined							//下拉框宽度
	});
})(jQuery);