/**
 * 一个左侧菜单
 * 作者：黄平
 * 日期：2020-05-09
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "leftMenu";
	let NAME_SPACE = "http://www.w3.org/2000/svg";
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
			_create($(self), opt);
		});
	};
	
	/**
	 * 创建
	 */
	function _create(jq, opt) {
		jq.addClass("left-side-menu");

		//生成伸缩条
		_createMenuTop(jq);

		//生成导航菜单
		if (opt.ajaxParam && opt.ajaxParam.url) {
			$.ajax($.extend({}, {
				success : function(json) {
					if (!json) {
						return;
					}
					opt.dataList = json;
					if (opt.loadFilter) {
						opt.dataList = opt.loadFilter(json);
					}
					_createLeftMenu(jq, opt);
					if (opt.onLoadSuccess) {
						opt.onLoadSuccess.call(jq, opt.dataList);
					}
				},
				dataType : "json"
			}, opt.ajaxParam));
		} else {
			_createLeftMenu(jq, opt);
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, opt.dataList);
			}
		}
		
		_init(jq);
	}

	/**
	 * 生成菜单顶部伸缩条
	 * @param {} jq 
	 */
	function _createMenuTop(jq) {
		let label = $("<label>").append($("<input>").attr({
			type : "checkbox",
			checked : "checked"
		}));

		let svg = _createSVG("svg");

		svg.click(function() {
			let checkbox = svg.prev("input[type='checkbox']");
			if (checkbox.prop("checked") == true) {
				jq.find(".lsm-sidebar-item.lsm-sidebar-show").removeClass('lsm-sidebar-show');
				jq.find("ul").removeAttr('style');
				jq.addClass('lsm-mini');
				jq.stop().animate({width : 60}, 200);
				$(".center-content").css("width", "calc(100% - 100px)");
			} else {
				jq.removeClass('lsm-mini');
				_getMenuContainer(jq).find("ul ul").hide();
				jq.stop().animate({width: 220}, 200);
				$(".center-content").css("width", "calc(100% - 260px)");
			}
		});

		svg.attr({
			viewBox : "0 0 100 100",
			xmlns : "http://www.w3.org/2000/svg"
		});
		svg.append(_createSVG("circle").attr({
			cx : 50,
			cy : 50,
			r : 30
		}));
		svg.append(_createSVG("path").addClass("line--1").attr("d", "M0 40h62c18 0 18-20-17 5L31 55"));
		svg.append(_createSVG("path").addClass("line--2").attr("d", "M0 50h80"));
		svg.append(_createSVG("path").addClass("line--3").attr("d", "M0 60h62c18 0 18 20-17-5L31 45"));
		label.append(svg);
		jq.append($("<div>").addClass("lsm-expand-btn").append($("<div>").addClass("lsm-mini-btn").append(label)));
	}

	/**
	 * 生成左侧菜单
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createLeftMenu(jq, opt) {
		let container = $("<div>").addClass("lsm-container").appendTo(jq);
		let scroll = $("<div>").addClass("lsm-scroll").appendTo(container);
		let sidebar = $("<div>").addClass("lsm-sidebar").appendTo(scroll);
		let ul = _createUL(jq, opt, opt.rootPid);
		sidebar.append(ul);

		//虚拟的滚动条
		_initScroll(jq);

		//默认展开
		if (opt.expandId !== undefined && opt.expandId !== null) {
			_getMenuContainer(jq).find("li.lsm-sidebar-item[menuid="+ opt.expandId +"]>a").click();
		}
	}

	/**
	 * 生成子节点(递归)
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} pid 
	 */
	function _createUL(jq, opt, pid) {
		if (!opt.dataList || opt.dataList.length == 0) {
			return null;
		}

		let childNode = _findChild(opt.dataList, pid, opt);
		if (!childNode || childNode.length == 0) {
			return null;
		}
		let ul = $("<ul>");

		if (pid == opt.rootPid) {
			ul.show();
		} else {
			ul.hide();
		}
		for (let i = 0; i < childNode.length; i++) {
			let node = childNode[i];
			let li = $("<li>").attr({
				menuid : node[opt.idField],
				pid : node[opt.pidField]
			}).appendTo(ul);
			
			let hasChild = _hasChild(opt.dataList, node[opt.idField], opt);

			if (hasChild) {
				li.addClass("lsm-sidebar-item");
			}
			let a = $("<a>").appendTo(li);
			let iEle = $("<i>").addClass("my-icon lsm-sidebar-icon").appendTo(a);
			if (node[opt.iconField]) {
				iEle.addClass(node[opt.iconField]);
			}
			a.append($("<span>").html(node[opt.textField]));
			if (hasChild) {
				a.append($("<i>").addClass("my-icon lsm-sidebar-more"));
				a.click(function() {
					if (jq.hasClass("lsm-mini")) {
						return;
					}
					let parent = $(this).parent();
					if (parent.hasClass("lsm-sidebar-show")) {
						_collapseMenu(jq, parent);
					} else {
						_expandMenu(jq, parent);
					}
				});
			} else {
				a.click(function() {
					opt.onClickMenu(node, i);
				});
			}
			if (opt.selectedId !== undefined && opt.selectedId !== null && opt.selectedId == node[opt.idField]) {
				a.addClass("active");
			}
			li.append(_createUL(jq, opt, node[opt.idField]));
		}

		return ul;
	}

	/**
	 * 是否有子节点
	 * @param {*} dataList 
	 * @param {*} id 
	 * @param {*} opt 
	 */
	function _hasChild(dataList, id, opt) {
		if (!opt.dataList || opt.dataList.length == 0) {
			return false;
		}

		for (let i = 0; i < opt.dataList.length; i++) {
			if (dataList[i][opt.pidField] == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查找子节点
	 * @param {*} dataList 
	 * @param {*} pid 
	 * @param {*} opt 
	 */
	function _findChild(dataList, id, opt) {
		let arr = [];
		if (!dataList || dataList.length == 0) {
			return arr;
		}
		for (let i = 0; i < dataList.length; i++) {
			if (dataList[i][opt.pidField] == id) {
				arr.push(dataList.splice(i, 1)[0]);
				i--;
			}
		}
		return arr;
	}

	/**
	 * 创建svg标签
	 * @param {*} tagName 
	 */
	function _createSVG(tagName) {
		return $(document.createElementNS(NAME_SPACE, tagName));
	}

	/**
	 * 获取滚动条div
	 * @param {*} jq 
	 */
	function _getScroll(jq) {
		return jq.find("div.lsm-scroll");
	}

	/**
	 * 获取菜单的容器
	 * @param {*} jq 
	 */
	function _getMenuContainer(jq) {
		return jq.find("div.lsm-container");
	}

	/**
	 * 初始化滚动条
	 * @param {*} jq 
	 */
	function _initScroll(jq) {
		_getScroll(jq).slimscroll({
			height: 'auto',
			position: 'right',
			railOpacity: 1,
			size: "5px",
			opacity: .4,
			color: '#fffafa',
			wheelStep: 5,
			touchScrollStep: 50
		});
	}

	/**
	 * 展开菜单
	 * @param {*} jq 
	 * @param {*} item 
	 */
	function _expandMenu(jq, item) {
		_initScroll(jq);
		item.siblings("li.lsm-sidebar-item").children('ul').slideUp(200);
		item.find('ul').slideDown(200);
		item.addClass('lsm-sidebar-show').siblings('li').removeClass('lsm-sidebar-show');
	}

	/**
	 * 折叠菜单
	 * @param {*} jq 
	 * @param {*} item 
	 */
	function _collapseMenu(jq, item) {
		_initScroll(jq);
		item.siblings("li.lsm-sidebar-item").children('ul').slideUp(200);
		item.find('ul').slideUp(200);
		item.removeClass('lsm-sidebar-show');
	}

	/**
	 * 初始化
	 */
	function _init(jq) {
		//lsm-mini
		
		$(document).on('mouseover','.lsm-mini .lsm-container ul:first>li',function(){
			$(".lsm-popup.third").hide();
			$(".lsm-popup.second").length == 0 && ($(".lsm-container").append("<div class='second lsm-popup lsm-sidebar'><div></div></div>"));
			$(".lsm-popup.second>div").html($(this).html());
			$(".lsm-popup.second").show();
			$(".lsm-popup.third").hide();
			var top = $(this).offset().top;
			var d = $(window).height() - $(".lsm-popup.second>div").height();
			if(d - top <= 0 ){
				top  = d >= 0 ?  d - 8 : 0;
			}
			$(".lsm-popup.second").stop().animate({"top":top}, 100);
		});
	
		$(document).on('mouseover','.second.lsm-popup.lsm-sidebar > div > ul > li',function(){
			if(!$(this).hasClass("lsm-sidebar-item")){
				$(".lsm-popup.third").hide();
				return;
			}
			$(".lsm-popup.third").length == 0 && ($(".lsm-container").append("<div class='third lsm-popup lsm-sidebar'><div></div></div>"));
			$(".lsm-popup.third>div").html($(this).html());
			$(".lsm-popup.third").show();
			var top = $(this).offset().top;
			var d = $(window).height() - $(".lsm-popup.third").height();
			if(d - top <= 0 ){
				top  = d >= 0 ?  d - 8 : 0;
			}
			$(".lsm-popup.third").stop().animate({"top":top}, 100);
		});
	
		$(document).on('mouseleave','.lsm-mini .lsm-container ul:first, .lsm-mini .slimScrollBar,.second.lsm-popup ,.third.lsm-popup',function(){
			$(".lsm-popup.second").hide();
			$(".lsm-popup.third").hide();
		});
	
		$(document).on('mouseover','.lsm-mini .slimScrollBar,.second.lsm-popup',function(){
			$(".lsm-popup.second").show();
		});
		$(document).on('mouseover','.third.lsm-popup',function(){
			$(".lsm-popup.second").show();
			$(".lsm-popup.third").show();
		});
	}
	
	//方法
	$.fn[pluginName].methods = {

	};
	
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {
			
		},
		/**
		 * 点击菜单执行
		 * @param item
		 * @param index
		 */
		onClickMenu : function(item, index) {
			
		}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		expandId : null,							//展开的id
		selectedId : null,							//选中的id
		rootPid : 0,
		idField : "id",
		pidField : "pid",
		textField : "text",
		iconField : "icon",
		ajaxParam : {
			dataType : "json"
		},
		dataList : [],
		loadFilter : function(data) {
			return data;
		}
	});
})(jQuery);