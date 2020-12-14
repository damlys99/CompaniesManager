var userApp = angular.module("userApp", ["ngRoute", "ngCookies"]);
userApp.config(function ($routeProvider, $locationProvider, $cookiesProvider) {
    $routeProvider
        .when("/contacts", {
            templateUrl: "/app/modules/user/templates/contacts.html",
            controller: "UserContactsController"
        })
        .when("/notes", {
            templateUrl: "/app/modules/user/templates/notes.html",
            controller: "UserNotesController"
        })
        .when("/companies", {
        templateUrl: "/app/modules/user/templates/companies.html",
            controller: "UserCompaniesController"
    })
        .otherwise({
            redirectTo:"/companies"
        });

});

userApp.controller("navController", function($scope, $location, $http, $cookies) {
    var userId = $cookies.get("userid");
    $scope.getClass = function (path) {
        return ($location.path() === path) ? 'active' : '';
    };
    $scope.user = function () {
        return $http.get(`/api/users/${userId}`).then(function (res) {
            return res.data;
        })

    }



});