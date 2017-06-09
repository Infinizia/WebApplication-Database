(function() {
	'use strict';
	angular.module('fabflixApp')
		.factory('shoppingCartSvc', shoppingCartSvc);
	shoppingCartSvc.$inject = ['$q', 'shoppingCartResrc', 'Notification'];
	
	function shoppingCartSvc($q, shoppingCartResrc, Notification) {
		return {
			getCart: getCart,
			addToCart: addToCart,
			removeFromCart: removeFromCart,
			removeAllFromCart: removeAllFromCart,
			checkout: checkout
		}
		
		function getCart() {
			var deferred = $q.defer();
			
			shoppingCartResrc.getCart()
				.$promise.then(function (cart) {
					deferred.resolve(cart);
				}, function(error) {
					deferred.reject(error);
				});
			return deferred.promise;
		}
		
		function addToCart(movie) {
			var deferred = $q.defer();
			
			shoppingCartResrc.addToCart({ moviesToAdd: [movie] })
				.$promise.then(function (cart) {
					Notification.success('Movie added to cart successfully');
					deferred.resolve(cart);
				}, function(error) {
					Notification.error('Movie was not added to cart');
					deferred.reject(error);
				});
			return deferred.promise;
		}
		
		function removeFromCart(movie) {
			var deferred = $q.defer();
			
			shoppingCartResrc.removeFromCart({ moviesToRemove: [movie] })
				.$promise.then(function (cart) {
					Notification.success('Movie removed from cart successfully');
					deferred.resolve(cart);
				}, function(error) {
					Notification.error('Movie was not removed from cart');
					deferred.reject(error);
				});
			return deferred.promise;
		}
		
		function removeAllFromCart(moviesToRemove) {
			var deferred = $q.defer();
			
			shoppingCartResrc.removeFromCart({ moviesToRemove: moviesToRemove })
				.$promise.then(function (cart) {
					deferred.resolve(cart);
				}, function(error) {
					deferred.reject(error);
				});
			return deferred.promise;
		}
		
		function checkout (card) {
			var deferred = $q.defer();
			
			shoppingCartResrc.checkout(card)
				.$promise.then(function (cart) {
					Notification.success('Movies purchased successfully');
					deferred.resolve(cart);
				}, function(error) {
					Notification.error('Invalid Card! Please check your information')
					deferred.reject(error);
				});
			return deferred.promise;
		}
	}
})();