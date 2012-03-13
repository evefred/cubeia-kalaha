KALAHA.pit = function(pitId) {
	
	this.stones = 6;
	
	this.id = pitId;
	
	var _isSelected = false;
	var _instance = this;

	var _getPitIdForDistance = function(distance) {
		var next = _instance;
		for (var i = 0; i < distance; i++) {
			var nextId = next.nextPitId();
			next = _getPit(nextId);
		}
		return next.id;
	};
	
	var _getPit = function(pitId) {
		return $("#pit" + pitId).data("pit");
	};
	
	var _resetAllBorders = function() {
		for (var i = 0; i < 14; i++) {
			_getPit(i + 1).reset();
		}
	};
	
	var _inTurn = function() {
		if(_instance.id < 8) {
			return $.kalaha.getActingSide() == 1;
		} else {
			return $.kalaha.getActingSide() == 2;
		}
	}
	
	var _visitNextPits = function(visitor) {
		var lastPit = _instance.id;
		for (var i = 0; i < _instance.stones; i++) {
			var distance = i + 1;
			var nextPitId = _getPitIdForDistance(distance);
			var pit = _getPit(nextPitId);
			visitor(pit);
			lastPit = nextPitId;
		}
		return lastPit;
	};
	
	this.getOppositePitId = function() {
		var myId = _instance.id;
		if(myId < 8) {
			return 6 + myId;
		} else {
			return myId - 6;
		}
	};
	
	this.nextPitId = function() {
		if(_instance.id === 1) {
			return 8;
		} else if(_instance.id < 8) {
			return _instance.id - 1;
		} else if(_instance.id < 14) {
			return _instance.id + 1; 
		} else {
			return 7;
		}
	};
	

	this.reset = function() {
		_instance.setBorder("black");
		_isSelected = false;
	}
	
	this.setBorder = function(color) {
		$("#pit" + _instance.id).css("border", "1px solid " + color);
	}
	
	this.handleClick = function() {
		if(_instance.stones === 0 || _instance.id === 1 || _instance.id === 14 || $.kalaha.isGameEnded()) {
			_resetAllBorders();
			return; 
		}
		if(!_inTurn()) {
			$.kalaha.showWarning("It's not your turn to move buddy!")
			_resetAllBorders();
			return;  
		}
		if(_isSelected) {
			// this is a move
			_resetAllBorders();
			_isSelected = false;
			var lastPit = _visitNextPits(function(pit) {
				pit.stones = pit.stones + 1;
			});
			_instance.stones = 0;
			$.kalaha.reportMove(_instance.id, lastPit);
		} else {
			_resetAllBorders();
			_instance.setBorder("blue");
			_isSelected = true;
			_visitNextPits(function(pit) {
				pit.setBorder("green");
			});
		}
	};
}