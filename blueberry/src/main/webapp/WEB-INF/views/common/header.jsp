<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<style>
/* For category toggle */
#add-category-form {
	display: none;
}
#add-category-form.active{
	display: block;
}

/* footer icons */
i.tiny {font-size: 1em;}
i.small {font-size: 2em;}
i.medium {font-size: 4em;}
i.large {font-size: 6em;}
.fa-github-square {
	color: #191919;
}
.fa-github-square:hover {
	color: #595959;
}
.fa-twitter-square {
	color: DogerBlue;
}
.fa-twitter-square:hover {
	color: #78BCFF;
}
.fa-facebook-official {
	color: MidnightBlue;
}
.fa-facebook-official:hover {
	color: #47478D;
}
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
				<a class="blog-nav-item" href="<c:url value="/board/newText" />">글쓰기</a> 
				<a class="blog-nav-item" href="<c:url value="/main/testsql" />">Test SQL</a> 
				<a class="blog-nav-item" href="<c:url value="/main/todo" />">할일</a>
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