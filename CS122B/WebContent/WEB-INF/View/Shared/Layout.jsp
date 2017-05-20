<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
	<script
		  src="https://code.jquery.com/jquery-3.2.1.min.js"
		  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
		  crossorigin="anonymous">
	</script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/angular-ui-notification.min.css">
</head>
<body>
<div ng-app="fabflixApp">
	<div class="container-fluid">
		<div class="row">
			<nav class="navbar navbar-default">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <a class="navbar-brand" ng-href="#DisplayResults">Fablix</a>
			    </div>
			    <ul class="nav navbar-nav">
			      <li class="active"><a ng-href="#DisplayResults">Home</a></li>
				</ul>
			    <ul class="nav navbar-nav navbar-right">
			    	<li><a href="#ShoppingCart">My Cart</a>
			    	<li><a href="#/Logout">Logout</a></li>
			   	</ul>
				  </div>
				</nav>
			</div>
		</div>
		<div ng-view></div>
		</div>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
   		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-route.min.js"></script>
   		<script src="${pageContext.request.contextPath}/javascript/angular/angular-resource.min.js"></script>
   		<script src="${pageContext.request.contextPath}/javascript/angular/angular-cookies.min.js"></script>
   		<script src="${pageContext.request.contextPath}/javascript/angular/angular-ui-notification.min.js"></script>
   		<script src="${pageContext.request.contextPath}/javascript/app.mod.js" type="text/javascript"></script>
   		<script src="${pageContext.request.contextPath}/javascript/shopping-cart.resrc.js" type="text/javascript"></script>
   		<script src="${pageContext.request.contextPath}/javascript/app.router.js" type="text/javascript"></script>
   		<script src="${pageContext.request.contextPath}/javascript/shopping-cart.svc.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/browse.ctrl.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/detailCtrl.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/shopping-cart.ctrl.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/Login/LoginCtrl.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/User/UserSvc.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/User/UserResrc.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/javascript/Admin/adminCtrl.js" type="text/javascript"></script>
	</div>
</body>
</html>
