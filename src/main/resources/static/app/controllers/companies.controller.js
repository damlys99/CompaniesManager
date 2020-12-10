
app.controller('CompaniesController', CompaniesController);

CompaniesController.$inject = ['$http', '$scope'];


function CompaniesController($http, $scope) {
    $scope.companies = [];
    $scope.pageNum = 1;
    $scope.loggeduser = undefined;
    $scope.companiesCount = 0;
    $scope.industries  = [];
    $scope.alert = {
        type: "",
        message: ""
    };

    init();

    function init() {
        getData($scope.pageNum);
        getIndustries();
        getCount();
        getLogged();
    }
    function getData(page){
        if (page) {
            var url = '/api/companies';
            var companiesPromise = $http.get(url, {
                params: {page: page}
            });
            companiesPromise.then(function (response) {
                $scope.companies = response.data;
                $scope.pageNum = page;
            }).catch(function (err) {
            });
        }
    }
    function getLogged(){
        var url='/api/users/logged';
        $http.get(url).then(function (suc){
            $scope.loggeduser = suc.data;
        })
    }
    function getCount() {

        var url = 'api/companies/count';
        var companiesPromise = $http.get(url);
        companiesPromise.then(function (response) {
            $scope.companiesCount = response.data;
        });
    }

    function getIndustries(){
        var url = 'api/industries/all';
        $http.get(url).then(function (response) {
            $scope.industries = response.data;

        })
    }

    $scope.goTo = function(page){
        getData(page);
    };

    /*        $scope.nextPage = function(){
                $scope.pageNum++;
                getData($scope.pageNum);
            };

            $scope.prevPage = function() {
                $scope.pageNum--;
                getData($scope.pageNum);
            };*/

    $scope.addCompany = function(company, form) {
        company.user =  $scope.loggeduser;
        company.added = new Date();
        company = angular.toJson(company);
        console.log(company);
        var url = '/api/companies';
        $http.post(url + "/add", company).then(function (suc){
            getData($scope.pageNum);
            formReset(form);
        })
            .catch(function (err){
                form.name.$setValidity(companyexists,false);
                console.log(err);
            })

    };

    $scope.modifyCompany = function(company, industry) {
        industry = JSON.stringify(industry);
        console.log(industry);
        var url = '/api/companies';
        var companiesPromise = $http.put(url + `/${company.id}/modify`, company);
        companiesPromise.then(function (suc) {
            $scope.alert.message = `Company ${suc.data.name} has been successfully modified`;
            $scope.alert.type = "success";
            $("#modifyCompanyModal").modal('hide');
            $http.put(url + "/" + suc.data.id + "/setindustry", industry).then(function (ok) {
            }).then(function (resp) {
                getData($scope.pageNum);
            })
                .catch(function (err) {
                    console.log(err);
                });
        }).catch(function (err, status, headers, config) {
            $scope.modifyCompanyForm.name.$setValidity("companyexists", false);
        })

    };

    $scope.delCompany = function(company) {
        var url = `api/companies/${company.id}/delete`;
        var companiesPromise = $http.put(url);
        companiesPromise.then(function (response) {
            $scope.alert.type = "success";
            $scope.alert.message = `Company ${response.data.name} has been successfully deleted`;
            getCount();
            if ($scope.pages <= $scope.pageNum && $scope.pages > 0) {
                $scope.pageNum--;
                getData($scope.pageNum);
            } else
                getData($scope.pageNum);

        }).catch(function (err) {
            $scope.alert.type = "danger";
            $scope.alert.message = "Couldn't delete company";
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
        form.reset();
    }


    $scope.setCompanyVar = function(company) {
        $scope.companyModel = angular.copy(company);
        $scope.companyModel.added = new Date($scope.companyModel.added);
    };


}