KALAHA.game = function(gameId) {
	
	if($.kalaha) {
		// previous instance
		$.kalaha.disconnect();
	}
	
	$.kalaha = this;
	
	var _gameId = gameId;
	
	var _instance = this;
	
	var _comm = new KALAHA.comm();
	
	var _messages = new KALAHA.messages();
	
	var _pits = new KALAHA.pits();
	
	var _playerOne = new KALAHA.player(1);
	var _playerTwo = new KALAHA.player(2);
	
	var _waitingPlayer;
	var _currentPlayer;
	
	var _southPlayerId;
	
	var _lastStateFromServer;
	
	var _firstState = true;
	var _gameEnded = false;
	
	this.isGameEnded = function() {
		return _gameEnded;
	};
	
	this.init = function() {
		_messages.init();
		_comm.connect();
		this.switchPlayer();
		_pits.layout();
	};
	
	this.getGameId = function() {
		return _gameId;
	}
	
	this.showWarning = function(msg) {
		_messages.queueMessage(msg);
	};
	
	var _handleState = function(state) {
		console.log("State: " + state.houses);
		_lastStateFromServer = state;
		if(_firstState) {
			_firstState = false;
			_updateState(state);
			_southPlayerId = state.southPlayerId;
			if(state.playerToAct == _comm.getPlayerId()) {
				_instance.switchPlayer();
			}
			$("#pits").show();
			$("#pitsDivide").show();
		} else {
			// verify correct state here...
		}
	};
	
	var _handleInfo = function(info) {
		console.log("Info: " + info);
		if(_comm.getPlayerId() == _southPlayerId) {
			console.log("Info 2: " + info.southPlayer.displayName);
			_playerTwo.displayName = info.southPlayer.displayName;
			_playerOne.displayName = info.northPlayer.displayName;
		} else {
			_playerTwo.displayName = info.northPlayer.displayName;
			_playerOne.displayName = info.southPlayer.displayName;
		}
		_playerOne.update();
		_playerTwo.update();
	};
	
	var _updateState = function(state) {
		var side = 1;
		if(state.northPlayerId == _comm.getPlayerId()) {
			side = 2;
		}
		$("#pits").show();
		_pits.setGameState(side, state.houses);
	}
	
	var _handleSow = function(sow) {
		console.log("Sow: " + sow.house);
		if(sow.playerId != _comm.getPlayerId()) {
			_messages.queueMessage("Player 1 sowed from house " + (sow.house + 1) + "!");
			_pits.remoteSow(7 - sow.house);
		}
	};
	
	var _handleEnd = function(end) {
		_gameEnded = true;
		_updateState(_lastStateFromServer);
		_playerOne.reset();
		_playerTwo.reset();
		if(end.isDraw || end.winnerId == -1) {
			_messages.queueMessage("Game ended in draw!");
		} else {
			_messages.queueMessage("Game ended, winner is: " + end.winnerId);
		}
	};
	
	this.playerOnline = function(playerId, isOnline) {
		/*if(isOnline) {
			_playerOne.setOnline();
		} else {
			_playerOne.setOffline();
		}*/
	}
	
	this.disconnect = function() {
		_comm.disconnect();
	}
	
	this.handleAction = function(packet) {
		var action = packet._action;
		if(action == "Sow") {
			_handleSow(packet);
		} else if(action == "State") {
			_handleState(packet);
		} else if(action == "End") {
			_handleEnd(packet);
		} else if(action == "Info") {
			_handleInfo(packet);
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
		if(_pits.checkIsGameEnd(_instance.getActingSide())) {
			doSwitch = false;
		} 
		if(_currentPlayer.isPitHome(lastPit) && doSwitch) {
			if(showMsgs) {
				_messages.queueMessage("It's your move again!");
			}
			doSwitch = false;
		} 
		if(_pits.checkPerformSteal(lastPit, _instance.getActingSide())) {
			if(showMsgs) {
				_messages.queueMessage("Great Move! It's a steal!");
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
		if(_currentPlayer == _playerTwo) {
			// $("#gameRowLink" + _gameId).empty();
			$("#gameRowLink" + _gameId).text("My Turn!");
		} else {
			// $("#gameRowLink" + _gameId).empty();
			$("#gameRowLink" + _gameId).text("Waiting...");
		}
	};
}