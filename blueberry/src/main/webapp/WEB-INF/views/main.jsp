<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String pagingUrl = (String)request.getAttribute( "javax.servlet.forward.request_uri" );
	int splitIndex = pagingUrl.lastIndexOf('/');
	pagingUrl = pagingUrl.substring(0, splitIndex);
%>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<title>Grape</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<div class="container">

		<div class="blog-header">
			<h1 class="blog-title">블로그</h1>
			<p class="lead blog-description">필요한 것들을 주렁주렁</p>
		</div>

		<div class="row">

			<div class="col-sm-8 blog-main">
				<table class="table table-hover">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
					</tr>
					<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.id}</td>
						<td><a class="list-title" href="#post-position-${dto.id}">${dto.title}</a></td>
						<td>${dto.date}</td>
					</tr>
					</c:forEach>
				</table>
				<nav>
				  <ul class="pagination">
				  	<c:set value="<%=pagingUrl %>" var="pagingUrl" />
				    <li>
				      <a href="${pagingUrl}/${paging.prevPage}" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
				    	<c:choose>
					    	<c:when test="${paging.curPage == i}">
					    		<li class="disabled"><a href="${pagingUrl}/${i}">${i}</a></li>
					    	</c:when>
					    	<c:otherwise>
					    		<li><a href="${pagingUrl}/${i}">${i}</a></li>
					    	</c:otherwise>
				    	</c:choose>
				    </c:forEach>
				    <li>
				      <a href="${pagingUrl}/${paging.nextPage}" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				  </ul>
				</nav>
				<br/>
				
				<c:forEach items="${list}" var="dto">
					<div class="blog-post" id="post-position-${dto.id}">
						<h2 class="blog-post-title">${dto.title}</h2>
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-8">
								<p class="blog-post-meta" id="relative-time">
									<c:url value="/main/category/${dto.category}/1" var="categoryUrl" />
									 <small><a href="${categoryUrl}">${dto.category}</a> | <a href="#" class="time">${dto.date}</a>&nbsp;by <a href="#">${dto.name}</a></small>
								</p>
							</div>
							<c:set var="sessionId" value="${pageContext.request.userPrincipal.name}"/>
							<c:set var="curId" value="${dto.userid}"/>
							<c:if test="${sessionId eq curId}">
								<div class="col-xs-6 col-md-4" id="buttons-delete-update">
									<a class="btn btn-default btn-sm" href="<c:url value="/board/updateText/id/${dto.id}" />" role="button">수정</a>
									<a class="btn btn-warning btn-sm delete-btn" id="delete-btn-${dto.id}" href="<c:url value="/board/deleteText/id/${dto.id}" />" role="button">삭제</a>
								</div>
							</c:if>
						</div>
						<div class="blog-post-content">${dto.content}</div>
					</div>
				</c:forEach>
				<nav>
					<ul class="pager">
						<li><a href="#">Previous</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</nav>

			</div>
			<!-- /.blog-main -->
			
			<%@ include file="/WEB-INF/views/common/footer.jsp" %>
			<%@ include file="/WEB-INF/views/common/bottom_script.jsp" %>
			<%@ include file="/WEB-INF/views/common/self_script.jsp" %>	
			<script>
				$(function(){
					//게시물 날짜 표시 관련
					var timeLength = $('a.time').length;
					for(var i=0; i<timeLength; i++){
						var boardTime = $('.time').eq(i).text();
						var splitBoardTime = boardTime.split('-');
						var now = moment().format("YYYY-MM-DD");
						var splitNow = now.split('-');
						//오늘과의 날짜 차이
						var diff = moment(splitNow).diff(moment(splitBoardTime), 'years');
						//날짜 차이가 1년 이상이면
						if(diff >= 1){
							var time = moment(boardTime).locale('ko').format('LL');
							$('.time').eq(i).text(time);
						} else {
							var time = moment(boardTime).locale('ko').format('MMMM Do dddd');
							$('.time').eq(i).text(time);																		
						}	
					}
					//리스트에서 제목 클릭시 해당 글로 이동
					$('.list-title').on('click', function(e){
						e.preventDefault();
						
						var postId = $(this).attr('href');
						$('body, html').animate({
							scrollTop: $(postId).offset().top - 20
						}, 500);
					});
					//글삭제 버튼 클릭시 체크
					$('#buttons-delete-update .delete-btn').on('click', function(e){
						var deleteId = $(this).attr('id');
						deleteId = $('.'+deleteId);
						deleteId.checkDelete(e);
						
					});
					//페이징 관련
					$('.pagination .disabled').on('click', function(e){
						e.preventDefault();
					});
				});/* end of $(function) */
				//글삭제 확인 함수
				$.fn.checkDelete = function(e) {
					if(!confirm('삭제하시겠습니까?')){
						e.preventDefault();
						return false;
					}
				}
			</script>	
	</body>
</html>
