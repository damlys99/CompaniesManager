userApp.controller("UserContactsController", function($scope, $http){

    $scope.contacts = [];
    init();

    function init(){
        let promise = $scope.$parent.user();
        promise.then(res =>{
            $scope.user = res;
            getData();
        })
    }

    function getData(){
        $http.get('/api/contacts/all', {params: {user : $scope.user.id}}).then(function(res){
            $scope.contacts = res.data;
        });
    }




});