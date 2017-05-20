(function() {
	'use strict';
	angular.module('fabflixApp')
		.factory('shoppingCartResrc', shoppingCartResrc);
	shoppingCartResrc.$inject = ['$resource'];
	
	function shoppingCartResrc($resource) {
		return $resource(null,null, {
			getCart: {
				method: 'Get',
				 url: 'UpdateCart',
				 headers: {'Content-Type': 'application/json'},
				 params:{ }
			},
			addToCart: {
				 method: 'POST',
				 url: 'UpdateCart',
				 headers: {'Content-Type': 'application/json'},
				 params: {
					movies: '@movies'
				 }
			},
			removeFromCart: {
				 method: 'POST',
				 url: 'UpdateCart',
				 headers: {'Content-Type': 'application/json'},
				 params: {
					movies: '@movies'
				 }
			},
			checkout: {
				method: 'POST',
				url: 'Checkout',
				headers: {'Content-Type': 'application/json'},
				params: {
					card: '@card'
				}
			}
		});
	}
})();