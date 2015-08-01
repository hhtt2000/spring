<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<%@ include file="/WEB-INF/views/common/summernote.jsp" %>
<title>글쓰기</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="container">
	<div class="blog-header"></div>
	<!-- newText -->
	<div class="row">
		<div class="col-sm-8 blog-main">
			<form action="insertProcess" method="post">
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
		
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
	
</body>
</html>