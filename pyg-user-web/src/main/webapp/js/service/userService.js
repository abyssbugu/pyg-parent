//服务层
app.service('userService',function($http){
	    	
	//读取列表数据绑定到表单中
	//用户注册时：需要判断用户验证码是否正确。
	this.register=function(entity,smsCode){
		return $http.post('../user/register?smsCode='+smsCode,entity);		
	}
	
	//根据手机号，获取验证码
	this.getSmsCode=function(phone){
		return $http.get('../user/getSmsCode?phone='+phone);		
	}
	
});
