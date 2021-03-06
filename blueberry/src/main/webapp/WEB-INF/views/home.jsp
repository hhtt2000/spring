<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" pageEncoding="utf-8"%>
<html lang="en" data-framework="angularjs">
<head>
<security:csrfMetaTags/>
<title>AngularJS • TodoMVC</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/angularjs/node_modules/todomvc-common/base.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/angularjs/node_modules/todomvc-app-css/index.css">
<style>
[ng-cloak] {
	display: none;
}
</style>
</head>
<body ng-app="todomvc">
	<a href="${pageContext.servletContext.contextPath}/main">HOME</a><span>현재 시간은 &nbsp;<strong id="time"></strong></span>
	<ng-view />
	<script>
		/* window.onload = function(){
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			function repeatLoop(){
				$.ajax({
					url: "${pageContext.servletContext.contextPath}/main/getTime",
					type: "get",
					beforeSend : function(xhr){
						xhr.setRequestHeader(header, token);
					},
					success: function(e){
						var data = e.serverTime;
						var strLength = data.length - 3;
						var modifiedData = data.substring(0, strLength);
						$('strong#time').text(modifiedData);
					},
					error: function(){
						console.log('error');
					}
				});
				setTimeout("repeatLoop()", 1000 * 30);
			}
		} */
			
	</script>
	<script type="text/ng-template" id="todomvc-index.html">
			<section id="todoapp">
				<header id="header">
					<h1>todos</h1>
					<form id="todo-form" ng-submit="addTodo()">
						<input id="new-todo" placeholder="What needs to be done?" ng-model="newTodo" ng-disabled="saving" autofocus>
					</form>
				</header>
				<section id="main" ng-show="todos.length" ng-cloak>
					<input id="toggle-all" type="checkbox" ng-model="allChecked" ng-click="markAll(allChecked)">
					<label for="toggle-all">Mark all as complete</label>
					<ul id="todo-list">
						<li ng-repeat="todo in todos | filter:statusFilter track by $index" ng-class="{completed: todo.completed, editing: todo == editedTodo}">
							<div class="view">
								<input class="toggle" type="checkbox" ng-model="todo.completed" ng-change="toggleCompleted(todo)">
								<label ng-dblclick="editTodo(todo)">{{todo.title}}</label>
								<button class="destroy" ng-click="removeTodo(todo)"></button>
							</div>
							<form ng-submit="saveEdits(todo, 'submit')">
								<input class="edit" ng-trim="false" ng-model="todo.title" todo-escape="revertEdits(todo)" ng-blur="saveEdits(todo, 'blur')" todo-focus="todo == editedTodo">
							</form>
						</li>
					</ul>
				</section>
				<footer id="footer" ng-show="todos.length" ng-cloak>
					<span id="todo-count"><strong>{{remainingCount}}</strong>
						<ng-pluralize count="remainingCount" when="{ one: 'item left', other: 'items left' }"></ng-pluralize>
					</span>
					<ul id="filters">
						<li>
							<a ng-class="{selected: status == ''} " href="#/">All</a>
						</li>
						<li>
							<a ng-class="{selected: status == 'active'}" href="#/active">Active</a>
						</li>
						<li>
							<a ng-class="{selected: status == 'completed'}" href="#/completed">Completed</a>
						</li>
					</ul>
					<button id="clear-completed" ng-click="clearCompletedTodos()" ng-show="completedCount">Clear completed</button>
				</footer>
			</section>
			<footer id="info">
				<p>Double-click to edit a todo</p>
				<p>Credits:
					<a href="http://twitter.com/cburgdorf">Christoph Burgdorf</a>,
					<a href="http://ericbidelman.com">Eric Bidelman</a>,
					<a href="http://jacobmumm.com">Jacob Mumm</a> and
					<a href="http://igorminar.com">Igor Minar</a>
				</p>
				<p>Part of <a href="http://todomvc.com">TodoMVC</a></p>
			</footer>
		</script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/node_modules/todomvc-common/base.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/node_modules/angular/angular.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/node_modules/angular-route/angular-route.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/js/app.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/js/controllers/todoCtrl.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/js/services/todoStorage.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/js/directives/todoFocus.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/angularjs/js/directives/todoEscape.js"></script>
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
</body>
</html>
