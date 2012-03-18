KALAHA.comm = function() {
	
	$.comm = this;
	
	var _instance = this;
	
	var _username;
	var _password;
	var _playerId;
	var _tableId;
	
	var _message = function(msg) {
		$("#events").append("<p class=\"event\">" + msg + "</p>");
	};
	
	var _packetCB = function(packet) {
		console.log("Packet: " + packet.classId);
	    switch (packet.classId) {
	        case FB_PROTOCOL.NotifyJoinPacket.CLASSID:
	            _notifyCB(packet.pid, true);
	            break;
	        case FB_PROTOCOL.NotifyLeavePacket.CLASSID:
	        	_notifyCB(packet.pid, false);
	        	break;
	        case FB_PROTOCOL.SeatInfoPacket.CLASSID :
	            // gameMessage("Player " + packet.player.pid + " is seated in seat " + packet.seat);
	            break;
	        case FB_PROTOCOL.JoinResponsePacket.CLASSID:
	            _joinCB(packet);
	            break;
	        case FB_PROTOCOL.GameTransportPacket.CLASSID:
	            _gameCB(packet);
	            break;
	        case FB_PROTOCOL.ServiceTransportPacket.CLASSID:
	            _serviceCB(packet);
	            break;
	    }
	};
	
	var _notifyCB = function(playerId, isJoin) {
		$.kalaha.playerOnline(playerId, isJoin);
	}
	
	var _joinCB = function(packet) {
		console.log("Join packet; status=" + packet.status + "; table=" + packet.tableid + "; seat=" + packet.seat);
		if(packet.status == "OK") {
			_tableId = packet.tableid;
			$("#gameDiv").hide();
		} else {
			_message("Join table failed!");
		}
	};
	
	var _gameCB = function(packet) {
		var byteArray = FIREBASE.ByteArray.fromBase64String(packet.gamedata);
	    var message = utf8.fromByteArray(byteArray);
		console.log("Game message: " + message);
		var resp = JSON.parse(message);
		$.kalaha.handleAction(resp);
	};
	
	var _serviceCB = function(packet) {
		var byteArray = FIREBASE.ByteArray.fromBase64String(packet.servicedata);
	    var message = utf8.fromByteArray(byteArray);
		var resp = JSON.parse(message);
		if(resp._action == "GetTableResponse") {
			console.log("Joining table: " + resp.tableId);
			_connector.joinTable(resp.tableId, -1);
		} else {
			console.log("Joining table: " + resp.gameId);
		}
	};
	
	var _lobbyCB = function lobbyCallback(packet) { };
	
	var _loginCB = function loginCallback(status, playerId, name) {
	    console.log("Login status " + status + " playerId " + playerId + " name " + name);
	    if (status === 'OK') {
	    	$("#loginDiv").hide();
	    	$("#gameDiv").show();
	    	$("#createBox").show();
	        _message("Login OK!");
	        _playerId = playerId;
	    } else {
	        _message("Login failed! " + status);
	    }
	};
	
	var _statusCB = function statusCallback(status) {
		console.log("Status received: " + status);
	    if (status === FIREBASE.ConnectionStatus.CONNECTED) {
	        _message('Connected to Firebase');
	        var pack = new FB_PROTOCOL.LoginRequestPacket();
	        pack.user = _username;
	        pack.password = _password;
	        pack.operatorid = 0;
	        _connector.sendProtocolObject(pack);
	    } else if (status === FIREBASE.ConnectionStatus.DISCONNECTED) {
	        _message('Disconnected from Firebase');
	    }
	};
	
	var _connector = new FIREBASE.Connector(_packetCB, _lobbyCB, _loginCB, _statusCB);
	
	var _haveWebSocket = function() {
		return (window.WebSocket && window.WebSocket.prototype && window.WebSocket.prototype.send);
	};
	
	this.getPlayerId = function() {
		return _playerId;
	};
	
	this.create = function(opponentId) {
		var pack = new FB_PROTOCOL.ServiceTransportPacket();
		pack.pid = _playerId;
		pack.seq = 0;
		pack.idtype = 0; // namespace
		pack.service = "net.kalaha:table";
		var req = { };
		req.userId = _playerId;
		req.correlationId = 0;
		req._action = "CreateGameRequest";
		req.opponentId = opponentId;
		var json = JSON.stringify(req);
		console.log("Sending: " + json);
		pack.servicedata = utf8.toByteArray(json);
		_connector.sendProtocolObject(pack);
	};
	
	this.sendMove = function(house) {
		var pack = { };
		pack._action = "Sow";
		pack.playerId = _playerId;
		pack.house = house;
		var json = JSON.stringify(pack);
		_connector.sendStringGameData(0, _tableId, json);
	};
	
	this.join = function(gameId) {
		var pack = new FB_PROTOCOL.ServiceTransportPacket();
		pack.pid = _playerId;
		pack.seq = 0;
		pack.idtype = 0; // namespace
		pack.service = "net.kalaha:table";
		var req = { };
		req.gameId = gameId;
		req.userId = _playerId;
		req.correlationId = 0;
		req._action = "GetTableRequest";
		var json = JSON.stringify(req);
		console.log("Sending: " + json);
		pack.servicedata = utf8.toByteArray(json);
		_connector.sendProtocolObject(pack);
	};
	
	this.connect = function(username, password) {
		_username = username;
		_password = password;
		if(_haveWebSocket()) {
			console.log("Connecing with WebSocket...");
			_connector.connect("FIREBASE.WebSocketAdapter", 'localhost', '8081', "/socket");
		} else {
			console.log("Connecing with CometD... ");
			_connector.connect("FIREBASE.CometdAdapter", 'localhost', '8081', "/cometd", false, function() { return $.cometd });
		}
	};
}