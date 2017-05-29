(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('fullTextCtrl', fullTextCtrl);
		
	fullTextCtrl.$inject = ['$scope', '$http', 'shoppingCartSvc', '$routeParams'];
	
	function fullTextCtrl($scope, $http, shoppingCartSvc, $routeParams) {
		$scope.searchText = "";
		$scope.freeTextResult = [];
		$scope.GetText = function(){
			if($scope.searchText != undefined && $scope.searchText != "" && $scope.searchText.length > 1 && $scope.searchText.trim() != ""){
				$http({
					 method: 'GET',
					 url: 'FullTextSearch',
					 headers: {'Content-Type': 'application/json'},
					 params:{
						 'searchText':$scope.searchText
					 }
			    }).then(function(data){
			    	$scope.freeTextResult = data.data;
				});
			}
			else{
				$scope.freeTextResult = [];
			}
		};
		$scope.reset = function(val){
			$scope.freeTextResult = [];
			if(val)
				$scope.searchText = val;
		}
	}
})();