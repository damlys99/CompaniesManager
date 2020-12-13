userApp.controller("UserNotesController", function($scope, $http){

    $scope.notes = [];

    function getData(){
        $http.get('/api/notes/all', {params: {user : $scope.$parent.userId}}).then(function(res){
            $scope.notes = res.data;
        });
    }

    getData();



});