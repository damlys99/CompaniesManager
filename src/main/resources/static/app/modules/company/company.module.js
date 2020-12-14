var companyApp = angular.module("companyApp", ["ngRoute", "ngCookies"]);
companyApp.config(function ($routeProvider, $locationProvider, $cookiesProvider) {
    $routeProvider
        .when("/contacts", {
            templateUrl: "/app/modules/company/templates/contacts.html",
            controller: "CompanyContactsController",
        })
        .when("/notes", {
            templateUrl: "/app/modules/company/templates/notes.html",
            controller: "CompanyNotesController"
        })
        .otherwise({
            redirectTo: "/contacts"
        })

});
companyApp.controller("navController", function($scope, $location, $http, $cookies) {
    var companyId = $cookies.get("companyid");
    $scope.alert = {
        message: "",
        type: ""
    };
    $scope.getClass = function (path) {
        return ($location.path() === path) ? 'active' : '';
    };
    $scope.logged = function() {
        return $http.get('/api/users/logged').then(function (res) {
            return res.data;
        });
    };

    $scope.company = function() {
        return $http.get(`/api/companies/${companyId}`).then(function (res) {
            return res.data;
        });
    };
    $scope.clearAlert = function() {
        $scope.alert.type = "";
        $scope.alert.message = "";
    };
});