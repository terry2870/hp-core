/**
 * 一个带下拉面板的输入框
 * 作者：黄平
 * 日期：2020-08-03
 * 继承与：textbox
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "combo";
	let COMBO_RANDOM_VALUE = "COMBO_RANDOM_VALUE";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
			}
		}

		//生成一个6位随机数
		let random = _getRandom(100000, 999999);
		this.data(COMBO_RANDOM_VALUE, random);
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create(self, opt, random);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} random 
	 */
	function _create(jq, opt, random) {
		//创建textbox
		jq.textbox($.extend({}, opt, {
			//iconCls : "glyphicon-chevron-down"
		}));
		
		//创建panel
		let panel = _createPanel(jq, opt, random);
		
		jq.click(function() {
			_showPanel(jq);
		});
		$(document).click(function(e) {
			if (e.target != jq.get(0) && e.target != panel.get(0) && e.target != panel.find(e.target).get(0)) {
				_hidePanel(jq);
			}
		});
	}

	/**
	 * 创建panel
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} random 
	 */
	function _createPanel(jq, opt, random) {
		let panel = $("<div>").css({
			position : "absolute",
			overflow : "auto",
			"z-index" : 9999
		}).appendTo($("body")).hide();
		panel.attr(COMBO_RANDOM_VALUE, random);
		
		let targetPosition = jq.offset();
		panel.css({
			left : targetPosition.left,
			top : targetPosition.top + jq.outerHeight(true)
		});
		
		//panel.append(opt.panelSelector);

		panel.card({
			width : (opt.panelWidth) ? opt.panelWidth : jq.outerWidth(true),
			height : (opt.panelHeight) ? opt.panelHeight : "auto",
			body : {
				selector : opt.panelSelector,
				style : {
					padding : 0,
					border : 0
				}
			}
		});
		return panel;
	}

	/**
	 * 隐藏下拉
	 * @param {*} jq 
	 */
	function _hidePanel(jq) {
		let opt = jq.data(pluginName);
		if (opt.onBeforeHidePanel) {
			if (opt.onBeforeHidePanel.call(jq) === false) {
				return;
			}
		}
		let panel = _getPanel(jq);
		panel.card("close");
		if (opt.onAfterHidePanel) {
			opt.onAfterHidePanel.call(jq);
		}
	}
	
	/**
	 * 显示下拉
	 */
	function _showPanel(jq) {
		let opt = jq.data(pluginName);
		if (opt.onBeforeShowPanel) {
			if (opt.onBeforeShowPanel.call(jq) === false) {
				return;
			}
		}
		let panel = _getPanel(jq);
		let po = jq.offset();
		panel.css({
			left : po.left,
			top : po.top + jq.outerHeight(true)
		});
		panel.card("show");
		if (opt.onAfterShowPanel) {
			opt.onAfterShowPanel.call(jq);
		}
	}

	/**
	 * 获取panel
	 * @param {*} jq 
	 */
	function _getPanel(jq) {
		let random = jq.data(COMBO_RANDOM_VALUE);
		return $("body").find("div["+ COMBO_RANDOM_VALUE +"='"+ random +"']");
	}

	/**
	 * 生成随机数
	 * @param {*} min 
	 * @param {*} max 
	 */
	function _getRandom(min, max) {
		let rand = parseInt(Math.random() * (max - min + 1) + min);
		return rand;
	}

	//方法
	$.fn[pluginName].methods = {
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
			let self = $(this);
			return self.each(function() {
				_hidePanel(self);
			});
		},
		/**
		 * 显示panel
		 */
		showPanel : function() {
			let self = $(this);
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