(function() {
	'use strict';
	angular.module('fabflixApp')
		.config(['$routeProvider', '$locationProvider', '$httpProvider', 
			function($routeProvider, $locationProvider, $httpProvider) {
			$locationProvider.hashPrefix('');
			$httpProvider.defaults.withCredentials = true;
		    $routeProvider
		    .when('/', {
		    	templateUrl: "html/Login.html",
		    	controller: "loginCtrl",
		    	controllerAs: 'vm'
		    })
		    .when('/EmployeeService', {
		    	templateUrl: "html/EmployeeService.html",
		    	controller: "adminCtrl",
		    	controllerAs: 'vm',
		    	resolve:{
		    		"check": function($rootScope, $http, $location, userSvc){
		    			$http({
		    				method:"GET",
		    				url: 'GetAdmin'
		    			}).then(function(data){
		    				if(!data.data)
		    				{
		    					$location.url('/DisplayResults').replace();
		    				}
		    			});
		    		}
		    	}
		    })
		    .when('/DisplayResults/:genre?', {
		    	templateUrl: "html/DisplayResults.html",
		    	controller: "browseCtrl",
		    	controllerAs: 'vm',
		    })
		    .when('/ShoppingCart', {
		    	templateUrl: "html/ShoppingCart.html",
		    	controller: "shoppingCartCtrl",
		    	controllerAs: 'vm',
		    })
		    .when('/Checkout', {
		    	templateUrl: "html/Checkout.html",
		    	controller: "shoppingCartCtrl",
		    	controllerAs: 'vm',
		    })
		    .when('/Confirmation', {
		    	templateUrl: "html/Confirmation.html",
		    	controller: "shoppingCartCtrl",
		    	controllerAs: 'vm',
		    })
		    .when('/Logout', {
		    	controller: "shoppingCartCtrl",
		    	controllerAs: 'vm',
		    	resolve:{
		    		"check": function($rootScope, $http, $location, userSvc){
		    			$http({
		    				method:"GET",
		    				url: 'Logout'
		    			});
		    			$location.url('/').replace();
		    		}
		    	}
		    })
		    .when('/DetailPage/:id/:type', {
		    	templateUrl: "html/DetailPage.html",
		    	controller: "displayCtrl",
		    	controllerAs: 'vm'
		    })	    
	}])
	.run(['$rootScope','$location', '$routeParams', 'userSvc', '$q', function($rootScope, $location, $routeParams, userSvc, $q) {
	    $rootScope.$on('$routeChangeSuccess', function(e, current, pre) {
	    	userSvc.currentUser().then(function(data){
	    		if(data.length === 0){
	    			$location.path('/').replace();
	    		}
	    		else{
	    			$location.path()
	    		}
	    	}).catch(function(err){
	    		$location.path('/').replace();
	    	})
	    });
	}]);
})();
