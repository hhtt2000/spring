<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- include libraries(jQuery, bootstrap, fontawesome) -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<!-- include summernote css/js-->
<link href="${pageContext.servletContext.contextPath}/resources/summernote/summernote.css" rel="stylesheet">
<script src="${pageContext.servletContext.contextPath}/resources/summernote/summernote.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/summernote/lang/summernote-ko-KR.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/summernote/plugin/summernote-ext-video.js"></script>
<script>
		$(function() {
			$('#summernote').summernote(
					{
						lang : ['ko-KR'],
						fontNames: [
						    'Nanum Gothic', 'Jeju Hallasan', 'Jeju Myeongjo', 
						    'Serif', 'Sans', 'Courier', 'Courier New', 'Comic Sans MS'
						],
						toolbar : [
								['small', [ 'style', 'bold', 'italic', 'underline',
												'clear', 'color',
												'strikethrough', 'superscript',
												'subscript', 'ul', 'ol',
												'picture', 'link', 'video'
										  ]
								],
								['big', [ 'fontsize', 'fontname', 'paragraph', 'height',
												'table'
										]
								]
						],
						height : 250
					});
		});
	</script>