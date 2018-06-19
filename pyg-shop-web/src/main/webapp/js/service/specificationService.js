//定义service服务层
app.service("specificationService", function($http) {
	// 查询所有
	this.findAll = function() {
		return $http.get("../specification/findAll");
	};

	// 分页查询
	this.findPage = function(page, rows) {
		return $http.get("../specification/findPage?page=" + page + "&rows=" + rows);
	};
	// 添加
	this.add = function(entity) {
		return $http.post("../specification/add", entity);
	}
	// 修改
	this.update = function(entity) {
		return $http.post("../specification/update", entity);
	};

	// 根据id查询品牌数据
	this.findOne = function(id) {
		return $http.get("../specification/findOne?id=" + id);
	};

	// 删除
	this.dele = function(ids) {
		return $http.get("../specification/delete?ids=" + ids);
	}
	
	////查询规格表数据，进行多项选择
	this.findSpecList = function(){
		return $http.get("../specification/findSpecList");
	}

});