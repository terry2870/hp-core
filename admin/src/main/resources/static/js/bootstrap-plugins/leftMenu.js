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

		let label = $("<label>").append($("<input>").attr({
			type : "checkbox",
			checked : "checked"
		}));

		let svg = _createSVG("svg");
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
		_init();
	}

	function _createSVG(tagName) {
		return $(document.createElementNS(NAME_SPACE, tagName));
	}

	function _init() {
		$('.lsm-scroll').slimscroll({
			height: 'auto',
			position: 'right',
			railOpacity: 1,
			size: "5px",
			opacity: .4,
			color: '#fffafa',
			wheelStep: 5,
			touchScrollStep: 50
		});
		$('.lsm-container ul ul').css("display", "none");
		// lsm-sidebar收缩展开
		$('.lsm-sidebar a').on('click',function(){
			$('.lsm-scroll').slimscroll({
				height: 'auto',
				position: 'right',
				size: "8px",
				color: '#9ea5ab',
				wheelStep: 5,
				touchScrollStep: 50
			});
			if (!$('.left-side-menu').hasClass('lsm-mini')) {
				$(this).parent("li").siblings("li.lsm-sidebar-item").children('ul').slideUp(200);
				if ($(this).next().css('display') == "none") {
					//展开未展开
					// $('.lsm-sidebar-item').children('ul').slideUp(300);
					$(this).next('ul').slideDown(200);
					$(this).parent('li').addClass('lsm-sidebar-show').siblings('li').removeClass('lsm-sidebar-show');
				}else{
					//收缩已展开
					$(this).next('ul').slideUp(200);
					//$('.lsm-sidebar-item.lsm-sidebar-show').removeClass('lsm-sidebar-show');
					$(this).parent('li').removeClass('lsm-sidebar-show');
				}
			}
		});
		//lsm-mini
		$('.lsm-mini-btn svg').on('click',function(){
			if ($('.lsm-mini-btn input[type="checkbox"]').prop("checked")) {
				$('.lsm-sidebar-item.lsm-sidebar-show').removeClass('lsm-sidebar-show');
				$('.lsm-container ul').removeAttr('style');
				$('.left-side-menu').addClass('lsm-mini');
				$('.left-side-menu').stop().animate({width : 60},200);
			}else{
				$('.left-side-menu').removeClass('lsm-mini');
				$('.lsm-container ul ul').css("display", "none");
				$('.left-side-menu').stop().animate({width: 240},200);
			}
	
		});
	
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
		
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		
	});
})(jQuery);