/**
 * 显示一个form
 * 继承与easyui的form
 * 作者：黄平
 * 日期：2016-07-29
 */
(function($) {
	$.fn.myForm = function(options, param) {
		var self = this;
		if (typeof (options) == "string") {
			var method = $.fn.myForm.methods[options];
			if (method){
				return method.call(this, param);
			} else {
				return this.form(options, param);
			}
		}
		return this.each(function() {
			var opt = $.extend({}, $.fn.myForm.defaults, options);
			self.data("myForm", opt);
			_createForm(self);
		});
	};
	
	/**
	 * 创建form
	 * @param jq
	 * @returns
	 */
	function _createForm(jq) {
		var opt = jq.data("myForm");
		jq.form($.extend({}, opt, {
			onBeforeLoad : function() {},
			onLoadSuccess : function() {}
		}));
		_loadData(jq);
	}
	
	/**
	 * 加载数据
	 * @param jq
	 * @returns
	 */
	function _loadData(jq) {
		var opt = jq.data("myForm");
		
		var interval = window.setInterval(function() {
			var result = _checkLoadStatus(jq);
			if (result === true) {
				//依赖项全部加载完成，清除延迟
				clearInterval(interval);
				
				//如果加载前的事件，返回false，则停止加载
				if (opt.onBeforeLoad) {
					if (opt.onBeforeLoad.call(jq, opt.loadParams) === false) {
						return;
					}
				}
				
				//继续加载
				if (opt.loadUrl) {
					//调用url进行加载
					$.post(opt.loadUrl, opt.loadParams, function(data) {
						//处理返回的数据
						if (opt.loadFilter) {
							data = opt.loadFilter(data);
						}
						_setData(jq, data);
					}, opt.dataType);
				} else {
					_setData(jq, opt.loadData);
				}
			}
		}, 200);
	}
	
	/**
	 * 检查依赖的项，是否加载完成
	 */
	function _checkLoadStatus(jq) {
		var opt = jq.data("myForm");
		if (!opt.loadDepend || opt.loadDepend.length == 0) {
			return true;
		}
		for (var i = 0; i < opt.loadDepend.length; i++) {
			if ($(opt.loadDepend[i]).loadSuccess("isNotLoadSuccess")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 往form里面设置值
	 */
	function _setData(jq, data) {
		if (!data) {
			return;
		}
		jq.form("load", data);
		var opt = jq.data("myForm");
		if (opt) {
			//从远端获取完数据后执行
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, data);
			}
		}
	}
	
	$.fn.myForm.methods = {
		setData : function(data) {
			var jq = $(this);
			return jq.each(function() {
				_setData(jq, data);
			});
		}
	};
	
	
	$.fn.myForm.event = {
		
	};
	
	$.fn.myForm.defaults = $.extend({}, $.fn.myForm.event, {
		loadDepend : [],		//加载前依赖于
		loadData : {},			//加载的数据
		loadUrl : "",			//加载form时，请求的url
		loadParams : {},		//初始化form时，传递到后台的参数
		loadFilter : function(data) {	//对初始化返回值进行特殊处理
			return data;
		}
	});
})(jQuery);