/**
 * 为easyui验证框增加验证方法
 */
$.extend($.fn.validatebox.defaults.rules, {
	/**
	 * 检查两次输入密码是否一致
	 */
	checkPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : "两次输入的密码必须一致！"
	},
	/**
	 * 检查手机号码（不为空才检查）
	 */
	checkMobile : {
		validator : function(value, param) {
			if (value == "") {
				return true;
			} else {
				var reg = /^1\d{10}$/;
				return reg.test(value);
			}
		},
		message : "请输入正确格式的手机号码！"
	},
	/**
	 * 检查电话号码（固定电话，不为空才检查）
	 */
	checkPhoneNum : {
		validator : function(value, param) {
			if (value == "") {
				return true;
			} else {
				var reg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
				return reg.test(value);
			}
		},
		message : "请输入正确格式的电话号码，区号和号码之间用-分开！"
	},
	/**
	 * 验证登录名
	 */
	checkLoginName : {
		validator : function(value, param) {
			return /^\w{4,30}$/.test(value);
		},
		message : "登录名只允许字母、数字和下划线组成，长度为4-30位"
	},
	/**
	 * 验证一般的名字
	 */
	checkName : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5\w]+$/.test(value);
		},
		message : "请输入正确的名称，不能包括特殊字符！"
	},
	/**
	 * 验证IP
	 */
	checkIp : {
		validator : function(value, param) {
			var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
			if (re.test(value)) {
				if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256)
					return true;
			}
			return false;
		},
		message : "IP地址输入有误！"
	},
	/**
	 * 验证端口
	 */
	checkPort : {
		validator : function(value, param) {
			if (value >= 0 && value <= 65535) {
				return true;
			}
			return false;
		},
		message : "Port（端口号）输入有误，请输入0到65535的数字！"
	},
	/**
	 * 时间比较（与当前时间比较）
	 */
	DateTimeCmpNow : {
		validator : function(value, param) {
			var date = Date.parse(value.replace(/-/g, "/"));
			var now = new Date();
			if (date > now) {
				return true;
			}
			return false;
		},
		message : "开始时间要大于当前时间！"
	},
	DateTimeCmp:{
		validator : function(value, param) {
			var edate = Date.parse(value.replace(/-/g, "/"));
			var bbdate = $(param[0]).datetimebox("getValue");
			var bdate = Date.parse(bbdate.replace(/-/g, "/"));
			if (edate>bdate){
				return true;
			}
			return false;
		},
		message : "结束时间要到大于开始时间！"
	},
	/**
	 * 检查密码
	 */
	pwdComplexDegree : {
		validator : function(value, param) {
			var reg = /^(?![0-9a-zA-Z]+$)(?![0-9a-zA-Z]+$)(?![0-9a-z\W]+$)(?![0-9A-Z\W]+$)(?![a-zA-Z\W]+$)[a-zA-Z0-9\W_]{8,20}$/;
			return reg.test(value);
		},
		message : "密码必须包含字母大小写、数字、特殊符号，且密码长度为8-20位"
	},
	checkProjectAccessories : {
		validator : function(value, param) {
			if (!value) {
				return false;
			}
			var reg = /^[\u4e00-\u9fa5#]+$/;
			return reg.test(value);
		},
		message : "只能输入汉字或#号符"
	}
});