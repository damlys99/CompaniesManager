userApp.controller("UserCompaniesController", function($scope, $http){

    $scope.companies = [];
    init();

    function init(){
        let promise = $scope.$parent.user();
        promise.then(res =>{
            $scope.user = res;
            getData();
        })
    }

     function getData(){
             $http.get('/api/companies/all', {params: {user : $scope.user.id}}).then(function(res){
                 $scope.companies = res.data;
             });
     }



});