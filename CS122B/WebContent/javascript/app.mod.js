(function() {
	'use strict';
	angular.module('fabflixApp', ['ngRoute', 'ngResource', 'ui-notification'])
		.config(function(NotificationProvider) {
			NotificationProvider.setOptions({
				startTop: 80
			})
		});
})();