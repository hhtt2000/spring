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
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			var urlRegEx = new RegExp(
					    // protocol identifier
					    "(?:(?:https?|ftp)://)" +
					    // user:pass authentication
					    "(?:\\S+(?::\\S*)?@)?" +
					    "(?:" +
					      // IP address exclusion
					      // private & local networks
					      "(?!(?:10|127)(?:\\.\\d{1,3}){3})" +
					      "(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})" +
					      "(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})" +
					      // IP address dotted notation octets
					      // excludes loopback network 0.0.0.0
					      // excludes reserved space >= 224.0.0.0
					      // excludes network & broacast addresses
					      // (first & last IP address of each class)
					      "(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])" +
					      "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}" +
					      "(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))" +
					    "|" +
					      // host name
					      "(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)" +
					      // domain name
					      "(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*" +
					      // TLD identifier
					      "(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))" +
					      // TLD may end with dot
					      "\\.?" +
					    ")" +
					    // port number
					    "(?::\\d{2,5})?" +
					    // resource path
					    "(?:[/?#]\\S*)?"
					  , "i"
					);
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
						height : 250,
						onKeyup : function(e) {
							var textValue = $('#summernote').code();
							var keyCode = e.which || keyCode;
							if(urlRegEx.test(textValue) && keyCode == 32){
								var urlArr = urlRegEx.exec(textValue);
								var url = {"url" : urlArr[0]};
								
								//ajax
								$.ajax({
									//1. bbsController @requestParam 사용할 경우
									url : "${pageContext.servletContext.contextPath}/board/getUrlInfo",
									method : "get",
									data : url,
									//2. bbsController @requestBody 사용할 경우
									//url : "${pageContext.servletContext.contextPath}/board/getUrlInfo",
									//method : "post",
									//type : "json",
									//contentType : "application/json",
									//data : JSON.stringify(url),
									beforeSend : function(xhr){
										xhr.setRequestHeader(header, token);
									},
									success : function(data){
										//관련 div가 active하면 아래의 내용을 수행하지 않는다.
										var editedBox = "<br/>"
														+ "<a href='"+data.info.url+"'>"
														+ "<div class='url-info-box active' style='height: 110px;padding: 5px;border:1px solid #999966;'>"
														+ "<div>"+data.info.image+"</div>"
														+ "<div style='font-size:13px;color:#999966'><b>"+data.info.title+"</b></div>"
														+ "<div style='font-size:13px;color:#999966'>"+data.info.description+"</div>"
														+"</div>"
														+"</a>";
										$('#summernote').code(textValue + editedBox);
									},
									error : function(exception){
										console.log(exception);
									}
								});	
								
							}
						}
					});
		});
	</script>