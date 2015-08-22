<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
	<script>		
		WebFont.load({
		    google: {
		      families: ['Nanum Gothic', 'Jeju Hallasan', 'Jeju Myeongjo']			    }
		});
		$(function() {
			//검색 관련
			$('#search-form button').on('click', function(e){
				var searchTextLen = $('#search-form input[name=searchText]').val().length;
				if(searchTextLen === null || searchTextLen === 0){
					e.preventDefault();
					alert('검색어를 입력해주세요.');
					$('#search-form input[name=searchText]').focus();
				}
				
			});
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
		//검색 관련
		function setUrl(form) {
			form.action="${pageContext.servletContext.contextPath}/main/search/"+form.searchText.value+"/1";
			console.log(form.action);
		}
		
		//카테고리 리스트
		function getCategoryList() {
			$.get("${pageContext.servletContext.contextPath}/main/categoryList", function(data){
				$(data.categories).each(function(index, list){
					$('.side-category').append('<li><a href="${pageContext.servletContext.contextPath}/main/category/'+list.name+'/1">'+list.name+'</a></li>');
				});
			});
		}
	</script>