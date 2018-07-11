//控制层 
app.controller('userController', function($scope, userService) {

	// 用户注册
	$scope.register = function() {
		
		//判断密码是否一致
		if($scope.entity.password!=$scope.password){
			return;
		}
		//调用service服务，实现注册
		userService.register($scope.entity,$scope.smsCode).success(function(response) {
			if (response.success) {
				// 跳转登录页面
				location.href="login.html";
			} else {
				alert(response.message);
			}
		});
	};
	
	
	//注册之前，先获取验证码
	$scope.getSmsCode = function(){
		//调用服务层方法，获取验证码
		userService.getSmsCode($scope.entity.phone).success(function(data){
			if(data.success){
				//发送短信成功
				alert("发送成功");
			}else{
				alert("发送失败");
			}
		})
	}
	
	

});
