userApp.controller("UserNotesController", function($scope, $http){

    $scope.notes = [];
    init();

    function init(){
        let promise = $scope.$parent.user();
        promise.then(res =>{
            $scope.user = res;
            getData();
        })
    }

    function getData(){
        $http.get('/api/notes/all', {params: {user : $scope.user.id}}).then(function(res){
            $scope.notes = res.data;
        });
    }




});