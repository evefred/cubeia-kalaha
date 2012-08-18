function fbInvite() {
	FB.ui({
		method : 'apprequests',
		message : 'Come play Kalaha with me!',
		filters : ['app_non_users']
	}, inviteCallback);
}

function fbChallenge() {
	FB.ui({
		method : 'apprequests',
		message : 'I dare you! Play Kahala with me!',
		filters : ['app_users']
	}, challengeCallback);
}

function inviteCallback(response) {
	alert(response.request + '_' + response.to);
	var json = JSON.stringify(response);
	var map = { };
	map['response'] = json;
	map['type'] = 'INVITE';
	$.ajax({
		type: 'POST',
		url: '/facebook-web/request',
		data: map
	});
}

function challengeCallback(response) {
	// alert(response.request + '_' + response.to);
	var json = JSON.stringify(response);
	var map = { };
	map['response'] = json;
	map['type'] = 'CHALLENGE';
	$.ajax({
		type: 'POST',
		url: '/facebook-web/request',
		data: map
	});
}