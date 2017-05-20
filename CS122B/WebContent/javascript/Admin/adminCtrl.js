(function() {
    'use strict';
    var myApp = angular.module('fabflixApp');
 
    /*
     A directive to enable two way binding of file field
     */
    myApp.directive('demoFileModel', function ($parse) {
        return {
            restrict: 'A', //the directive can be used as an attribute only
 
            /*
             link is a function that defines functionality of directive
             scope: scope associated with the element
             element: element on which this directive used
             attrs: key value pair of element attributes
             */
            scope:true,
            link: function (scope, element, attrs) {
                var model = $parse(attrs.demoFileModel),
                    modelSetter = model.assign; //define a setter for demoFileModel
             
                //Bind change event on the element
                element.bind('change', function () {
                    //Call apply on scope, it checks for value changes and reflect them on UI
                    scope.$apply(function () {             
                        //set the model value
                        modelSetter(scope, element[0].files[0]);
                   	   console.log('we here!!');
                	   console.log(scope);
                    });
                });
            }
        };
    });
})();
(function() {
	'use strict';
	angular.module('fabflixApp')
		.controller('adminCtrl', adminCtrl);
		
	adminCtrl.$inject = ['$http','Notification','$scope'];
	
	function adminCtrl($http,Notification,$scope) {
		var vm = this;

		vm.movie_info = {
			title:"",
			year:"",
			director:"",
			banner_url:"",
			trailer_url:""
		}
		
		vm.star_info ={
				first_name:"",
				last_name:""	
		}
		vm.star_fullname="";

		vm.genre_info ={
				name:""
		}
		vm.Clear = function(){
			vm.movie_info = {
					title:"",
					year:"",
					director:"",
					banner_url:"",
					trailer_url:""
				}
				
				vm.star_info ={
						first_name:"",
						last_name:""	
				}
				vm.star_fullname="";

				vm.genre_info ={
						name:""
				}
		}

		vm.AddMovie = function(AddMovieForm){
			if (AddMovieForm.$valid)
			{
				console.log("Add movie")
				$http({
					 method: 'GET',
					 url: 'AddMovie',
					 headers: {'Content-Type': 'application/json'},
					 params:{'movie':vm.movie_info, 'star':vm.star_info, 'genre':vm.genre_info }
			    }).then(function(data){
			    	if(data){
			    		console.log(data);
			    		if (data.data == "success")
			    			Notification.success('Movie added successfully!');
			    		else
			    			Notification.error('Error: This movie is already exist in the database');
			    	}	
				});
			}	
		}

		vm.InsertStar = function(){
			console.log("Insert function")
			var name = vm.star_fullname.split(" ");
			
			if (name.length > 1)
			{
				vm.star_info.first_name = name[0];
				vm.star_info.last_name = name[1];
			}
			else
				vm.star_info.last_name = name[0];
			
			$http({
				 method: 'GET',
				 url: 'InsertStar',
				 headers: {'Content-Type': 'application/json'},
				 params:{'star':vm.star_info}
				 
		    }).then(function(data){
		    	if(data){
		    		console.log(data)
		    		vm.statusInfo = data.data;
		    		if (vm.statusInfo == "success")
		    			Notification.success('Star insert successfully!');
		    		else
		    			Notification.error('Error: The star already exists!');
		    	}
			});
		}
		
		vm.PrintMetaData = function(){
			console.log("Display Metadata")
			$http({
				method: 'GET',
				url: 'DisplayMetaData',
				headers: {'Content-Type': 'application/json'},
				params:{ }
				
			}).then(function(data){
				if(data)
				{
					console.log(data);
					vm.dataList = data.data;
					console.log(vm.dataList);
				}
			})
		}
		vm.xmlUpload = {
			xmlFile:undefined,
			type:"actor",
		}
		vm.UploadFile = function(){
			jQuery("body").toggleClass("wait");
			var fd = new FormData();
			for(var key in vm.xmlUpload){
				fd.append(key, vm.xmlUpload[key]);
			}
			$http.post('XmlActor', fd, {
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined}
			}).then(function(data){
				Notification.success('XML Uploaded!');
				jQuery("body").toggleClass("wait");
		    	if(data.data && data.data.length > 0){
		    		if(vm.xmlUpload.type === 'main'){
		    			vm.movieErrorList = data.data;
		    		}
		    		else{
		    			vm.starErrorList = data.data;
		    		}
		    	}	
			});
		}
	
	}
})();
