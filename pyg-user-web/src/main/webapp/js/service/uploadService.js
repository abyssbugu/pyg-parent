//定义service服务层
app.service("uploadService", function($http) {
	//上传方法
	this.uploadFile = function() {
		//创建form表单对象
		var formData = new FormData();
		//把表单对象封装表单数据
		formData.append("file", file.files[0]);
		
		return $http({
			method : 'POST',
			url : "../upload/pic",
			data : formData,
			headers : {
				'Content-Type' : undefined
			},
			transformRequest : angular.identity
		});
	}

});