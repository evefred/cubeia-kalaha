KALAHA.messages = function() {
	
	_instance = this;
	
	var _queue = [];
	
	this.init = function() {
		$("#messageBox").hide();
	};
	
	var _show = function() {
		var msg = _queue[0];
		$("#messageBox").empty();
		$("#messageBox").append(msg);
		$("#messageBox").show(250);
		window.setTimeout(function() {
			_queue.shift();
			$("#messageBox").hide(250);
			if(_queue.length > 0) {
				_show();
			}
		}, 3000);
	}
	
	this.queueMessage = function(msg) {
		var len = _queue.push(msg);
		console.log("Msg: " + msg);
		if(_queue.length == 1) {
			console.log("Show called; Queue length: " + len + "; Queue: " + _queue);
			_show();
		}
	};
}