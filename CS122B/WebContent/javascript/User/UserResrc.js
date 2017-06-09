(function() {
	'use strict';
	angular.module('fabflixApp')
		.factory('userResc', userResc);
	userResc.$inject = ['$resource'];
	
	function userResc($resource) {
		return $resource(null,null, {
			login: {
				 method: 'POST',
				 url: 'Login',
				 headers: {'Content-Type': 'application/json'},
				 params:{ info: '@info'}
			},
			getUser:{
				method: 'GET',
				url:'GetUser',
				headers: {'Content-Type': 'application/json'},
			}
		})
	}
})();