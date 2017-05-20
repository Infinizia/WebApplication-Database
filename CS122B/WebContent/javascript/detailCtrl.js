(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('displayCtrl', displayCtrl);
		
	displayCtrl.$inject = ['$scope', '$http', 'shoppingCartSvc', '$routeParams'];
	
	function displayCtrl($scope, $http, shoppingCartSvc, $routeParams) {
		$scope.init = function(){
				$scope.vm = {};
				$scope.UpdateDetail( $routeParams.id,  $routeParams.type);
			};
		
		$scope.UpdateDetail = function(id, type){
			if(id && type){
				$scope.vm.temp_id = id;
				$scope.vm.type = type;
			}
			$http({
				 method: 'GET',
				 url: 'GetDetail',
				 headers: {'Content-Type': 'application/json'},
				 params:{
					 'vm':$scope.vm
				 }
		   }).then(function(data){
			   	if(data){
			   		$scope.vm = data.data;
			   	}
			});
		}
		$scope.init();
		$scope.addToCart = function (movie) {
				shoppingCartSvc.addToCart(movie).then(function(){
			});
		}
	}
})();