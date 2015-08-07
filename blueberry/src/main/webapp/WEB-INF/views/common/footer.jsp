<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<div class="col-sm-3 col-sm-offset-1 blog-sidebar">
				<!-- Blog Search Well -->
                <div class="well">
                    <h4>Blog Search</h4>
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                <span class="glyphicon glyphicon-search"></span>
                        </button>
                        </span>
                    </div>
                    <!-- /.input-group -->
                </div>
				<div class="sidebar-module">
					<h4>분류</h4>
					<div style="text-align:right;">
						<a href="#" id="toggle-add-category-form">추가<i class="fa fa-plus-square-o tiny"></i></a>							
						<c:url value="/main/addCategory" var="addCategoryUrl" />
						<form id="add-category-form" class="form-inline" action="${addCategoryUrl}" method="post">
								<div class="form-group">
									<input type="text" class="form-control input-sm" name="categoryName" autofocus />							
								</div>
								<input type="submit" class="btn btn-default btn-sm" value="확인" />
						</form>
					</div>
					<ul class="side-category" style="padding-left:20px">
					</ul>
				</div>
				<div class="sidebar-module">
					<h4>또다른 페이지</h4>				
						<a href="https://github.com/hhtt2000"><i class="fa fa-github-square small"></i></a>
						<a href="https://www.twitter.com/"><i class="fa fa-twitter-square small"></i></a>
						<a href="https://ko-kr.facebook.com/"><i class="fa fa-facebook-official small"></i></a>
				</div>
			</div>
			<!-- /.blog-sidebar -->

		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<footer class="blog-footer">
		<p>
			Blog template built for <a href="http://getbootstrap.com">Bootstrap</a>
			by <a href="https://twitter.com/mdo">@mdo</a>.
		</p>
		<p>
			<a href="#">Back to top</a>
		</p>
	</footer>
