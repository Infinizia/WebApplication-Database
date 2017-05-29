(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('browseCtrl', browseCtrl);
	browseCtrl.$inject = ['$scope', '$http', 'shoppingCartSvc', '$window', '$routeParams'];
	
	function browseCtrl($scope, $http, shoppingCartSvc, $window, $routeParams){
		var vm = this;
		
		$scope.init = function(){
			if($routeParams.genre){
				$scope.filters = {};
				$scope.filters.genre = $routeParams.genre;
			}
			if($routeParams.title){
				if(!$scope.filters){
					$scope.filters = {};
				}
				$scope.filters.searchWord = $routeParams.title;
				$scope.filters.type = 'title';

			}
			
			$scope.page=['Show All',10,20,50];
			$scope.page = [
			    { name: 'Show All', value: 1000 },
			    { name: '10', value: 10 },
			    { name: '20', value: 20 },
			    { name: '50', value: 50 },
			];
			$scope.searchTypes = 
					[{value: "title", text: "Title",},
					{value: "year", text: "Year"},
					{value: "director", text: "Director"},
					{value: "star", text: "Star's name"}];
			
			$scope.filterTitleList = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
									  'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
									  'W', 'X', 'Y', 'Z'];
		
			$scope.Search();
		};
		

		$scope.ResetPages = function(){
			$scope.pageVal = [];
	    	var maxPage = Math.ceil($scope.resultCount/$scope.filters.pageLength);
	    	for(var i=0; i<maxPage; i++){$scope.pageVal.push(i+1);}
		}
		$scope.GetNextPage = function (page){
			if(page >= 1 && page <= $scope.pageVal.length){
				$window.scrollTo(0, 0);
				$scope.filters.currentPage = page;
				$http({
					 method: 'GET',
					 url: 'Page',
					 headers: {'Content-Type': 'application/json'},
					 params:{
						 'filter':$scope.filters
					 }
			    }).then(function(data){
			    	if(data){
			    		$scope.ResetPages();
				    	$scope.movieList = data.data;
			    	}
				});
			}
		}
		$scope.RefreshPage = function(){
			$scope.filters.currentPage = 1;
			$scope.GetNextPage($scope.filters.currentPage);
		}
		$scope.Search = function(){
			$http({
				 method: 'GET',
				 url: 'Search',
				 headers: {'Content-Type': 'application/json'},
				 params:{
					 'filter':$scope.filters
				 }
		    }).then(function(data){
		    	if(data){
		    		var selectVal = {'name':'All', 'id': -1};
		    		$scope.genreList = data.data.genreList;
		    		$scope.genreList.unshift(selectVal);
			    	$scope.movieList = data.data.movieList;
			    	$scope.filters = data.data.filters;
			    	$scope.resultCount = data.data.movieCount;
			    	$scope.ResetPages();
			    	$scope.currtype = $scope.filters.type;
		    	}
			});
		}
		$scope.Clear = function(){
			$scope.filters.genre = "All";
			$scope.filters.orderBy = "title";
			$scope.filters.isDesc = true;
			$scope.SearchTitle('','');
		
		}
		$scope.CheckImg = function(url){
			$http.get(ngSrc).success(function(){
                alert('image exist');
            }).error(function(){
                alert('image not exist');
                element.attr('src', '/images/default_user.jpg'); // set default image
            });
		}
		
		$scope.SearchTitle = function(word, type){
			$scope.filters.searchWord = word;
			$scope.filters.type = type;
			$scope.Search();
		}
		$scope.ReOrderMovie = function(type){
			if(type != $scope.filters.orderBy){
				$scope.filters.orderBy = type;
				$scope.filters.isDesc = true;
			}
			else{
				$scope.filters.isDesc = !$scope.filters.isDesc;
			}
			$scope.filters.currentPage = 1;
			$http({
				 method: 'GET',
				 url: 'Sort',
				 headers: {'Content-Type': 'application/json'},
				 params:{
					 'filter':$scope.filters
				 }
				
		    }).then(function(data){
		    	if(data){
		    		$scope.movieList = data.data;
		    	}
			});
		}
		$scope.init();
		vm.addToCart = function (movie) {
				shoppingCartSvc.addToCart(movie).then(function(){
			});
		}
	}
})();