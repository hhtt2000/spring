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
			<form action="newText" method="post">
				<input type="hidden" name="userid" value="${pageContext.request.userPrincipal.name}" />
				<div class="form-group row category-title-gap">
					<div class="category col-xs-4">
						<select class="form-control" name="category" autofocus>
							<c:forEach items="${categories}" var="category">
						  		<c:choose>
									<c:when test="${category.categoryName == boardDto.category}">
										<option value="${category.categoryName}" selected="selected">${category.categoryName}</option>						
									</c:when>
									<c:otherwise>
										<option value="${category.categoryName}">${category.categoryName}</option>
									</c:otherwise>
								</c:choose>
						  	</c:forEach>
						</select>
					</div>
					<div class="name col-xs-8">
						<c:choose>
							<c:when test="${not empty name}">
								<input type="text" class="form-control" name="name" value="${name}" placeholder="작성자" readonly />							
							</c:when>
							<c:otherwise>
								<input type="text" class="form-control" name="name" value="${boardDto.name}" placeholder="작성자" readonly />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="title" value="${boardDto.title}" placeholder="제목" />
				</div>
				<div class="form-group">
					<textarea id="summernote" name="content">${boardDto.content}</textarea>
				</div>
				<button type="submit" class="btn btn-default">확인</button>
			</form>
		</div>
		<!-- End newText -->
		
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
	
</body>
</html>