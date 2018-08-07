/**
 * 对myPlugin 进行扩展了一些工具类
 */
$.myPlugin = {
	/**
	 * 显示
	 * @param jq
	 * @param option
	 * @returns
	 */
	show : function(jq, option) {
		var opt = jq.data("panel");
		if (!option) {
			$(jq).show();
		} else if (option.effect === "slide") {
			$(jq).slideDown(option.speed, option.callback);
		} else if (option.effect === "fade") {
			$(jq).fadeIn(option.speed, option.callback);
		} else {
			$(jq).show(option.speed, option.callback);
		}
	},
	/**
	 * 隐藏
	 * @param jq
	 * @param option
	 * @returns
	 */
	hide : function(jq, option) {
		var opt = jq.data("panel");
		if (!option) {
			$(jq).hide();
		} else if (option.effect === "slide") {
			$(jq).slideUp(option.speed, option.callback);
		} else if (option.effect === "fade") {
			$(jq).fadeOut(option.speed, option.callback);
		} else {
			$(jq).hide(option.speed, option.callback);
		}
	},
	/**
	 * 图片转为base64
	 */
	convertImgToBase64_bak : function(url, callback) {
		var canvas = document.createElement('CANVAS');
		var ctx = canvas.getContext('2d');
		var img = new Image;
		img.crossOrigin = '';
		img.src = url;
		img.onload = function() {
			canvas.height = img.height;
			canvas.width = img.width;
			ctx.drawImage(img, 0, 0, img.width, img.height);
			var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
			var dataURL = canvas.toDataURL("image/"+ext);
			callback.call(this, dataURL);
			//canvas = null; 
		};
	},
	/**
	 * 图片转为base64
	 */
	convertImgToBase64 : function(files, callback) {
		var file = $(files).get(0);
		if (!file || file.files.length == 0) {
			return;
		}
		var arr = [];
		var self = this;
		$(file.files).each(function(index, item) {
			var imgFile = new FileReader();
			imgFile.readAsDataURL(item);
			imgFile.onload = function () {
				var imgData = this.result; //base64数据
				//callback.call(this, imgData);
				arr.push(imgData);
			}
		});
		var interval = window.setInterval(function() {
			if (arr.length == file.files.length) {
				clearInterval(interval);
				callback.call(self, arr);
			}
		}, 100);
	}
};