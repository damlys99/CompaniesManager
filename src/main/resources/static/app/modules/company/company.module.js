var companyApp = angular.module("companyApp", ["ngRoute"]);
companyApp.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when("/notes", {
            templateUrl: "/app/modules/company/templates/notes.html",
            controller: "UserCompaniesController"
        })
        .otherwise({
            templateUrl: "/app/modules/company/templates/contacts.html"
        });

});

companyApp.controller("navController", function($scope, $location) {
    $scope.getClass = function (path) {
        return ($location.path() === path) ? 'active' : '';
    }
});