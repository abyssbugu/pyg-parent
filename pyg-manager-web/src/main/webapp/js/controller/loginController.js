//控制层
app.controller('loginController', function ($scope, loginService) {

    //载入登录名
    $scope.loadLoginName = function () {
        loginService.loadLoginName().success(function (data) {
            $scope.loginName = data.loginName;
        })
    }

});	
