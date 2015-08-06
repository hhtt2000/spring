<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
	<script>		
		WebFont.load({
		    google: {
		      families: ['Nanum Gothic', 'Jeju Hallasan', 'Jeju Myeongjo']			    }
		});
		$(function() {
			getCategoryList();
		});
		//사용자 인증시
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
		//카테고리 리스트
		function getCategoryList() {
			$.get("${pageContext.servletContext.contextPath}/categoryList", function(data){
				$(data.categories).each(function(index, list){
					$('.side-category').append('<li><a href="${pageContext.servletContext.contextPath}/main/'+list.categoryName+'">'+list.categoryName+'</a>('+list.numCategoryList+')</li>');
					//console.log(list.categoryName+", "+list.numCategoryList);
				});
			});
		}
	</script>