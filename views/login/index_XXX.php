<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AngularJS Posting Login Form</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<style type="text/css">
		.login-form {
			max-width: 300px;
			margin: 0 auto;
		}
		#inputUsername {
		  margin-bottom: -1px;
		  border-bottom-right-radius: 0;
		  border-bottom-left-radius: 0;
		}
		#inputPassword {
			border-top-left-radius: 0;
  			border-top-right-radius: 0;
		}
	</style>
  </head>
  <body ng-app="postExample" ng-controller="PostController as postCtrl">
    <div class="container">
      <form class="login-form" ng-submit="postCtrl.postForm()">
        <h2>Please sign in</h2>
        <label for="inputUsername" class="sr-only">Username</label>
        <input type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus ng-model="postCtrl.inputData.username">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required ng-model="postCtrl.inputData.password">
		<br>
		<div class="alert alert-danger" role="alert" ng-show="errorMsg">{{errorMsg}}</div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		
      </form>
    </div> 
	<script>
	angular.module('postExample', [])
	.controller('PostController', ['$scope', '$http', function($scope, $http) {
		
		this.postForm = function() {
		
			var encodedString = 'username=' +
				encodeURIComponent(this.inputData.username) +
				'&password=' +
				encodeURIComponent(this.inputData.password);
				
			$http({
				method: 'POST',
				url: 'login/run',
				data: encodedString,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			})
			.success(function(data, status, headers, config) {
				console.log(data);
				if ( data.trim() === 'correct') {
					window.location.href = 'dashboard';
				} else {
					$scope.errorMsg = "Login not correct";
				}
			})
			.error(function(data, status, headers, config) {
				$scope.errorMsg = 'Unable to submit form';
			})
		}
		
	}]);
	</script>
  </body>
</html>