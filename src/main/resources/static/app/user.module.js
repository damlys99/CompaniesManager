var userApp = angular.module("userApp", ["ngRoute"]);
userApp.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when("/contacts", {
            templateUrl: "/app/templates/contacts.html"
        })
        .when("/notes", {
            templateUrl: "/app/templates/notes.html"
        })
        .otherwise({
        templateUrl: "/app/templates/companies.html"
    });
/*    $locationProvider.html5Mode(false).hashPrefix('#');*/

});

userApp.controller("navController", function($scope, $location) {
    $scope.getClass = function (path) {
        return ($location.path() === path) ? 'active' : '';
    }
});