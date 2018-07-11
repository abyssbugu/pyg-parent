//定义控制器
//依赖注入service
app.controller("loginController", function($scope,loginService) {
	
	//获取用户登录成功用户名
	$scope.loadLoginName = function() {
		// 发送请求
		loginService.loadLoginName().success(function(data) {
			$scope.loginName = data.loginName;
		})
	};	

	

})