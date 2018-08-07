jQuery.extend({

	/**
	 * 获取随机数
	 */
	getRandomNum : function(min, max) {
		var Range = max - min;
		var Rand = Math.random();
		return (min + Math.round(Rand * Range));
	},
	/**
	 * 加载依赖
	 * @param depends			依赖的项
	 * @param callback			回调函数
	 * @param checkFunction		验证函数
	 * @param interval			周期
	 */
	loadDepend : function(depends, callback, checkFunction, interval) {
		if (checkFunction) {
			if (checkFunction() === true) {
				callback();
			} else {
				setTimeout(function() {
					$.loadDepend(depends, callback, checkFunction, interval);
				}, interval || 100);
			}
		} else {
			if (!depends) {
				return;
			}
			if ($.type(depends) == "string") {
				depends = [depends];
			}
			var successNum = 0;
			
			//检查，每个依赖
			var check = function() {
				successNum = 0;
				for (var i = 0; i < depends.length; i++) {
					if ($(depends[i]).loadSuccess("isLoadSuccess")) {
						successNum++;
					} else {
						setTimeout(check, interval || 100);
					}
				}
			};
			//检查所有依赖是否通过
			var judage = function() {
				if (successNum == depends.length) {
					callback();
				} else {
					setTimeout(judage, interval || 100);
				}
			}
			check();
			judage();
		}
	}
});