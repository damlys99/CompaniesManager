
app.controller('CompaniesController', function CompaniesController($http, $scope, $location) {
    $scope.companies = [];
    $scope.pageNum = 1;
    $scope.loggeduser = undefined;
    $scope.companiesCount = 0;
    $scope.industries  = [];
    $scope.dates = [];
    $scope.industriesFilter = "";
    $scope.datesFilter = "";
    $scope.filter = {
        industries : undefined,
        dates : undefined
    };
    $scope.alert = {
        type: "",
        message: ""
    };

    $scope.$watch(function() {
        $('.industriesfilter').selectpicker('refresh');
        $('.datesfilter').selectpicker('refresh');
    });


    init();

    function init() {
        getData($scope.pageNum);
        getIndustries();
        getDates();
        getCount();
        getLogged();
    }
    function getData(page){
        if (page) {
            var url = '/api/companies';
            var companiesPromise = $http.get(url, {
                params: {page: page, industries: $scope.filter.industries, dates: $scope.filter.dates}
            });
            companiesPromise.then(function (response) {
                $scope.companies = response.data;
                $scope.pageNum = page;
                getCount();
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

        var url = '/api/companies/count';
        var companiesPromise = $http.get(url);
        companiesPromise.then(function (response) {
            $scope.companiesCount = response.data;
        });
    }

    function getIndustries(){
        var url = '/api/industries/all';
        $http.get(url).then(function (response) {
            $scope.industries = response.data;

        })
    }
    function getDates(){
        var url = '/api/companies/dates';
        $http.get(url).then(function (response) {
            $scope.dates = response.data;

        })
    }

    $scope.goTo = function(page) {
        getData(page);
    };


    $scope.addCompany = function(company, form) {
        company.user =  $scope.loggeduser;
        company.added = new Date();
        company = angular.toJson(company);
        console.log(company);
        var url = '/api/companies';
        $http.post(url + "/add", company).then(function (suc){
            getData($scope.pageNum);
            formReset(form);
            $scope.alert.message = `Company ${suc.data.name} has been successfully added!`;
            $scope.alert.type = "success";
        })
            .catch(function (err){
                form.name.$setValidity("companyexists",false);
                console.log(err);
            })

    };

    $scope.modifyCompany = function(company) {
        let id = company.id;
        company = angular.toJson(company);
        var url = '/api/companies';
        var companiesPromise = $http.put(url + `/${id}/modify`, company);
        companiesPromise.then(function (suc) {
            $scope.alert.message = `Company ${suc.data.name} has been successfully modified`;
            $scope.alert.type = "success";
            $("#modifyCompanyModal").modal('hide');
            getData($scope.pageNum);
        }).catch(function (err) {
            $scope.modifyCompanyForm.name.$setValidity("companyexists", false);
            console.log(err);
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
        document.getElementsByName(form.$name)[0].reset();
    }


    $scope.setCompanyVar = function(company) {
        $scope.companyModel = JSON.parse(angular.toJson(company));
        $scope.companyModel.industry = $scope.industries.filter(i => i.id === company.industry.id)[0];
    };

    $scope.filterData = function () {
        if($scope.industriesFilter.length){
        $scope.filter.industries = $scope.industriesFilter.map(el => el.name).join();}
        else{
            $scope.filter.industries = undefined;}
        if($scope.datesFilter.length){
        $scope.filter.dates = $scope.datesFilter.map(el => el.added).join();}
        else{
            $scope.filter.dates = undefined;}
        console.log("aa");
        getData($scope.pageNum);
    }


});