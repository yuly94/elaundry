angular.module('MyApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache'])
  
.controller('AppCtrl', ['$scope', '$http','$mdToast', function($scope, $http, $mdToast) {
          
                $scope.showHints = true;
		
		this.postForm = function() {
		
			var encodedString = 'username=' +
				encodeURIComponent(this.inputData.username) +
				'&password=' +
				encodeURIComponent(this.inputData.password);
				
                        console.log("encoded data "+ encodedString);
                                
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
                                    //$scope.errorMsg = "Login not correct";
                                      
                                    showError();
				}
			})
			.error(function(data, status, headers, config) {
				//$scope.errorMsg = 'Unable to submit form';
                         
                              // showToast();
                               console.log(data);
                                })
                        }
                
                        var showError = function() {
                            $mdToast.show(
                            $mdToast.simple()
                                .textContent('Hello World!')                       
                                .hideDelay(3000)
                                );
                            };
               

  }])


