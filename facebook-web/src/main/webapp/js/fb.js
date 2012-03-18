function renderFacebookNames() {
	/*$("[FBuserName]").each(function() {
		var id = $(this).attr("FBuserName");
		FB.api("/" + id, function(user) {
			$("[FBuserName=\"" + id + "\"]").append(user.name);
		});
	});*/
}

function openGame(gameId) {
	// $("#pits").show();
	// $("#pitsDivide").show();
	var game = new KALAHA.game(gameId);
	game.init();
}

$(document).ready(function() {
	$("#pits").hide();
	$("#messageBox").hide();
	$("#pitsDivide").hide();
});