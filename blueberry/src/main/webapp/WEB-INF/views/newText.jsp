<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- include libraries(jQuery, bootstrap, fontawesome) -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.9.1.min.js"></script> 
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
<script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<!-- include summernote css/js-->
<link href="summernote/summernote.css" rel="stylesheet">
<script src="summernote/summernote.min.js"></script>
<script type="text/javascript" src="summernote/lang/summernote-ko-KR.js"></script>
<script src="summernote/plugin/summernote-ext-video.js"></script >
<title>글쓰기</title>
</head>
<body>
	<form action="newTextProcessing.jh" method="post">
		<div class="form-group">
			<label for="exampleInputTitle1">제목</label> <input
				type="text" class="form-control" name="title" id="exampleInputTitle1">
		</div>
		<div class="form-group">
			<label for="exampleInputName1">이름</label> <input
				type="text" class="form-control" name="name" id="exampleInputName1">
		</div>
		<div class="form-group">
			<textarea id="summernote" name="content"></textarea>
		</div>
		<button type="submit" class="btn btn-default">확인</button>
	</form>
	<script>
		$(function(){
			$('#summernote').summernote({
				toolbar: [
				          ['small', ['bold', 'italic', 'underline', 'clear', 'color',
				                     'strikethrough', 'superscript', 'subscript',
				                     'ul', 'ol', 'picture', 'link', 'video']],
				          ['big', ['fontsize', 'paragraph', 'height', 'table']],
				          ],
				focus: true,
				height: 250
			});
		});
	</script>
</body>
</html>