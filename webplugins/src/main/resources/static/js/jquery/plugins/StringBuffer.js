function StringBuffer(str){
	this._strings = [];
	if(str != undefined && str != null && str != ""){
		this._strings.push(str);
	}
}

StringBuffer.prototype.append = function(str){
	this._strings.push(str);
	return this;
}

StringBuffer.prototype.toString = function(){
	return this._strings.join("");
}

