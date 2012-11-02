<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Welcome">
	<s:layout-component name="head">
	    <link rel="stylesheet" href="${contextPath}/css/app.css">
		<script type="text/javascript" src="${contextPath}/js/json2.js"></script>
		<script type="text/javascript" src="${contextPath}/js/cometd/cometd.js"></script>
		<script type="text/javascript" src="${contextPath}/js/cometd/jquery-cometd.js"></script>
		<script src="${contextPath}/js/fb/firebase-js-api-1.8.0-CE-javascript.js" type="text/javascript"></script>
		<script src="${contextPath}/js/fb/firebase-protocol-1.8.0-CE-javascript.js" type="text/javascript"></script>
		<script type="text/javascript" src="${contextPath}/js/app/ns.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/messages.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/player.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/pit.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/pits.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/comm.js"></script>
		<script type="text/javascript" src="${contextPath}/js/app/game.js"></script>
		<script>
			__session = "${actionBean.context.currentSession.id}";
			__operatorId = "${actionBean.context.operatorId}";
			__firebaseHost = "${actionBean.firebaseHost}";
			__firebasePort = "${actionBean.firebasePort}";
			__gameId = "${actionBean.gameId}";
			$(document).ready(function() {
				$("#pits").hide();
				// $("#messageBox").hide();
				var game = new KALAHA.game(__gameId);
				game.init();
			});
		</script>
	</s:layout-component>
	<s:layout-component name="body">
		<div class="row">
				<div class="span10">
					<p>Make your move below, or <a href="${contextPath}/Home.action">go back</a> to your list of games. </p>
			
					<div id="pits">
	
						<div id="pit1" class="pit"></div>
						<div id="pit2" class="pit"></div>
						<div id="pit3" class="pit"></div>
						<div id="pit4" class="pit"></div>
						<div id="pit5" class="pit"></div>
						<div id="pit6" class="pit"></div>
						<div id="pit7" class="pit"></div>
	
						<div id="pit8" class="pit homePit"></div>
						<div id="pit9" class="pit homePit"></div>
						<div id="pit10" class="pit homePit"></div>
						<div id="pit11" class="pit homePit"></div>
						<div id="pit12" class="pit homePit"></div>
						<div id="pit13" class="pit homePit"></div>
						<div id="pit14" class="pit"></div>
	
						<div id="boardSide1"></div>
						<div id="boardSide2"></div>
	
					</div>
	
					<div id="messageBox" ></div>
					
				</div>
		</div>
	</s:layout-component>
</s:layout-render>
