companyApp.controller("CompanyNotesController", function($scope, $http, $q){

    $scope.notes = [];
    $scope.alert = $scope.$parent.alert;
    $scope.edit = [];

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
        return $http.get('/api/notes/all', {params: {company : $scope.company.id}}).then(function(res){
            $scope.notes = res.data;
        });
    }

    $scope.addNote = function (note, form) {
        note.user = $scope.loggeduser;
        note.company = $scope.company;
        note = angular.toJson(note);
        var url = '/api/notes/add';
        $http.post(url,note).then(function(suc){
            $scope.alert.message = `Note has been successfully added`;
            $scope.alert.type = 'success';
            getData().then(res => {
                formReset(form);
            });

        })
    };

    $scope.delNote = function (note){
        var url = `/api/notes/${note.id}/delete`;
        $http.put(url).then(function(suc){
            $scope.alert.message = `Note has been successfully deleted`;
            $scope.alert.type = 'success';
            getData();
        })

    };

    $scope.modifyNote = function(note){
        console.log(note);
        var url = `/api/notes/${note.id}/modify`;
        var note = angular.toJson(note);
        console.log(note);
        $http.put(url, note).then(function(suc){
            $scope.alert.message = `Note has been successfully modified`;
            $scope.alert.type = 'success';
            getData().then(res=>{
                $scope.hideAllForms();
            });

        })
    };

    $scope.revealForm = function(ind){
        $scope.hideAllForms();
        $scope.edit[ind] = true;
    };
    $scope.hideAllForms = function(){
        for(let i=0;i<$scope.edit.length;i++){
            $scope.edit[i] = false;
        }
    };

    $scope.setNoteVar = function (note) {
        $scope.noteModel = angular.copy(note);
    };

    function formReset(form) {
        form.$invalid = "true";
        form.$pristine = "true";
        form.$untouched = "true";
        document.getElementsByName(form.$name)[0].reset();
    }
});