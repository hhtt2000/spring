<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<link href="<c:url value="/resources/loginform/css/style.css" />" rel="stylesheet" type="text/css">
<title>로그인</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<c:if test="${not empty error}">
		<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><strong>${error}</strong></div>	
	</c:if>

	<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-7 col-md-offset-2">
            
            <div class="account-wall">
                <img class="profile-img img-rounded" src="https://image.freepik.com/free-photo/woman-working-at-her-office-desk_385-19323848.jpg"
                    alt="">
                <form name="form" class="form-signin" action="j_spring_security_check" method="post">
	                <input type="text" class="form-control" name="userId" placeholder="아이디" autofocus>
	                <input type="password" class="form-control" name="password" placeholder="비밀번호">
	                <button class="btn btn-lg btn-primary btn-block" type="button" onclick="checkLogin()">
	                    로그인</button>
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                <label class="checkbox pull-left">
	                    <input type="checkbox" name="remember-me">
	                    자동 로그인 사용하기
	                </label>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	function checkLogin() {
		if(form.userId.value == "") {
			alert("아이디를 입력해주세요.");
			return;
		}
		if(form.password.value == "") {
			alert("패스워드를 입력하세요.");
			return;
		}
		form.submit();
	}
</script>
</body>
</html>
	
