<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- include libraries(jQuery, bootstrap, fontawesome) -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
<script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<link href="resources/bootstrap/theme/blog.css" rel="stylesheet">
<!-- include summernote css/js-->
<link href="resources/summernote/summernote.css" rel="stylesheet">
<script src="resources/summernote/summernote.min.js"></script>
<script type="text/javascript" src="resources/summernote/lang/summernote-ko-KR.js"></script>
<script src="resources/summernote/plugin/summernote-ext-video.js"></script>
<title>글쓰기</title>
</head>
<body>
	<div class="blog-masthead">
		<div class="container row">
			<nav class="blog-nav">
				<a class="blog-nav-item active" href="main.jh">홈</a> <a
					class="blog-nav-item" href="#">New features</a> <a
					class="blog-nav-item" href="newText.jh">글쓰기</a> <a
					class="blog-nav-item" href="testsql.jh">Test SQL</a> <a
					class="blog-nav-item" href="todo.jh">할일</a>
			</nav>
		</div>
	</div>
	<div class="container">
	<div class="blog-header"></div>
	<!-- newText -->
	<div class="row">
		<div class="col-sm-8 blog-main">
			<form action="insertProcessing.jh" method="post">
				<div class="form-group">
					<input type="text" class="form-control" name="title" placeholder="제목">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="name" placeholder="작성자">
				</div>
				<div class="form-group">
					<textarea id="summernote" name="content"></textarea>
				</div>
				<button type="submit" class="btn btn-default">확인</button>
			</form>
		</div>
		<!-- End newText -->
		
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
		<!-- row -->
	</div>
	<!-- container -->
			<footer class="blog-footer">
				<p>
					Blog template built for <a href="http://getbootstrap.com">Bootstrap</a>
					by <a href="https://twitter.com/mdo">@mdo</a>.
				</p>
				<p>
					<a href="#">Back to top</a>
				</p>
			</footer>
	<script>
		$(function() {
			var curPath = window.location.pathname;
			var splittedcurPathName = curPath.split("/");
			var curPathName = splittedcurPathName[2];
			var curNavPathName = $('.blog-nav-item').attr('href');
			var navLength = $('.blog-nav').children().length;
			if(curNavPathName !== curPathName){
				$('.blog-nav-item.active').removeClass('active');
				$('.blog-nav-item[href="'+curPathName+'"]').addClass('active');
			}
			
			$('#summernote').summernote(
					{
						lang : [ 'en-US', 'ko-KR' ],
						toolbar : [
								[
										'small',
										[ 'bold', 'italic', 'underline',
												'clear', 'color',
												'strikethrough', 'superscript',
												'subscript', 'ul', 'ol',
												'picture', 'link', 'video' ] ],
								[
										'big',
										[ 'fontsize', 'paragraph', 'height',
												'table' ] ], ],
						focus : true,
						height : 250
					});
		});
	</script>
</body>
</html>