(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['$http'];


    function UsersController($http) {
        var vm = this;
        vm.users = [];
        vm.nextPage = nextPage;
        vm.prevPage = prevPage;
        vm.goTo = goTo;
        vm.addUser = addUser;
        vm.clearAlert = clearAlert;
        vm.pageNum = 1;
        vm.pages = 0;
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

            var url = '/api/users';
            var usersPromise = $http.get(url, {
                params: {page: (currPage + 1)}
            });
            usersPromise.then(function (response) {
                vm.users = response.data;
            }).catch(function (err) {
            });
            vm.pageNum += 1;
        }

        function prevPage(currPage) {

            var url = '/api/users';
            var usersPromise = $http.get(url, {
                params: {page: (currPage - 1)}
            });
            usersPromise.then(function (response) {
                vm.users = response.data;
            }).catch(function (err) {
            });
            vm.pageNum -= 1;
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

        function addUser(user, roles, currPage) {
            var rolesarr = [];
            for(let key in roles){
                rolesarr.push(roles[key]);
            }
            rolesarr = JSON.stringify(rolesarr);
           var url = '/api/users';
            var usersPromise = $http.post(url + "/add", user);
            usersPromise.then(function (suc) {
                vm.alert.message = "User " + suc.data.name + " " + suc.data.surname + " has been successfully added.";
                vm.alert.type = "success";
                $http.post(url + "/" + suc.data.id + "/setroles", rolesarr).then(function (ok){

                }).catch(function (err){
                    console.log(err);
                });
                $http.get(url, {
                        params: {page: currPage}
                    }
                ).then(function (response) {
                    vm.users = response.data;
                    getCount();
                });
            }).catch(function (err, status, headers, config) {
                vm.alert.type = "danger";
                vm.alert.message = "Couldn't add new user";
            }).finally(function (){
                document.getElementById("adduserForm").reset();
            });


        }

        function clearAlert() {
            vm.alert.type = "";
            vm.alert.message = "";
        }

    }
})();