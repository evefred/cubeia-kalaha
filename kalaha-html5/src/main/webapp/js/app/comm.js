KALAHA.comm = function() {
	
	$.comm = this;
	
	var _instance = this;
	
	var _username;
	var _password;
	var _playerId;
	
	var _message = function(msg) {
		$("#events").append("<p class=\"event\">" + msg + "</p>");
	}
	
	var _packetCB = function(packet) {
		console.log("Packet: " + packet.classId);
	    switch (packet.classId) {
	        case FB_PROTOCOL.NotifyJoinPacket.CLASSID:
	            // gameMessage("Player " + packet.pid + " joined");
	            break;
	        case FB_PROTOCOL.NotifyLeavePacket.CLASSID:
	            // gameMessage("Player " + packet.pid + " left");
	            break;
	        case FB_PROTOCOL.SeatInfoPacket.CLASSID :
	            // gameMessage("Player " + packet.player.pid + " is seated in seat " + packet.seat);
	            break;
	        case FB_PROTOCOL.JoinResponsePacket.CLASSID:
	            // _message("Join response: " + packet.status);
	            break;
	        case FB_PROTOCOL.GameTransportPacket.CLASSID:
	            _gameCB(packet);
	            break;
	        case FB_PROTOCOL.ServiceTransportPacket.CLASSID:
	            _serviceCB(packet);
	            break;
	    }
	}
	
	var _gameCB = function(packet) {
		var byteArray = FIREBASE.ByteArray.fromBase64String(packet.gamedata);
	    var message = utf8.fromByteArray(byteArray);
		// var resp = JSON.parse(message);
		console.log("Game message: " + message);
	}
	
	var _serviceCB = function(packet) {
		var byteArray = FIREBASE.ByteArray.fromBase64String(packet.servicedata);
	    var message = utf8.fromByteArray(byteArray);
		var resp = JSON.parse(message);
		console.log("Joining table: " + resp.tableId);
		_connector.joinTable(resp.tableId, 0);
	}
	
	var _lobbyCB = function lobbyCallback(packet) { };
	
	var _loginCB = function loginCallback(status, playerId, name) {
	    console.log("Login status " + status + " playerId " + playerId + " name " + name);
	    if (status === 'OK') {
	    	$("#loginDiv").hide();
	    	$("#gameDiv").show();
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
	}
	
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
	}
	
	this.connect = function(username, password) {
		_username = username;
		_password = password;
		if(_haveWebSocket()) {
			console.log("Connecing with WebSocket...");
			_connector.connect("FIREBASE.WebSocketAdapter", 'localhost', '8081', "/socket");
		} else {
			console.log("Connecing with CometD... LATER!");
			// _connector.connect("FIREBASE.CometdAdapter", 'localhost', '8081', "/cometd");
		}
	}
}