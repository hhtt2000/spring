<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
				<a class="blog-nav-item active" href="<c:url value="/main" />">홈</a> 
				<a class="blog-nav-item" href="#">New features</a> 
				<a class="blog-nav-item" href="<c:url value="/newText" />">글쓰기</a> 
				<a class="blog-nav-item" href="<c:url value="/testsql" />">Test SQL</a> 
				<a class="blog-nav-item" href="<c:url value="/todo" />">할일</a>
			</nav>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			사용자: ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">로그아웃</a>
		</c:if>
	</security:authorize>