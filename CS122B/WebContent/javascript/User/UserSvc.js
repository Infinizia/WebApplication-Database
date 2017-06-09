(function() {
	'use strict';
	angular.module('fabflixApp')
		.factory('userSvc', userSvc);
	userSvc.$inject = ['$http', '$q', 'userResc', 'Notification'];
	
	function userSvc($http, $q, userResc, Notification) {
		var user = null;
		return {
			getUser: getUser,
			currentUser : currentUser
		}
		function getUser(info) {
			var deferred = $q.defer();
			userResc.login(info).$promise.then(function(data) {
				Notification.success("Login Successful");
				deferred.resolve(data);
			}, function(error) {
				if(error.status == 520) {
					Notification.error("reCaptcha Failed");
				}
				else {
					Notification.error("Login Failed");
				}
				deferred.reject(error);
			});
			return deferred.promise;	
		}
		function currentUser() {
			var deferred = $q.defer();
			userResc.getUser().$promise.then(function(data) {
				if(data)
					deferred.resolve(data);
				else {
					deferred.reject(data);
				}
			}, function(error) {
				deferred.reject(error);
			});
			return deferred.promise;
		}
		
	}
})();