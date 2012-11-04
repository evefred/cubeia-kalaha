function fbInvite() {
	FB.ui({
		method : 'apprequests',
		message : 'Come play Kalaha with me!!',
		filters : ['app_non_users']
	}, inviteCallback);
}

function fbChallenge() {
	FB.ui({
		method : 'apprequests',
		message : 'I dare you! Play Kahala with me!!',
		filters : ['app_users']
	}, challengeCallback);
}

function inviteCallback(response) {
	doCallback(response, 'INVITATION');
}

function challengeCallback(response) {
	doCallback(response, 'CHALLENGE');
}

function doCallback(response, type) {
	// alert(response.request + '_' + response.to);
	var json = JSON.stringify(response);
	var map = { };
	map['requestData'] = json;
	map['type'] = type;
	$.ajax({
		type: 'POST',
		url: '/Callback.action',
		data: map
	});
}