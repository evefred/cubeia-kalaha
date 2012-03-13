KALAHA.game = function() {
	
	var _instance = this;
	
	$.kalaha = this;
	
	var _messages = new KALAHA.messages();
	
	var _pits = new KALAHA.pits();
	
	var _playerOne = new KALAHA.player(1);
	var _playerTwo = new KALAHA.player(2);
	
	var _waitingPlayer;
	var _currentPlayer;
	
	var _gameEnded = false;
	
	this.isGameEnded = function() {
		return _gameEnded;
	};
	
	this.init = function() {
		_messages.init();
		this.switchPlayer();
		_pits.layout();
	};
	
	this.showWarning = function(msg) {
		_messages.setMessage(msg);
	};
	
	this.getActingSide = function() {
		return _currentPlayer.boardSide;
	};
	
	this.reportMove = function(pit, lastPit) {
		var doSwitch = true;
		if(_pits.checkPerformGameEnd(_instance.getActingSide())) {
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
		} 
		if(_currentPlayer.isPitHome(lastPit) && doSwitch) {
			_messages.setMessage("It's your move again!");
			doSwitch = false;
		} 
		if(_pits.checkPerformSteal(lastPit, _instance.getActingSide())) {
			_messages.setMessage("Great Move! It's a steal!");
		}
		if(doSwitch) {
			_instance.switchPlayer();
		}
	};
	
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