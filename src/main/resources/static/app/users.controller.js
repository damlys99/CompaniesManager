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
        vm.pageNum = 1;
        vm.pages = 0;

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


    }
})();