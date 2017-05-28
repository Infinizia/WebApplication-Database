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
<style>
	.fab-search-dropdown{
	    position: absolute;
	    /* top: 100%; */
	    left: 23px;
	    z-index: 1000;
	    float: left;
	    min-width: 160px;
	    padding: 5px 0;
	    margin: 2px 0 0;
	    font-size: 14px;
	    text-align: left;
	    list-style: none;
	    background-color: #fff;
	    -webkit-background-clip: padding-box;
	    background-clip: padding-box;
	    border: 1px solid #ccc;
	    border: 1px solid rgba(0,0,0,.15);
	    border-radius: 4px;
	    width:90%;
	    -webkit-box-shadow: ;
	}
	.fab-search-dropdown>li>a {
	    display: block;
	    padding: 3px 20px;
	    clear: both;
	    font-weight: 400;
	    line-height: 1.42857143;
	    color: #333;
	    white-space: nowrap;
	}
	.fab-search-dropdown>li:hover{
		background-color:rgba(244, 66, 89,.5);
	}
	.navbar{
		margin-bottom:2px;
	}
</style>
<body>
	<div ng-app="fabflixApp">
		<div class="container-fluid" ng-controller="fullTextCtrl">
			<div class="row">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
					<div class="col-md-2">
						<div class="navbar-header">
					      <a class="navbar-brand" ng-href="#DisplayResults">Fablix</a>
					    </div>
					    <ul class="nav navbar-nav">
					      <li class="active"><a ng-href="#DisplayResults">Home</a></li>
						</ul>
					</div>	
					<div class="col-md-2" style="margin-top:.5%">
			      		<input type="text" required class="form-control" ng-model="searchText" ng-change="GetText();" placeholder="Search">
			      	</div>
					<div class="col-md-1"  style="margin-top:.5%">
						<button type="button" class="btn btn-success">Search</span></button>
					</div>
				    <ul class="nav navbar-nav navbar-right">
				    	<li><a href="#ShoppingCart">My Cart</a>
				    	<li><a href="#/Logout">Logout</a></li>
				   	</ul>
				  </div>
				</nav>
			</div>
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-2">
					<ul class="fab-search-dropdown" style="display:block">
				      <li><a href="#">HTML</a></li>
				      <li><a href="#">CSS</a></li>
				      <li><a href="#">JavaScript</a></li>
				    </ul>
				</div>
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
		<script src="${pageContext.request.contextPath}/javascript/FullTextCtrl/fullTextCtrl.js" type="text/javascript"></script>
</body>
</html>
