<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
	<script>
		$(function() {
			//네비게이션바 표시 따라가도록 설정
			var curPath = window.location.pathname;
			var splitCurPathName = curPath.split("/");
			var curPathName = splitCurPathName[splitCurPathName.length - 1];
			var splitNavPathName = $('.blog-nav-item').attr('href').split("/");
			var curNavPathName = splitNavPathName[splitNavPathName.length - 1];
			var navLength = $('.blog-nav').children().length;
			if(curNavPathName !== curPathName){
				$('.blog-nav-item.active').removeClass('active');
				$('.blog-nav-item[href="'+curPath+'"]').addClass('active');
			}
		});
	</script>