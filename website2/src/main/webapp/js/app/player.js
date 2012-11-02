KALAHA.player = function(boardSide) {
	
	var _instance = this;
	
	this.boardSide = boardSide;
	
	this.displayName = "Player " + _instance.boardSide;
	
	var _current = false;
	
	this.update = function() {
		if(_current) {
			$("#boardSide" + _instance.boardSide).empty();
			$("#boardSide" + _instance.boardSide).append(_instance.displayName); // + ": Acting!");
			$("#boardSide" + _instance.boardSide).addClass("playerTurn");
		} else {
			$("#boardSide" + _instance.boardSide).empty();
			$("#boardSide" + _instance.boardSide).append(_instance.displayName); //  + ": Waiting..."); 
			$("#boardSide" + _instance.boardSide).removeClass("playerTurn");
		}
	}
	
	this.setCurrent = function(current) {
		_current = current;
		_instance.update();
	};
	
	this.setOnline = function() {
		// $("#boardSide" + _instance.boardSide).addClass("playerOnline");
	}
	
	this.setOffline = function() {
		// $("#boardSide" + _instance.boardSide).removeClass("playerOnline");
	}
	
	this.reset = function() {
		$("#boardSide" + _instance.boardSide).empty();
		$("#boardSide" + _instance.boardSide).append(_instance.displayName);
	}
	
	this.isPitHome = function(pitNo) {
		var myPitHome = (this.boardSide == 1 ? 1 : 14);
		return myPitHome == pitNo;
	};
}