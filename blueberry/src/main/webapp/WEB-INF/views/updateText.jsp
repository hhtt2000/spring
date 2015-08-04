<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<%@ include file="/WEB-INF/views/common/summernote.jsp" %>
<title>글수정</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="container">
	<div class="blog-header"></div>
	<!-- newText -->
	<div class="row">
		<div class="col-sm-8 blog-main">
			<form:form action="${pageContext.servletContext.contextPath}/updateProcess" commandName="boardDto" method="post">
				<div class="form-group">
					<form:input class="form-control" type="hidden" path="id"/>
				</div>
				<div class="form-group">
					<form:input class="form-control" type="text" path="title" placeholder="제목" autofocus="autofocus"/>
				</div>
				<div class="form-group">
					<form:input class="form-control" type="text" path="name" placeholder="작성자" readonly="true"/>
				</div>
				<div class="form-group">
					<form:input class="form-control" type="hidden" path="date" />
				</div>
				<div class="form-group">
					<form:textarea id="summernote" path="content"/>
				</div>
				<form:button type="submit" class="btn btn-default">확인</form:button>
			</form:form>
		</div>
		<!-- End newText -->
		
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
</body>
</html>