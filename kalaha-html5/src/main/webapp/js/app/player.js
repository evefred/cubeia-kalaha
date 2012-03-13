KALAHA.player = function(boardSide) {
	
	var _instance = this;
	
	this.boardSide = boardSide;
	
	this.setCurrent = function(current) {
		if(current) {
			$("#boardSide" + _instance.boardSide).empty();
			$("#boardSide" + _instance.boardSide).append("Player " + _instance.boardSide + ": Acting!");
		} else {
			$("#boardSide" + _instance.boardSide).empty();
			$("#boardSide" + _instance.boardSide).append("Player " + _instance.boardSide + ": Waiting..."); 
		}
	};
	
	this.reset = function() {
		$("#boardSide" + _instance.boardSide).empty();
		$("#boardSide" + _instance.boardSide).append("Player " + _instance.boardSide);
	}
	
	this.isPitHome = function(pitNo) {
		var myPitHome = (this.boardSide == 1 ? 1 : 14);
		return myPitHome == pitNo;
	};
}