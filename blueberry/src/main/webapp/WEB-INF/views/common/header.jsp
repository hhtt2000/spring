<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:csrfMetaTags/>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#blog-navbar-collapse" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand nav-font" href="<c:url value="/main" />"><strong>gra.pe</strong></a>
		    </div>
		    <div class="collapse navbar-collapse" id="blog-navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a class="nav-font" href="<c:url value="/board/newText" />">글쓰기</a></li>
				<li><a class="nav-font" href="<c:url value="/main/epl" />">EPL순위</a></li>
				<li><a class="nav-font" href="<c:url value="/main/randomBoard" />">무작위 이전글</a></li> 
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal.name == null}">
						<li><a class="nav-font" href="<c:url value="/login" />"><span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> 로그인</a></li>
					</c:when>
					<c:otherwise>
						<security:authorize access="hasRole('ROLE_USER')">
						<!-- For logged-in user -->
							<c:url value="/j_spring_security_logout" var="logoutUrl" />
							<form action="${logoutUrl}" method="post" id="logoutForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form>
							<c:if test="${pageContext.request.userPrincipal.name != null}">
							   <li class="dropdown">
							     <a href="#" class="dropdown-toggle nav-font" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i> ${pageContext.request.userPrincipal.name} <span class="caret"></span></a>
							     <ul class="dropdown-menu">
							       <li><a class="nav-font" href="#">Action</a></li>
							       <li role="separator" class="divider"></li>
							       <li><a class="nav-font" href="javascript:formSubmit()"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> 로그아웃</a></li>
							     </ul>
							   </li>
							</c:if>
						</security:authorize>
					</c:otherwise>
				</c:choose>
			</ul>
			</div>
		</div>
	</nav>
