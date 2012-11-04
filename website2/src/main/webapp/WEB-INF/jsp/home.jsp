<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Welcome">
	<s:layout-component name="body">
		<div class="row">
				<div class="span10">
					<p>
						What's that below? It's your current games, stupid! You don't have
						any, just go ahead and <a href="#" onclick="fbChallenge(); return false">challenge</a> 
						someone. Don't have any friends here? Not to worry, just 
						<a href="#" onclick="fbInvite(); return false">invite</a> them. 
					</p>
				</div>
		</div>
		<div class="row">
				<div class="span10">
				
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
							
							<c:set var="games" value="${actionBean.myGames}" scope="page"/>
						
							<c:forEach items="${games}" var="game" varStatus="loop">
							
								<tr>
									<td>${game.opponent}</td>
									<td>${game.created}</td>
									<td>${game.lastMove}</td>
									<td>${game.noMoves}</td>
									<td><a href="${contextPath}/Game.action?gameId=${game.id}">${game.status}</a></td>
								</tr>
							
							</c:forEach>
						
							
						</tbody>
					</table>
				</div>
			</div>
		<div class="row">
			<div class="span10">
				You looking for your finished games? No problem, have a look in the
				<a href="/Archive.action">archive</a>!
			</div>
		</div>
		<div class="row">
			<div class="span10">
				Of course, you also have sent a lot of invites and challenges? Right?! Of course 
				you have, and <a href="/Pending.action">here</a> they are!
			</div>
		</div>
	</s:layout-component>
</s:layout-render>
