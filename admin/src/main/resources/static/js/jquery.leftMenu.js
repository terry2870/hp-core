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
		jq.addClass("nav-main");

		//生成伸缩条
		_createMenuTop(jq, opt);

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
	}

	/**
	 * 生成菜单顶部伸缩条
	 * @param {} jq 
	 * @param {} opt
	 */
	function _createMenuTop(jq, opt) {
		let navTop = $("<div>").addClass("nav-top").appendTo(jq);
		let miniDiv = $("<div id='mini'>").appendTo(navTop);
		let nimiImg = $("<img>").attr("src", opt.contextPath + "/image/mini.png").appendTo(miniDiv);
		nimiImg.click(function() {
			if (jq.hasClass("nav-mini")) {
				$(".nav-main.nav-mini .scroll-bar-warp").off("mouseover mouseout");
				jq.removeClass("nav-mini")
				_revert(jq);
			} else {
				jq.addClass("nav-mini");
				_mini(jq);
				$(".nav-main.nav-mini .scroll-bar-warp").on("mouseover", function() {
					_revert(jq);
				});
				$(".nav-main.nav-mini .scroll-bar-warp").on("mouseout", function() {
					_mini(jq);
				});
			}
		});
	}

	/**
	 * 生成左侧菜单
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createLeftMenu(jq, opt) {
		let scrollBarWarp = $("<div>").addClass("scroll-bar-warp").appendTo(jq);
		let ul = _createUL(jq, opt, opt.rootPid);
		scrollBarWarp.append(ul);

		//虚拟的滚动条
		_initScroll(jq);

		//默认展开
		if (opt.expandId !== undefined && opt.expandId !== null) {
			//_getMenuContainer(jq).find("li.lsm-sidebar-item[menuid="+ opt.expandId +"]>a").click();
		}
	}

	/**
	 * 生成子节点
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
		let ul = $("<ul>").addClass("nav-menu scroll-bar");

		if (pid == opt.rootPid) {
			ul.show();
		} else {
			ul.hide();
		}
		for (let i = 0; i < childNode.length; i++) {
			let node = childNode[i];
			let li = $("<li>").addClass("nav-item").attr({
				menuid : node[opt.idField],
				pid : node[opt.pidField]
			}).appendTo(ul);

			let a = $("<a>").attr("href", "javascript:;").appendTo(li);
			a.click(function() {

				if ($(this).next().css('display') == "none") {
					_expandMenu(jq, $(this).parent());
				} else {
					_collapseMenu(jq, $(this).parent());
				}
			});
			//菜单图标
			let menuIcon = createSVGIcon(node[opt.iconField], opt.contextPath, {
				class : "nav-icon",
				fill : "currentColor"
			})
			a.append(menuIcon);

			//菜单名称
			a.append($("<span>").text(node[opt.textField]));

			//检查是否有子节点
			let subChildNode = _findChild(opt.dataList, node[opt.idField], opt);
			if (!subChildNode || subChildNode.length == 0) {
				continue;
			}

			//more icon
			let moreIcon = createSVGIcon("chevron-right", opt.contextPath, {
				class : "nav-more",
				fill : "currentColor"
			});
			a.append(moreIcon);

			let subUl = $("<ul>").appendTo(li);
			//生成子菜单
			$(subChildNode).each(function(index, item) {
				let subLi = $("<li>").appendTo(subUl);
				subLi.attr("menuid", item[opt.idField]);
				let subA = $("<a>").attr("href", "javascript:;").appendTo(subLi);
				subA.append($("<span>").addClass("sub-menu").text(item[opt.textField]));

				subA.click(function() {
					jq.find(".nav-menu .nav-active").removeClass("nav-active");
					$(this).addClass("nav-active");
					opt.onClickMenu.call(jq, item, index);
				});

				if (opt.selectedId && opt.selectedId == item[opt.idField]) {
					subA.addClass("nav-active");
				}
			});
			
			if (opt.expandId && node[opt.idField] == opt.expandId) {
				//展开
				_expandMenu(jq, li);
			}
		}

		return ul;
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
	 * 获取滚动条div
	 * @param {*} jq 
	 */
	function _getScroll(jq) {
		return jq.find("ul.scroll-bar");
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
	 * @param {*} item (li 对象)
	 */
	function _expandMenu(jq, item) {
		item.parent().find('ul').slideUp(300);
		item.find('ul').slideDown(300);
		item.addClass('nav-show').siblings('li').removeClass('nav-show');
	}

	/**
	 * 收起菜单
	 * @param {*} jq 
	 * @param {*} item  (li 对象)
	 */
	function _collapseMenu(jq, item) {
		item.find('ul').slideUp(300);
		item.removeClass("nav-show");
	}

	/**
	 * 收起所有
	 * @param {*} jq 
	 */
	function _collapseAll(jq) {
		jq.find('ul.nav-menu li.nav-item ul').slideUp(300);
	}

	/**
	 * 设置为mini
	 * @param {*} jq 
	 */
	function _mini(jq) {
		jq.find(".nav-item>a span").hide();
		jq.find(".nav-more use").hide();
		_setWidth(jq, "60px");
	}
	
	/**
	 * 还原
	 * @param {*} jq 
	 */
	function _revert(jq) {
		jq.find(".nav-item>a span").show();
		jq.find(".nav-more use").show();
		_setWidth(jq, "220px");
	}
	
	
	function _setWidth(jq, width) {
		jq.css({
			width : width,
			"max-width" : width
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
		},
		contextPath : ""
	});
})(jQuery);