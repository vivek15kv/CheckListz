var app = angular.module("checklist", []);
app.controller('checklistCtrl', function($scope, $http) {
	    $http.get("input.json").success(function(response)
	    {
	    	$scope.menus = [];
	    	angular.forEach(response.menus,function(value,key){
	    		$scope.menus.push(value);
	    		$scope.currentShow = 0;
	    		$scope.menus[$scope.currentShow].isActive = true;
	    	});
	    });
	    
	   //add item to current menu
	    $scope.addTodo = function  () {
			/*Should prepend to array*/
			$scope.menus[$scope.currentShow].menuItem.splice(0, 0, {content: $scope.newTodo, isDone: false });
			/*Reset the Field*/
			$scope.newTodo = "";
		};
		
		
		$scope.changeTodo = function  (i) {
			$scope.currentShow = i;
			for (var j=0;j<$scope.menus.length;j++)
			$scope.menus[j].isActive = false;
			$scope.menus[i].isActive = true;
		};
		
		//delete item of current menu
		$scope.deleteTodo = function  (index) {
			$scope.menus[$scope.currentShow].menuItem.splice(index, 1);
		};
		
		//add menu in navigation bar
		$scope.addMenu = function() {
			
		}
		
});
	    
	   
  