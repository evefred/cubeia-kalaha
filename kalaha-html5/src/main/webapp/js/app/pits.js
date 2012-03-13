KALAHA.pits = function() {

	var _instance = this;
	
	var _pits = {
		1 : new KALAHA.pit(1),
		2 : new KALAHA.pit(2),
		3 : new KALAHA.pit(3),
		4 : new KALAHA.pit(4),
		5 : new KALAHA.pit(5),
		6 : new KALAHA.pit(6),
		7 : new KALAHA.pit(7),
		8 : new KALAHA.pit(8),
		9 : new KALAHA.pit(9),
		10 : new KALAHA.pit(10),
		11 : new KALAHA.pit(11),
		12 : new KALAHA.pit(12),
		13 : new KALAHA.pit(13),
		14 : new KALAHA.pit(14)
	};
	
	// cheat for quick win below
	/*for (var i = 1; i < 15; i++) {
		_pits[i].stones = 0;
	}
	_pits[2].stones = 1;
	_pits[12].stones = 1;
	_pits[13].stones = 1;*/
	
	var _updateAllStones = function() {
		for (var i = 1; i <= 14; i++) {
			$("#pit" + i).empty();
			$("#pit" + i).append(_pits[i].stones);
		}
	};
	
	var _getHomePit = function(boardSide) {
		return (boardSide == 1 ? _pits[1] : _pits[14]);
	};
	
	var _getBoardSide = function(pit) {
		if(pit < 8) {
			return 1;
		} else {
			return 2;
		}
	};
	
	var _hasSideStones = function(side) {
		if(side == 1) {
			for (var i = 2; i < 8; i++) {
				if(_pits[i].stones > 0) {
					return true;
				}
			}
			return false;
		} else {
			for (var i = 8; i < 14; i++) {
				if(_pits[i].stones > 0) {
					return true;
				}
			}
			return false;
		}
	};
	
	var _countSide = function(side) {
		if(side == 1) {
			return _pits[1].stones;
		} else {
			return _pits[14].stones;
		}
	}
	
	this.getLeader = function() {
		var sideOne = _countSide(1);
		var sideTwo = _countSide(2);
		if(sideOne > sideTwo) {
			return 1;
		} else if(sideTwo > sideOne){
			return 2;
		} else {
			return 0;
		} 
	};
	
	this.checkPerformGameEnd = function(boardSide) {
		if(!_hasSideStones(boardSide)) {
			if(boardSide == 2) {
				var count = 0;
				for (var i = 2; i < 8; i++) {
					count += _pits[i].stones;
					_pits[i].stones = 0;
				}
				_pits[1].stones += count;
			} else {
				var count = 0;
				for (var i = 8; i < 14; i++) {
					count += _pits[i].stones;
					_pits[i].stones = 0;
				}
				_pits[14].stones += count;
			}
			return true;
		} else {
			return false;
		}
	};
	
	this.checkPerformSteal = function(lastPit, boardSide) {
		if(_getBoardSide(lastPit) == boardSide && _pits[lastPit].stones == 1) {
			var pit = _pits[lastPit];
			var oppositeId = pit.getOppositePitId();
			var opposite = _pits[oppositeId];
			var stones = opposite.stones;
			if(stones > 0) {
				var home = _getHomePit(boardSide);
				home.stones += 1 + stones;
				opposite.stones = 0;
				pit.stones = 0;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	};
	
	this.layout = function() {
		
		for (var i = 0; i < 7; i++) {
		
			var pitNo = i + 1;
			
			$("#pit" + pitNo).css("top", ((pitNo -1) * -27));
			$("#pit" + pitNo).css("right", ((pitNo -1) * -54));
			// $("#pit" + pitNo).append(_pits[pitNo].stones);
			
		}
		
		
		for (var i = 0; i < 7; i++) {
			
			var pitNo = i + 8;
			
			$("#pit" + pitNo).css("top", (((pitNo - 2) * -27) + 2));
			$("#pit" + pitNo).css("right", (((i + 1) - 1) * -54));
			// $("#pit" + pitNo).append(_pits[pitNo].stones);
			
		}
		
		_updateAllStones();
		
		$("#pit1").css("background-color", "lightgray");
		$("#pit14").css("background-color", "lightgray");
		
		for (var i = 1; i <= 14; i++) {
			
			$("#pit" + i).data("pit", _pits[i]);
			
			$("#pit" + i).click(function(event) {
				$(this).data("pit").handleClick();
				event.preventDefault();
				_updateAllStones();
			});
		}
	}
}