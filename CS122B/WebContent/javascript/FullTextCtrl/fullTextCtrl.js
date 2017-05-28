(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('fullTextCtrl', fullTextCtrl);
		
	fullTextCtrl.$inject = ['$scope', '$http', 'shoppingCartSvc', '$routeParams'];
	
	function fullTextCtrl($scope, $http, shoppingCartSvc, $routeParams) {
		$scope.searchText = "";
		
		$scope.GetText = function(){
			if($scope.searchText != "" && $scope.searchText.length > 3 && $scope.searchText.trim() != ""){
				$http({
					 method: 'GET',
					 url: 'FullTextSearch',
					 headers: {'Content-Type': 'application/json'},
					 params:{
						 'searchText':$scope.searchText
					 }
			    }).then(function(data){
			    	console.log(data.data);
				});
			}
		};
	}
})();