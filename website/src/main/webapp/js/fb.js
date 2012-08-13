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
	}, inviteCallback);
}


function inviteCallback(response) {
	alert(response.request + '_' + response.to);
}