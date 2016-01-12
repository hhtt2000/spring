<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<title>EPL 순위</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="container">
	<div class="blog-header"></div>
	<div class="row">
		<div class="col-sm-8 blog-main">
			<div id="rank-table">잠시만 기다려주세요...</div>
		</div>
		<!-- End Ranking Table -->
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/bottom_script.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
		<script>
			$(function(){
				$.ajax({
					  headers: { 'X-Auth-Token': 'ce9e6d7c8647466ba8cea693181696d5' },
					  url: 'http://api.football-data.org/v1/soccerseasons/398/leagueTable',
					  dataType: 'json',
					  type: 'GET',
				}).done(function(response) {
					var tableTitle = "<h2 class='blog-title'>"+response.leagueCaption+"</h2>";
					var tableHead = "<tr>"
										+"<th>순위</th>"
										+"<th>팀</th>"
										+"<th>경기</th>"
										+"<th>승</th>"
										+"<th>무</th>"
										+"<th>패</th>"
										+"<th>득점</th>"
										+"<th>실점</th>"
										+"<th>GD</th>"
										+"<th>승점</th>"
									+"</tr>";
					var tableBody = "";
					var teams = response.standing;
					for(var teamNum in response.standing) {
						var team = teams[teamNum];
						tableBody += "<tr><td>"+team.position+"</td>"
										+"<td><img src='"+team.crestURI+"' height='28' width='21'> "+team.teamName+"</td>"
										+"<td>"+team.playedGames+"</td>"
										+"<td>"+team.wins+"</td>"
										+"<td>"+team.draws+"</td>"
										+"<td>"+team.losses+"</td>"
										+"<td>"+team.goals+"</td>"
										+"<td>"+team.goalsAgainst+"</td>"
										+"<td>"+team.goalDifference+"</td>"
										+"<td>"+team.points+"</td></tr>"
					}
					var totalTable = tableTitle
									+"<br />"
									+"<div class='table-responsive'>"
										+"<table class='table table-hover'>"
											+ tableHead
											+ tableBody
										+"</table>"
									+"</div>";
					$('#rank-table').html(totalTable);
				}).fail(function(){
					var errorMsg = "데이터를 불러오지 못했습니다.";
					$('#rank-table').html(errorMsg);
				}); 
			});
		</script>
</body>
</html>