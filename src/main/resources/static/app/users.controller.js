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
        vm.pageNum = 1;

        init();
        
        function init() {
            var url = '/api/users';
            var usersPromise = $http.get(url);
            usersPromise.then(function(response) {
                vm.users = response.data;
            });

        }
        function nextPage(currPage){

            var url = '/api/users';
            var usersPromise = $http.get(url, {
                params: {page:(currPage+1)}
            });
            usersPromise.then(function(response){
                vm.users = response.data;
            }).catch(function(err){});
            vm.pageNum += 1;
        }
        function prevPage(currPage){

            var url = '/api/users';
            var usersPromise = $http.get(url, {
                params: {page:(currPage-1)}
            });
            usersPromise.then(function(response){
                vm.users = response.data;
            }).catch(function(err){});
            vm.pageNum -=1;
        }



    }
})();