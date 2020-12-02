(function () {
    'use strict';

    angular
        .module('app', [])
        .controller('UsersController', UsersController);

    UsersController.$inject = ['$http', '$scope'];


    function UsersController($http, $scope) {
        $scope.users = [];
        $scope.pageNum = 1;
        $scope.pages = 0;
        $scope.role = 'User';
        $scope.alert = {
            type: "",
            message: ""
        };

        init();

        function init() {
            getData($scope.pageNum);
            getCount();
        }
        function getData(page){
            if (page) {
                var url = '/api/users';
                var usersPromise = $http.get(url, {
                    params: {page: page}
                });
                usersPromise.then(function (response) {
                    $scope.users = response.data;
                    $scope.pageNum = page;
                }).catch(function (err) {
                });
            }
        }

        function getCount() {

            var url = 'api/users/count';
            var usersPromise = $http.get(url);
            usersPromise.then(function (response) {
                $scope.pages = parseInt((response.data - 1) / 10 + 1);
            });
        }
        
        $scope.goTo = function(page){
            getData(page);
        };

        $scope.nextPage = function(){
            $scope.pageNum++;
            getData($scope.pageNum);
        };

        $scope.prevPage = function() {
            $scope.pageNum--;
            getData($scope.pageNum);
        };

        $scope.addUser = function(user, role, form) {
            role = JSON.stringify(`ROLE_${role.toUpperCase()}`);
            console.log(role);
            var url = '/api/users';
            var usersPromise = $http.post(url + "/add", user);
            usersPromise.then(function (suc) {
                $scope.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully added`;
                $scope.alert.type = "success";
                $http.put(url + "/" + suc.data.id + "/setrole", role).then(function (ok) {

                }).catch(function (err) {
                    console.log(err);
                });
            }).catch(function (err, status, headers, config) {
                $scope.alert.type = "danger";
                $scope.alert.message = "Couldn't add new user";
            }).finally(function () {
                //document.getElementById("addUserForm").reset();
                formReset(form);
                getCount();
                getData($scope.pageNum);
            });

        };

        $scope.modifyUser = function(user, role) {
            role = JSON.stringify(`ROLE_${role.toUpperCase()}`);
            console.log(role);
            var url = '/api/users';
            var usersPromise = $http.put(url + `/${user.id}/modify`, user);
            usersPromise.then(function (suc) {
                $scope.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully modified`;
                $scope.alert.type = "success";
                $http.put(url + "/" + suc.data.id + "/setrole", role).then(function (ok) {
                }).then(function (resp) {
                    getData($scope.pageNum);
                })
                    .catch(function (err) {
                        console.log(err);
                    });
            }).catch(function (err, status, headers, config) {
                $scope.alert.type = "danger";
                $scope.alert.message = "Couldn't modify user";
            }).finally(function () {
                $("#modifyUserModal").modal('hide');
                $scope.role = 'User';
            });

        };

        $scope.delUser = function(user) {
            var url = `api/users/${user.id}/delete`;
            var usersPromise = $http.put(url);
            usersPromise.then(function (response) {
                $scope.alert.type = "success";
                $scope.alert.message = `User ${user.name} ${user.surname} has been successfully deleted`;
                getCount();
                if ($scope.pages <= $scope.pageNum && $scope.pages > 0) {
                    $scope.pageNum--;
                    getData($scope.pageNum);
                } else
                    getData($scope.pageNum);

            }).catch(function (err) {
                $scope.alert.type = "danger";
                $scope.alert.message = "Couldn't delete user";
            })
        };

        $scope.clearAlert = function() {
            $scope.alert.type = "";
            $scope.alert.message = "";
        };

        function formReset(form) {
            form.$invalid = "true";
            form.$pristine = "true";
            form.$untouched = "true";
            document.getElementsByName(form.$name)[0].reset();
        }


        $scope.setUserVar = function(user) {
            $scope.userModel = angular.copy(user);
            $scope.userModel.dateOfBirth = new Date($scope.userModel.dateOfBirth);
            $scope.role = user.role.name.replace("ROLE_", "");
            $scope.role = $scope.role.charAt(0).toUpperCase() + $scope.role.slice(1).toLowerCase();
            console.log($scope.role);
        };

        $scope.setRole = function () {
            $scope.role = "User";
        }
    }
})();