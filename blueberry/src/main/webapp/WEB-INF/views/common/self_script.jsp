<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
	<script>		
		WebFont.load({
		    google: {
		      families: ['Nanum Gothic', 'Jeju Hallasan', 'Jeju Myeongjo']			    }
		});
		$(function() {
			getCategoryList();
			
			//카테고리 추가 폼 보여주기
			document.getElementById('toggle-add-category-form').addEventListener('click', function(e) {
				e.preventDefault();
				$('#add-category-form').toggleClass('active');
			});
		});
		//사용자 인증시
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
		//카테고리 리스트
		function getCategoryList() {
			$.get("${pageContext.servletContext.contextPath}/main/categoryList", function(data){
				$(data.categories).each(function(index, list){
					$('.side-category').append('<li><a href="${pageContext.servletContext.contextPath}/main/category/'+list.name+'/1">'+list.name+'</a></li>');
					//console.log(list.categoryName+", "+list.numCategoryList);
				});
			});
		}
	</script>