<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Grape</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link href="${pageContext.servletContext.contextPath}/resources/bootstrap/theme/blog.css" rel="stylesheet">
<script src="${pageContext.servletContext.contextPath}/resources/momentjs/moment-with-locales.js"></script>

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="../../assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.form-login {
	padding-top: 20px;
}
</style>
</head>

<body>

	<div class="blog-masthead">
		<div class="container row">
			<nav class="blog-nav">
				<a class="blog-nav-item active" href="${pageContext.servletContext.contextPath}/main">홈</a> <a
					class="blog-nav-item" href="#">New features</a> <a
					class="blog-nav-item" href="${pageContext.servletContext.contextPath}/newText">글쓰기</a> <a
					class="blog-nav-item" href="${pageContext.servletContext.contextPath}/testsql">Test SQL</a> <a
					class="blog-nav-item" href="${pageContext.servletContext.contextPath}/todo">할일</a>
			</nav>
		</div>
	</div>

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
						<th>날짜</th>
					</tr>
					<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.id}</td>
						<td><a href="#">${dto.title}</a></td>
						<td>${dto.date}</td>
					</tr>
					</c:forEach>
				</table>
				<c:forEach items="${list}" var="dto">
					<div class="blog-post">
						<h2 class="blog-post-title">${dto.title}</h2>							
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-8">
								<p class="blog-post-meta" id="relative-time">
									 <a href="#" class="time">${dto.date}</a>&nbsp;by <a href="#">${dto.name}</a>
								</p>
							</div>
							<div class="col-xs-6 col-md-4" id="buttons-delete-update">
								<a class="btn btn-default btn-sm" href="updateText/${dto.id}" role="button">수정</a>
								<a class="btn btn-warning btn-sm" href="deleteText/${dto.id}" role="button">삭제</a>
							</div>
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

			<div class="col-sm-3 col-sm-offset-1 blog-sidebar">
				<div class="sidebar-module sidebar-module-inset">
					<form class="form-horizontal form-login">
					  <div class="form-group">
					    <div class="col-sm-offset-1 col-sm-10">
					      <input type="text" class="form-control" name="id" placeholder="Id">
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-sm-offset-1 col-sm-10">
					      <input type="password" class="form-control" name="password" placeholder="Password">
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-sm-offset-6 col-sm-10">
					      <button type="submit" class="btn btn-default">Sign in</button>
					    </div>
					  </div>
					</form>
				</div>
				<div class="sidebar-module">
					<h4>Archives</h4>
					<ol class="list-unstyled">
						<li><a href="#">March 2014</a></li>
						<li><a href="#">February 2014</a></li>
						<li><a href="#">January 2014</a></li>
						<li><a href="#">December 2013</a></li>
						<li><a href="#">November 2013</a></li>
						<li><a href="#">October 2013</a></li>
						<li><a href="#">September 2013</a></li>
						<li><a href="#">August 2013</a></li>
						<li><a href="#">July 2013</a></li>
						<li><a href="#">June 2013</a></li>
						<li><a href="#">May 2013</a></li>
						<li><a href="#">April 2013</a></li>
					</ol>
				</div>
				<div class="sidebar-module">
					<h4>Elsewhere</h4>
					<ol class="list-unstyled">
						<li><a href="https://github.com/">GitHub</a></li>
						<li><a href="#">Twitter</a></li>
						<li><a href="#">Facebook</a></li>
					</ol>
				</div>
			</div>
			<!-- /.blog-sidebar -->

		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<footer class="blog-footer">
		<p>
			Blog template built for <a href="http://getbootstrap.com">Bootstrap</a>
			by <a href="https://twitter.com/mdo">@mdo</a>.
		</p>
		<p>
			<a href="#">Back to top</a>
		</p>
	</footer>


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
	<script>
		$(function() {
			var curPath = window.location.pathname;
			var splittedcurPathName = curPath.split("/");
			var curPathName = splittedcurPathName[splittedcurPathName.length - 1];
			var splittedNavPathName = $('.blog-nav-item').attr('href').split("/");
			var curNavPathName = splittedNavPathName[splittedNavPathName.length - 1];
			var navLength = $('.blog-nav').children().length;
			if(curNavPathName !== curPathName){
				$('.blog-nav-item.active').removeClass('active');
				$('.blog-nav-item[href="'+curPath+'"]').addClass('active');
			}
			var timeLength = $('a.time').length;
			for(var i=0; i<timeLength; i++){
				var boardTime = $('.time').eq(i).text();
				var time = moment().locale('ko').calendar(boardTime);
				$('.time').eq(i).text(time);								
			}
		});
	</script>
</body>
</html>
