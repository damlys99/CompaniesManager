
    app.controller('UsersController', UsersController);

    UsersController.$inject = ['$http', '$scope'];



    function UsersController($http, $scope) {
        $scope.users = [];
        $scope.pageNum = 1;
        $scope.usersCount = 0;
        $scope.roles = undefined;
        $scope.newuser = {};
        $scope.alert = {
            type: "",
            message: ""
        };

        init();

        function init() {
            getData($scope.pageNum);
            getCount();
            getRoles();
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
                    getCount();
                }).catch(function (err) {
                });
            }
        }

        function getRoles(){
            var url = '/api/roles/all';
            $http.get(url).then(function (succ){
                $scope.roles = succ.data;
            });
        }

        function getCount() {

            var url = 'api/users/count';
            var usersPromise = $http.get(url);
            usersPromise.then(function (response) {
                $scope.usersCount = response.data;
            });
        }
        
        $scope.goTo = function(page){
            getData(page);
        };

        $scope.addUser = function(user, form) {
            user = angular.toJson(user);
            console.log(user);
            var url = '/api/users';
            $http.post(url + "/add", user).then(function (suc){
                getData($scope.pageNum);
                formReset(form);
                $scope.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully added!`;
                $scope.alert.type = "success";
            })
                .catch(function (err){
                    form.username.$setValidity("userexists",false);
                    console.log(err);
                })
        };

        $scope.modifyUser = function(user) {
            let id = user.id;
            user = angular.toJson(user);
            var url = '/api/users';
            $http.put(url + `/${id}/modify`, user).then(function (suc) {
                $scope.alert.message = `User ${suc.data.name} ${suc.data.surname} has been successfully modified`;
                $scope.alert.type = "success";
                $("#modifyUserModal").modal('hide');
                getData($scope.pageNum);
            }).catch(function (err) {
                $scope.modifyUserForm.username.$setValidity("userexists", false);
                console.log(err);
            })

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
            console.log($scope.userModel);
            $scope.userModel.dateOfBirth = new Date($scope.userModel.dateOfBirth);
            $scope.userModel.role = $scope.roles.filter(i => i.id === user.role.id)[0];
        };

        $scope.setRole = function () {
            $scope.newuser.role = $scope.roles.filter(i => i.nameWithoutPrefix === "User")[0];
        };


    }