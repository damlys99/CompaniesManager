companyApp.controller("CompanyContactsController", function($scope, $http, $q){

    $scope.contacts = [];
    $scope.alert = $scope.$parent.alert;
    $scope.searchInput = "";
    $scope.filter = undefined;

    init();

    function init(){
        let promise1 = $scope.$parent.company();
        let promise2 = $scope.$parent.logged();
        $q.all([promise1,promise2]).then(res =>{
            $scope.company = res[0];
            $scope.loggeduser = res[1];
            getData();
        })
    }
    function getData(){
        $http.get('/api/contacts/all', {params: {company : $scope.company.id, search: $scope.filter}}).then(function(res){
            $scope.contacts = res.data;
        });
    }

    $scope.addContact = function (contact, form) {
        contact.user = $scope.loggeduser;
        contact.company = $scope.company;
        contact = angular.toJson(contact);
        var url = '/api/contacts/add';
        $http.post(url,contact).then(function(suc){
            $scope.alert.message = `Contact ${suc.data.name} ${suc.data.surname} has been successfully added`;
            $scope.alert.type = 'success';
            formReset(form);
            getData();
        })
    };

    $scope.delContact = function (contact){
        var url = `/api/contacts/${contact.id}/delete`;
        $http.put(url).then(function(suc){
          $scope.alert.message = `Contact ${suc.data.name} ${suc.data.surname} has been successfully deleted`;
          $scope.alert.type = 'success';
          getData();
        })

    };

    $scope.modifyContact = function(contact){
        var url = `/api/contacts/${contact.id}/modify`;
        var contact = angular.toJson(contact);
        console.log(contact);
        $http.put(url, contact).then(function(suc){
            $scope.alert.message = `Contact ${suc.data.name} ${suc.data.surname} has been successfully modified`;
            $scope.alert.type = 'success';
            getData();
            $("#modifyContactModal").modal('hide');
        })
    };
    $scope.filterData = function () {
        if($scope.searchInput.length) {
            $scope.filter = $scope.searchInput;
        }
        else{
            $scope.filter = undefined;
        }
        getData();
    };

    $scope.setContactVar = function (contact) {
        $scope.contactModel = angular.copy(contact);
    };

    function formReset(form) {
        form.$invalid = "true";
        form.$pristine = "true";
        form.$untouched = "true";
        document.getElementsByName(form.$name)[0].reset();
    }

});