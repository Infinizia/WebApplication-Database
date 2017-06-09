(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('shoppingCartCtrl', shoppingCartCtrl);
	shoppingCartCtrl.$inject = ['shoppingCartSvc', '$location'];
	
	function shoppingCartCtrl(shoppingCartSvc, $location) {
		var vm = this;
		vm.cart = {};
		vm.form = {};
		
		(function _init() {
			shoppingCartSvc.getCart()
				.then(function(cart) {
					vm.cart = cart;
					if($location.path().includes('/Confirmation')) {
						shoppingCartSvc.removeAllFromCart(vm.cart.moviesToAdd);
					}
				});
		})();
		
		vm.addToCart = function (movie) {
			shoppingCartSvc.addToCart(movie);
		}

		vm.updateCart = function (movie) {
			movie.isUpdate = true;
			if(movie.inCart > 0) {
				shoppingCartSvc.addToCart(movie);
			}
			else if(movie.inCart === "0") {
				shoppingCartSvc.removeFromCart(movie)
					.then(function(newCart){
						vm.cart = newCart;
				});
			}
		}
		
		vm.removeFromCart = function (movie) {
			shoppingCartSvc.removeFromCart(movie)
				.then(function(newCart){
					vm.cart = newCart;
			});
		};
		
		vm.checkout = function () {
			shoppingCartSvc.checkout(vm.form)
				.then(function(response) {
					$location.path("/Confirmation").replace();
				});
		}
		
		vm.redirectToHome = function () {
			$location.path("/DisplayResults").replace();
		}
	};
})();