<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- include libraries(jQuery, bootstrap, fontawesome) -->
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script> 
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<!-- include summernote css/js-->
<link href="${pageContext.servletContext.contextPath}/resources/summernote/summernote.css" rel="stylesheet">
<script src="${pageContext.servletContext.contextPath}/resources/summernote/summernote.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/summernote/lang/summernote-ko-KR.js"></script>
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
			
			//일정 시간마다(28분) session 유지 요청 보내기
			var keepSession = function() {
				$.ajax({
					type: "get",
					url: "${pageContext.servletContext.contextPath}/board/keepSession",
					success: function(result) {
						alert("글쓰기 시간이 연장되었습니다.");
					}
				});
			}
			
			setInterval(keepSession, 28 * 60 * 1000);
			
			var removeUrlBoxButton = function (context) {
				  var ui = $.summernote.ui;
				  // URL box 삭제 버튼
				  var button = ui.button({
				    contents: '<i class="glyphicon glyphicon-trash"/>',
				    tooltip: 'URL박스 삭제',
				    click: function () {
				    	var box = $('.note-editable').find('.url-info-box');
				    	if(box.length > 0) {
							box[0].remove();
				    	}
				    }
				 });
				 return button.render(); 
			}
			
			// 이모지 리스트 로드 & 에디터 생성
		    $.ajax({
		        url: 'https://api.github.com/emojis'
		    }).then(function (data) {
		        var emojis = Object.keys(data);
		        var emojiUrls = data;
		        
				$('#summernote').summernote({
							placeholder : "[팁] 1. ':'+알파벳=이모지, 2. URL 입력+스페이스바=URL 정보 박스",
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
									],
									['mybutton', ['removeUrlBox']]
							],
							buttons: {
								removeUrlBox: removeUrlBoxButton
							},
							height : 250,
							callbacks: {
								onKeyup : function(e) {
									var textValue = $('#summernote').summernote('code');
									var keyCode = e.which || keyCode;
									if(urlRegEx.test(textValue) && keyCode == 32){
										var urlArr = urlRegEx.exec(textValue);
										var url = {"url" : urlArr[0]};
										//url-info-box가 존재하면 박스를 추가하지 않는다.
										if($('.note-editable').find('.url-info-box').length > 0) {
											return;
										}
										
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
												var editedBox = "<div class='url-info-box container-fluid'>"
													 + "<div class='row' style='padding-top:5px;padding-bottom:5px;margin-left:5px;margin-right:10px;border:1px solid #999966;'>"
													   + "<a href='"+data.info.url+"'>"
													     + "<div class='col-sm-2' style='padding-left:5px;padding-right:5px;'>"+data.info.image+"</div>"
													     + "<div class='col-sm-10' style='padding-left:5px'>"
													       + "<div style='font-size:13px;color:#999966;padding-left:5px;padding-right:10px;padding-top:1px;padding-bottom:7px;'><b>"+data.info.title+"</b></div>"
													       + "<div style='font-size:13px;color:#999966;padding-left:5px;padding-right:10px;padding-bottom:5px;'>"+data.info.description+"</div>"
													     + "</div>"
													   +"</a>"
													 +"</div>"
												   +"</div>";
												$('#summernote').summernote('code', textValue + editedBox);
											},
											error : function(exception){
												if(exception.status === 500) {
													alert('해당 URL 정보를 불러올 수 없습니다.');
												}
											}
										});	
									}
								},
								onImageUpload : function(files) {
									for(var i = 0; i < files.length; i++) {
										sendFile(files[i], this);
									}
								}
							},
							
							hintDirection: 'bottom',
				            hint: [{
				              search: function (keyword, callback) {
				                callback($.grep(emojis, function (item) {
				                  return item.indexOf(keyword)  === 0;
				                }));
				              },
				              match: /\B:([\-+\w]+)$/,
				              template: function (item) {
				                var content = emojiUrls[item];
				                return '<img src="' + content + '" width="20" /> :' + item + ':';
				              },
				              content: function (item) {
				                var url = emojiUrls[item];
				                if (url) {
				                  return $('<img />').attr('src', url).css('width', 20)[0];
				                }
				                return '';
				              }
				            }]
					});
		    }); //summernote
			
			function sendFile(file, tagName) {
				var data = new FormData();
				data.append('file', file);
				$.ajax({
					data: data,
					type: "post",
					url: "${pageContext.servletContext.contextPath}/board/saveImage",
					cache: false,
					contentType: false,
					processData: false,
					beforeSend : function(xhr){
						xhr.setRequestHeader(header, token);
					},
					success: function(data) {
						$(tagName).summernote('editor.insertImage', data.path);
					}
				});
			}
	
			$('#text-frm-btn').on('click', function(e) {
				e.stopImmediatePropagation();
				var title = $('#text-frm input[name=title]').val().trim();
				if(title === null || title === '') {
					alert('제목을 입력해 주세요.');
					$('#text-frm input[name=title]').focus();
					return false;
				}
				if($('#summernote').summernote('isEmpty')) {
					alert('내용을 입력해 주세요.');
					$('#summernote').summernote('focus');
					return false;
				}
				return true;
			});
		}); //$(function())
		
		function doFiltering(form) {
			var whitelist = '//www.youtube.com/embed/[\\w\\d]+';
            // 정규표현식 객체 생성
            var regex = RegExp(whitelist);
            DOMPurify.addHook('afterSanitizeAttributes', function(node){
                var anchor = document.createElement('iframe');
                //iframe 태크는 유투브만 허용
                // iframe 태그의 src가 화이트리스트 조건을 만족하는지 검사
                if (node.tagName === 'IFRAME' && node.hasAttribute('src')) {
                    anchor.src  = node.getAttribute('src');
                    if (anchor.src && !anchor.src.match(regex)) {
                        node.parentNode.removeChild(node);
                    }
                }
            });
			form.title.value = DOMPurify.sanitize(form.title.value);
			form.content.value = DOMPurify.sanitize(form.content.value);
		}
	</script>