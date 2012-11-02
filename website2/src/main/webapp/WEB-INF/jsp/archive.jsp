<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Welcome">
	<s:layout-component name="body">
		<div class="row">
				<div class="span10">
					<p>
						What's that below? It's your old games, stupid! There's not much
						to do with them except gloat, so here a link <a href="${contextPath}/Home.action">back</a> for your convenience.
					</p>
				</div>
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
									<td>${game.winner}</td>
								</tr>
							
							</c:forEach>
						
							
						</tbody>
					</table>
				
				</div>
		</div>
	</s:layout-component>
</s:layout-render>
