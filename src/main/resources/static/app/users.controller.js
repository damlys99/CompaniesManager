(function () {
    'use strict';

    angular
        .module('app', [])
        .controller('UsersController', UsersController);

    UsersController.$inject = ['$http', '$scope'];


    function UsersController($http, $scope) {
        var vm = this;
        vm.users = [];
        vm.nextPage = nextPage;
        vm.prevPage = prevPage;
        vm.goTo = goTo;
        vm.addUser = addUser;
        vm.modifyUser = modifyUser;
        vm.clearAlert = clearAlert;
        vm.delUser = delUser;
        vm.setUserVar = setUserVar;
        vm.user = undefined;
        vm.modalType = "";
        vm.pageNum = 1;
        vm.pages = 0;
        $scope.role = 'User';
        vm.alert = {
            type: "",
            message: ""
        };

        init();

        function init() {
            var url = '/api/users';
            var usersPromise = $http.get(url);
            usersPromise.then(function (response) {
                vm.users = response.data;
            });
            getCount();

        }

        function nextPage(currPage) {
            goTo(currPage + 1);
        }

        function prevPage(currPage) {
            goTo(currPage - 1);
        }

        function getCount() {

            var url = 'api/users/count';
            var usersPromise = $http.get(url);
            usersPromise.then(function (response) {
                vm.pages = parseInt((response.data - 1) / 10 + 1);
            });
        }


        function goTo(page) {
            if (page) {
                var url = '/api/users';
                var usersPromise = $http.get(url, {
                    params: {page: page}
                });
                usersPromise.then(function (response) {
                    vm.users = response.data;
                    vm.pageNum = page;
                }).catch(function (err) {
                });
            }
        }

        function addUser(user, role, currPage, form) {
            role = JSON.stringify(`ROLE_${role.toUpperCase()}`);
            console.log(role);
            var url = '/api/users';
            var usersPromise = $http.post(url + "/add", user);
            usersPromise.then(function (suc) {
                vm.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully added`;
                vm.alert.type = "success";
                $http.put(url + "/" + suc.data.id + "/setrole", role).then(function (ok) {

                }).catch(function (err) {
                    console.log(err);
                });
            }).catch(function (err, status, headers, config) {
                vm.alert.type = "danger";
                vm.alert.message = "Couldn't add new user";
            }).finally(function () {
                //document.getElementById("addUserForm").reset();
                formReset(form);
                getCount();
                goTo(currPage);
            });

        }

        function modifyUser(user, role, currPage, form) {
            role = JSON.stringify(`ROLE_${role.toUpperCase()}`);
            console.log(role);
            var url = '/api/users';
            var usersPromise = $http.put(url + `/${user.id}/modify`, user);
            usersPromise.then(function (suc) {
                vm.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully modified`;
                vm.alert.type = "success";
                $http.put(url + "/" + suc.data.id + "/setrole", role).then(function (ok) {
                }).then(function (resp) {
                    goTo(currPage);
                })
                    .catch(function (err) {
                        console.log(err);
                    });
            }).catch(function (err, status, headers, config) {
                vm.alert.type = "danger";
                vm.alert.message = "Couldn't modify user";
            }).finally(function () {
                $("#modifyUserModal").modal('hide');
                $scope.role = 'User';
                //goTo(currPage);
            });

        }

        function delUser(user, currPage) {
            var url = `api/users/${user.id}/delete`;
            var usersPromise = $http.put(url);
            usersPromise.then(function (response) {
                vm.alert.type = "success";
                vm.alert.message = `User ${user.name} ${user.surname} has been successfully deleted`;
                getCount();
                if (vm.pages <= vm.pageNum && vm.pages > 0) {
                    goTo(currPage - 1);
                } else
                    goTo(currPage);

            }).catch(function (err) {
                vm.alert.type = "danger";
                vm.alert.message = "Couldn't delete user";
            })
        }

        function clearAlert() {
            vm.alert.type = "";
            vm.alert.message = "";
        }

        function formReset(form) {
            form.$invalid = "true";
            form.$pristine = "true";
            form.$untouched = "true";
            document.getElementsByName(form.$name)[0].reset();
        }


        function setUserVar(user) {
            $scope.userModel = angular.copy(user);
            $scope.userModel.dateOfBirth = new Date($scope.userModel.dateOfBirth);
            $scope.role = user.role.name.replace("ROLE_", "");
            $scope.role = $scope.role.charAt(0).toUpperCase() + $scope.role.slice(1).toLowerCase();
            console.log($scope.role);
        }

        $scope.setRole = function () {
            $scope.role = "User";
        }
    }
})();