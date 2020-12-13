userApp.controller("UserCompaniesController", function($scope, $http){

    $scope.companies = [];

     function getData(){
             $http.get('/api/companies/all', {params: {user : $scope.$parent.userId}}).then(function(res){
                 $scope.companies = res.data;
             });
     }

    getData();



});