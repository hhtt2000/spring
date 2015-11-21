<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<%@ include file="/WEB-INF/views/common/summernote.jsp" %>
<title>글수정</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="container">
	<div class="blog-header"></div>
	<!-- newText -->
	<div class="row">
		<div class="col-sm-8 blog-main">
			<form:form id="text-frm" action="${pageContext.servletContext.contextPath}/board/updateText" commandName="boardDto" method="post" onsubmit="doFiltering(this)">
				<div class="form-group">
					<form:input class="form-control" type="hidden" path="id"/>
				</div>
				<div class="form-group row category-title-gap">
					<div class="category col-xs-4">
						<form:select class="form-control" path="category">
							<c:forEach items="${categories}" var="category">
						  		<form:option value="${category.name}">${category.name}</form:option>
						  	</c:forEach>
						</form:select>
					</div>
					<div class="name col-xs-8">
						<form:input class="form-control" type="text" path="name" placeholder="작성자" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<form:input class="form-control" type="text" path="title" placeholder="제목" autofocus="autofocus" />
				</div>
				<div class="form-group">
					<form:input class="form-control" type="hidden" path="regdate" />
				</div>
				<div class="form-group">
					<form:textarea id="summernote" path="content"/>
				</div>
				<form:button type="submit" class="btn btn-default" id="text-frm-btn">확인</form:button>
			</form:form>
		</div>
		<!-- End newText -->
		
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
		<script>
			$(function() {
				//url-info-box가 있는 경우 'x'버튼 활성화
				if($('#url-info-box').length > 0) {
					$('#url-info-box-del').show();
				}
			});
		</script>
</body>
</html>