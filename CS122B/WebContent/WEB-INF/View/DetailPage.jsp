<div class="container">
	<div class="row">
		<div class="col-md-12">
		</div>
	</div>
	<div class="row" ng-if="vm.type === 'movie'">
		<h2>Movie information</h2>
		<table id ="mytable" class="table table-hover table-bordered">
	      	<tr>
	          	<th>ID</th>
	          	<td>{{vm.movieDetail.id}}</td>
	        </tr>
	        <tr>
	          	<th>hello i changes this file.</th>
	          	<td>{{vm.movieDetail.title}}</td>
	        </tr>
	        <tr>
	          	<th>Year</th>
	          	<td>{{vm.movieDetail.year}}</td>
	        </tr>
	        <tr>
	          	<th>Director</th>
	          	<td>{{vm.movieDetail.director}}</td>
	        </tr>
	        <tr>
	          	<th>Genres</th>
	          	<td>
	         		<a ng-repeat="g in vm.movieDetail.genres" href="Search?genre=g.name">{{g.name}}<span ng-if="!$last">, </span></a>
	          	</td>
	      	</tr>
	      	<tr>
	          	<th>Featured star</th>
	          	<td>
	          		<a ng-repeat="s in vm.movieDetail.stars" href="#" ng-click="UpdateDetail(s.id, 'star')">{{s.first_name + ' ' + s.last_name}}<span ng-if="!$last">, </span></a>
	          	</td>
	      	</tr>
	      	<tr>
	          	<th>Poster</th>
	          	<td>{{vm.movieDetail.banner_url}}</td>
	      	</tr>
	      	<tr>
	          	<th>Trailer</th>
	          	<td>{{vm.movieDetail.trailer_url}}</td>
	      	</tr>
		</table>
	</div>
	<div class="row" ng-if="vm.type === 'star'">
		
		<h2>Star information</h2>
		<img ng-src="{{vm.starDetail.photo_url}}" width="auto" height="auto"/>
		<table id ="mytable" class="table table-hover table-bordered" >
	      	<tr>
	          	<th>ID</th>
	          	<td>{{vm.starDetail.id}}</td>
	        </tr>
	        <tr>
	          	<th>First Name</th>
	          	<td>{{vm.starDetail.first_name}}</td>
	        </tr>
	        <tr>
	          	<th>Last Name</th>
	          	<td>{{vm.starDetail.last_name}}</td>
	        </tr>
	        <tr>
	          	<th>Date of Birth</th>
	          	<td>{{vm.starDetail.dob}}</td>
	        </tr>
	      	<tr>
	          	<th>Movies</th>
	          	<td>
	          		<a ng-repeat="m in vm.starDetail.movieList" href="#" ng-click="UpdateDetail(m.id, 'movie')">{{m.title}}<span ng-if="!$last">, </span></a>
	          	</td>
	      	</tr>
		</table>
	</div>
</div>