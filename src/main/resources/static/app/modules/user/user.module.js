var userApp = angular.module("userApp", ["ngRoute"]);
userApp.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when("/contacts", {
            templateUrl: "/app/modules/user/templates/contacts.html",
            controller: "UserContactsController"
        })
        .when("/notes", {
            templateUrl: "/app/modules/user/templates/notes.html",
            controller: "UserNotesController"
        })
        .otherwise({
        templateUrl: "/app/modules/user/templates/companies.html",
            controller: "UserCompaniesController"
    });

});

userApp.controller("navController", function($scope, $location, $http) {
    $scope.userId = $location.absUrl().slice($location.absUrl().lastIndexOf('/') +1);
    $scope.getClass = function (path) {
        return ($location.path() === path) ? 'active' : '';
    };



});