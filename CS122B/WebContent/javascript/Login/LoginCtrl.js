(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('loginCtrl', loginCtrl);
		
	loginCtrl.$inject = ['userSvc', '$location', '$q'];
	
	function loginCtrl(userSvc, $location) {
		var vm = this;
		
		vm.form_info = {
			email : "",
			password:"",
			recaptcha:""
		}
		
		vm.login = function(){
			vm.form_info.recaptcha = angular.element(document.getElementById('g-recaptcha-response'))[0].value;
			userSvc.getUser(vm.form_info)
				.then(function(user) {
					console.log('test user', user);
					if(user.id == -1){
						$location.path('/EmployeeService').replace();
					}
					else{
						$location.path('/DisplayResults').replace();
					}
				});
		}
	}
})();