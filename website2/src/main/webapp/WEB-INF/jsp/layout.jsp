<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<s:layout-definition>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>${title}</title>
	<link rel="stylesheet" href="${contextPath}/css/bootstrap/bootstrap-2.0.3.css">
	<link rel="stylesheet" href="${contextPath}/css/main.css">
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="${contextPath}/js/bootstrap/bootstrap-2.0.3.js"></script>
	<script type="text/javascript" src="/js/fb.js"></script>
	<script type="text/javascript" src="/js/json2.js"></script>
	<s:layout-component name="head" />
</head>
<body>
	<div class="container">
		<div class="row main-row">
			<div class="span1">&nbsp;</div>
			<div class="span10">
			
				<div class="row fixed-height-100">
					<div class="span10 bottom-aligned">
						<h1>Kalaha With Friends</h1>
					</div>
				</div>
		
				<s:layout-component name="body" />
			</div>
			
			<div class="span1">&nbsp;</div>
	
		</div>
	</div>
	<div id="fb-root"></div>
	<script src="http://connect.facebook.net/en_US/all.js"></script>
	<script type="text/javascript">
		FB.init({
			appId : ${actionBean.context.facebookAppId},
			frictionlessRequests: true,
		});
	</script>
</body>
</html>
</s:layout-definition>