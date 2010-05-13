// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

// Compose flash variables
var flashVars = "firebaseHost=" + __FIREBASE_HOST + "&firebasePort=" + __FIREBASE_PORT + "&userName=" + __USER_NAME + "&sessionToken=" + __SESSION_TOKEN + "&operatorId=" + __OPERATOR_ID + "&gameId=" + __GAME_ID;

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
		"width", "1023",
		"height", "100%",
		"align", "middle",
		"id", "KalahaRealClient",
		"quality", "high",
		"bgcolor", "#869ca7",
		"name", "KalahaRealClient",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "/facebook-web/KalahaRealClient",
			"width", "1023",
			"height", "100%",
			"align", "middle",
			"id", "KalahaRealClient",
			"flashVars", flashVars,
			"quality", "high",
			"bgcolor", "#869ca7",
			"name", "KalahaRealClient",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
}