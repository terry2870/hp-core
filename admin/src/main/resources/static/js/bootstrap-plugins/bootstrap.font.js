jQuery.bootstrap_font = {};
jQuery.bootstrap_font.method = {
	/**
	 * 获取复选框中svg背景
	 * @param {*} param 
	 */
	getInputBgImageStyle : function(param) {
		/*let backgroundImage = jQuery.bootstrap_font.method.getSVGFontBgImage(param);
		if (!backgroundImage) {
			return {};
		}*/
		if (!param.font) {
			return {};
		}
		
		let obj = {
			"padding-right" : "calc(1.5em + 0.75rem)",
			"background-repeat" : "no-repeat",
			"background-position" : "right calc(0.375em + 0.1875rem) center",
			"background-size" : "calc(0.75em + 0.375rem) calc(0.75em + 0.375rem)",
			"background-image" : "url(../png/"+ param.font +".png)"
		};
		if (param.borderColor) {
			obj["border-color"] = param.borderColor;
		}
		return obj;
	},
	/**
	 * 获取背景图片的SVG font
	 * @param {*} param
	 */
	getSVGFontBgImage : function(param) {
		let svg = jQuery.bootstrap_font.method.getSVGFont(param);
		if (!svg) {
			return "";
		}
		return "url(\"data:image/svg+xml,"+ svg +"\")";
	},
	/**
	 * 获取svg的绘图
	 * @param {*} param
	 */
	getSVGFont : function(param) {
		if (!param || !param.font) {
			return "";
		}
		let width = param.width || "1rem";
		let height = param.height || "1rem";
		let viewBox = param.viewBox || "0 0 16 16";
		let fill = param.fill || "currentColor";
		let font = param.font;
		let str = "<svg xmlns='http://www.w3.org/2000/svg' width='"+ width +"' height='"+ height +"' viewBox='"+ viewBox +"' fill='"+ fill +"'>";
		str += font;
		str += "</svg>";
		return str;
	},
	
	
};