userApp.controller("UserContactsController", function($scope, $http){

    $scope.contacts = [];

    function getData(){
        $http.get('/api/contacts/all', {params: {user : $scope.$parent.userId}}).then(function(res){
            $scope.contacts = res.data;
        });
    }

    getData();



});