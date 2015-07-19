<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<title>글쓰기</title>
</head>
<body>
	<form action="newTextProcessing.jh" method="post">
		<div class="form-group">
			<label for="exampleInputTitle1">제목</label> <input
				type="text" class="form-control" name="title" id="exampleInputTitle1"
				placeholder="Title">
		</div>
		<div class="form-group">
			<label for="exampleInputName1">이름</label> <input
				type="text" class="form-control" name="name" id="exampleInputName1"
				placeholder="Name">
		</div>
		<div class="form-group">
			<label for="exampleInputContent1">내용</label>
			<textarea class="form-control" name="content" id="exampleInputContent1" rows="5" placeholder="Content"></textarea>
		</div>
		<button type="submit" class="btn btn-default">확인</button>
	</form>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>