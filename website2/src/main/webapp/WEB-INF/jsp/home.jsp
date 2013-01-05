<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Welcome">
	<s:layout-component name="body">
		<div class="row">
			<div class="span10">
				<a href="#" onclick="fbChallenge(); return false" class="btn btn-primary"><i class="icon-search icon-hand-up"></i> Challenge!</a> 
				<a href="#" onclick="fbInvite(); return false" class="btn btn-info"><i class="icon-search icon-user"></i> Invite!</a>
			</div>
		</div>
		<div class="row">
			<div class="span10 gamerow">
				<c:set var="games" value="${actionBean.myGames}" scope="page" />
				<c:choose>
					<c:when test="${not empty games}">
						<table class="table">
							<thead>
								<tr>
									<th>Opponent</th>
									<th>Created</th>
									<th>Last Move</th>
									<th>No. of Moves</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${games}" var="game" varStatus="loop">
									<tr>
										<td>${game.opponent}</td>
										<td>${game.created}</td>
										<td>${game.lastMove}</td>
										<td>${game.noMoves}</td>
										<td><a
											href="${contextPath}/Game.action?gameId=${game.id}">${game.status}</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<div class="alert alert-info">
    						Ooops, it appears you have no games in progress! Without any games there's not much
    						fun to have here. <a href="#" onclick="fbChallenge(); return false">Challenge</a> 
							a friend to play! Or if you're feeling lonely, <a href="#" onclick="fbInvite(); return false">invite</a>
							more friends to this splendid game!
    					</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row">
			<div class="span4">
				<div class="bottombox">
					<div class="boxtitle"><h5>Top Friends</h5></div>
					<div class="boxcontent">lll</div>
					<div class="boxbottom">More...</div>
				</div>
			</div>
			<div class="span3">
				<div class="bottombox">
					<div class="boxtitle"><h5>Finished Games</h5></div>
					<div class="boxcontent">lll</div>
					<div class="boxbottom"><a href="${contextPath}/Archive.action">More...</a></div>
				</div>
			</div>
			<div class="span3">
				<div class="bottombox">
					<div class="boxtitle"><h5>Challenges</h5></div>
					<div class="boxcontent">lll</div>
					<div class="boxbottom"><a href="${contextPath}/Pending.action">More...</a></div>
				</div>
			</div>
		</div>
	</s:layout-component>
</s:layout-render>
