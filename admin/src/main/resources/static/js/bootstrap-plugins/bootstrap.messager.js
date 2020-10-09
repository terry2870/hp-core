/**
 * 基于bootstrap的消息提示工具
 */
jQuery.messager = {
	/**
	 * 一个消息提示
	 * @param {*} param 
	 * 	width		宽
	 * 	height		高
	 * 	title		标题
	 * 	content		内容
	 * 	animateTime		动画显示和关闭持续时间（毫秒）
	 * 	showTime	消息显示时间（毫秒）
	 * 	callback	关闭后回调
	 */
	toast : function(param) {
		let toast = $("<div>").addClass("toast fade").appendTo($("body"));
		toast.attr({
			role : "alert",
			"aria-live" : "assertive",
			"aria-atomic" : true
		});
		toast.css({
			position : "absolute",
			bottom : "10px",
			right : "10px",
			"min-width" : "300px",
			height : "0px"
		});
		if (param.width) {
			toast.css("width", param.width);
		}
		if (param.height) {
			toast.css("height", param.height);
		}
		let header = $("<div>").addClass("toast-header bg-success").appendTo(toast);
		header.append($("<strong>").addClass("mr-auto text-light").html(param.title || "提示"));
		let closeBtn = $("<button>").appendTo(header);
		closeBtn.attr({
			type : "button",
			"aria-label" : "关闭",
			"data-dismiss" : "toast"
		}).addClass("ml-2 mb-1 close").append($("<span>").attr("aria-hidden", true).html("&times;"));

		let body = $("<div>").addClass("toast-body").appendTo(toast);
		body.html(param.content);

		toast.toast({
			autohide : false
		});
		toast.toast("show");
		toast.animate({
			"min-height" : "120px",
			height : param.height || "120px"
		}, param.animateTime || 500, function() {
			window.setTimeout(function() {
				toast.animate({
					"min-height" : "0px",
					height : "0px"
				}, param.animateTime || 500, function() {
					toast.remove();
				});
			}, param.showTime || 2000);
		});
		toast.on("hidden.bs.toast", function() {
			toast.remove();
			if (param.callback) {
				param.callback.call(toast);
			}
		});
	},
	/**
	 * 一个消息提示框
	 * @param {*} param 
	 * 	title		标题
	 * 	content		内容
	 * 	icon		图标(question；info；error；warning)
	 * 	callback	关闭后回调
	 */
	alert : function(param) {
		let div = $("<div>").appendTo($("body"));
		let msg = "";
		if (param.icon) {
			msg = "<div class='messager-icon messager-"+ param.icon +"'></div>";
			msg += param.content;
		} else {
			msg = param.content;
		}
		div.dialog({
			size : "sm",
			title : param.title || "消息",
			content : msg,
			center : true,
			buttons : [{
				text : "关闭",
				className : "danger",
				onClick : function() {
					div.dialog("close");
				}
			}],
			onAfterClose : function() {
				if (param.callback) {
					param.callback();
				}
			}
		});
	},
	/**
	 * 一个确认的消息框
	 * 	title		标题
	 * 	content		内容
	 * 	callback	选择确定后的回调
	 * @param {*} param 
	 */
	confirm : function(param) {
		let div = $("<div>").appendTo($("body"));
		let msg = "<div class='messager-icon messager-question'></div>";
		msg += param.content;
		div.dialog({
			size : "sm",
			title : param.title || "",
			content : msg,
			center : true,
			buttons : [{
				text : "确定",
				className : "success",
				onClick : function() {
					if (param.callback) {
						param.callback();
					}
					div.dialog("close");
				}
			}, {
				text : "关闭",
				className : "danger",
				onClick : function() {
					div.dialog("close");
				}
			}],
			footerStyle : {
				"justify-content" : "center"
			}
		});
	},
	/**
	 * 一个带有输入框的对话框
 	 *	title		标题
	 * 	content		内容
	 * 	callback(value)	选择确定后的回调（里面带有参数value，为输入的值）
	 * @param {*} param 
	 */
	prompt : function(param) {
		let div = $("<div>").appendTo($("body"));
		let mainDiv = $("<div>");
		let iconDiv = $("<div>").appendTo(mainDiv);
		iconDiv.append($("<div>").addClass("messager-icon messager-question"));
		iconDiv.append(param.content || "");

		let textDiv = $("<div>").appendTo(mainDiv);
		let text = $("<input>").addClass("form-control").appendTo(textDiv);
		
		div.dialog({
			size : "sm",
			title : param.title || "",
			content : mainDiv,
			center : true,
			buttons : [{
				text : "确定",
				className : "success",
				onClick : function() {
					if (param.callback) {
						param.callback(text.val());
					}
					div.dialog("close");
				}
			}, {
				text : "关闭",
				className : "danger",
				onClick : function() {
					div.dialog("close");
				}
			}],
			footerStyle : {
				"justify-content" : "center"
			}
		});
	},
	/**
	 * 一个滚动条
	 * @param {*} param 
	 * 	title			//标题
	 * 	interval		//周期
	 */
	progress : function(param) {
		if ($.type(param) == "string") {
			//执行关闭方法
			let jq = $("body").find("[creater='messager'].modal");
			if (!jq || jq.length == 0) {
				return;
			} else {
				jq.dialog("close");
			}
		} else {
			//创建进度条
			let div = $("<div>").attr({
				creater : "messager"
			}).appendTo($("body"));

			let content = $("<div>");
			if (param.content) {
				content.append($("<div>").html(param.content));
			}
			let progressBar = $("<div>");
			progressBar.progress({
				animate : true
			});
			content.append(progressBar);

			let interval = window.setInterval(function() {
				let value = progressBar.progress("value");
				if (!value) {
					value = 0;
				} else if (value == 100) {
					value = 0;
				} else {
					value = parseInt(value) + 10;
				}
				progressBar.progress("value", value);
			}, param.interval || 500);

			div.dialog({
				title : param.title,
				content : content,
				closeAble : false,
				center : true,
				onAfterClose : function() {
					clearInterval(interval);
				}
			});
		}
		
	}
};