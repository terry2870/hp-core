var CODE_SUCCESS = 200;
$.ajaxSetup({
	cache : false,
	dataType : "json",
	type : "POST",
	complete : function(XMLHttpRequest, textStatus) {
		window.top.$.messager.progress("close");
		var status = XMLHttpRequest.status;
		if (status == 200) {
			return;
		}
		if (status == 900) {
			//数据库超时
			window.top.$.messager.alert("失败", "查询超时了", "error");
		} else if (status == 901) {
			//session超时
			window.top.$.messager.alert("错误", "登录超时了！", "error", function() {
				window.top.location.href = "/logout";
			});
		} else {
			//其他异常
			var text = XMLHttpRequest.responseText;
			text = JSON.parse(text);
			window.top.$.messager.alert("失败", text.message, "error");
		}
	}
});

//通用的LoadFilter
function defaultLoadFilter(data) {
	if ($.type(data) == "array") {
		return data;
	}
	if (!data) {
		return [];
	}
	if (data.code) {
		return data.data || [];
	} else {
		return data || [];
	}
}
