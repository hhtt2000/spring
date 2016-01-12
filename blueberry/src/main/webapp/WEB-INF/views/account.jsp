<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/common/config.jsp" %>
<title>계정 생성</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<c:if test="${not empty error}">
		<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><strong> ${error}</strong></div>	
	</c:if>
	
	<div class="container">
		<div class="blog-header">
			<h1>계정 생성</h1>
		</div>
		<div class="row">
			<div class="col-sm-8 blog-main">
				<form:form id="account-form" class="form-horizontal" action="accounts" commandName="accountDto" method="post">
				  <div class="form-group">
				    <label for="userid" class="col-sm-3 control-label">아이디</label>
				    <div class="col-sm-9">
				      <input type="text" id="userid" class="form-control" name="userid" value="${account.userid}" placeholder="아이디" /><br />
				      <form:errors path="userid" cssClass="error-handle" />
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="name" class="col-sm-3 control-label">이름</label>
				    <div class="col-sm-9">
				      <input type="text" id="name" class="form-control" name="name" value="${account.name}" placeholder="이름" /><br />
				      <form:errors path="name" cssClass="error-handle" />
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="password" class="col-sm-3 control-label">비밀번호</label>
				    <div class="col-sm-9">
				      <input type="password" id="password" class="form-control" name="password" placeholder="비밀번호" /><br />
				      <form:errors path="password" cssClass="error-handle" />
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="check-password" class="col-sm-3 control-label">비밀번호 확인</label>
				    <div class="col-sm-9">
				      <input type="password" id="check-password" class="form-control" id="check-password" placeholder="비밀번호 확인" />
				    </div>
				  </div>
				  <div class="form-group"></div>
				  <div class="form-group">
				    <div class="col-sm-offset-9 col-sm-3">
				      <button type="submit" id="account-form-btn"class="btn btn-default">회원가입</button>
				      <a class="btn btn-default" href="#" role="button">취소</a>
				    </div>
				  </div>
				</form:form>
			</div>
			
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		<%@ include file="/WEB-INF/views/common/bottom_script.jsp" %>
		<%@ include file="/WEB-INF/views/common/self_script.jsp" %>
		<script>
			$(function(){
				$('#account-form-btn').on('click', function(e) {
					var passwd = $('#account-form #password').val();
					var chkPasswd = $('#account-form #check-password').val();
					//패스워드가 일치하지 않거나 패스워드 체크를 하지 않은 경우
					if(passwd !== chkPasswd || chkPasswd === null || chkPasswd === '') {
						e.preventDefault();
						alert("패스워드를 다시 확인해 주세요.");
						$('#account-form #check-password').focus();
					}				
				});
				
			});
		</script>
</body>
</html>