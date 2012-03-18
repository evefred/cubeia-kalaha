KALAHA.game = function() {
	
	$.kalaha = this;
	
	var _instance = this;
	
	var _comm = new KALAHA.comm();
	
	var _messages = new KALAHA.messages();
	
	var _pits = new KALAHA.pits();
	
	var _playerOne = new KALAHA.player(1);
	var _playerTwo = new KALAHA.player(2);
	
	var _waitingPlayer;
	var _currentPlayer;
	
	var _firstState = true;
	var _gameEnded = false;
	
	this.isGameEnded = function() {
		return _gameEnded;
	};
	
	this.init = function() {
		_messages.init();
		// _comm.connect();
		this.switchPlayer();
		_pits.layout();
	};
	
	this.showWarning = function(msg) {
		_messages.setMessage(msg);
	};
	
	var _handleState = function(state) {
		console.log("State: " + state.houses);
		if(_firstState) {
			_firstState = false;
			var side = 1;
			if(state.northPlayerId == _comm.getPlayerId()) {
				side = 2;
			}
			$("#pits").show();
			_pits.setGameState(side, state.houses);
			if(state.playerToAct == _comm.getPlayerId()) {
				_instance.switchPlayer();
			}
		} else {
			// verify correct state here...
		}
	};
	
	var _handleSow = function(sow) {
		console.log("Sow: " + sow.house);
		if(sow.playerId != _comm.getPlayerId()) {
			_messages.setMessage("Player 1 sowed from house " + (sow.house + 1) + "!");
			_pits.remoteSow(7 - sow.house);
		}
	};
	
	var _handleEnd = function(end) {
		if(end.isDraw) {
			console.log("Game ended in draw!");
		} else {
			console.log("Game ended, winner is: " + end.winnerId);
		}
	};
	
	this.handleAction = function(packet) {
		var action = packet._action;
		if(action == "Sow") {
			_handleSow(packet);
		} else if(action == "State") {
			_handleState(packet);
		} else if(action == "End") {
			_handleEnd(packet);
		} else {
			console.log("Unknown action: " + action);
		}
	};
	
	this.getActingSide = function() {
		return _currentPlayer.boardSide;
	};
	
	this.reportMove = function(pit, lastPit) {
		_comm.sendMove(pit - 8);
	};
	
	this.moveFinished = function(pit, lastPit, showMsgs) {
		var doSwitch = true;
		/*if(_pits.checkPerformGameEnd(_instance.getActingSide())) {
			var winner = _pits.getLeader();
			if(winner != 0) {
				_messages.setMessage("Game ended! Player " + winner + " won!");
			} else {
				_messages.setMessage("Game ended in a draw! No one won!");
			}
			_playerOne.reset();
			_playerTwo.reset();
			_gameEnded = true;
			doSwitch = false;
		} */
		if(_currentPlayer.isPitHome(lastPit) && doSwitch) {
			if(showMsgs) {
				_messages.setMessage("It's your move again!");
			}
			doSwitch = false;
		} 
		if(_pits.checkPerformSteal(lastPit, _instance.getActingSide())) {
			if(showMsgs) {
				_messages.setMessage("Great Move! It's a steal!");
			}
		}
		if(doSwitch) {
			_instance.switchPlayer();
		}
	}
	
	this.switchPlayer = function() {
		if(!_currentPlayer) {
			_currentPlayer = _playerOne;
			_waitingPlayer = _playerTwo;
		} else {
			if(_currentPlayer == _playerOne) {
				_currentPlayer = _playerTwo;
				_waitingPlayer = _playerOne;
			} else {
				_currentPlayer = _playerOne;
				_waitingPlayer = _playerTwo;
			}
		}
		_waitingPlayer.setCurrent(false);
		_currentPlayer.setCurrent(true);
	};
}