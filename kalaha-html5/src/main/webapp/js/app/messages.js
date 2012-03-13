KALAHA.messages = function() {
	
	this.init = function() {
		$("#messageBox").hide();
	};
	
	this.setMessage = function(msg) {
		$("#messageBox").empty();
		$("#messageBox").append(msg);
		$("#messageBox").show(250);
		window.setTimeout(function() {
			$("#messageBox").hide(250);
		}, 3000);
	};
}