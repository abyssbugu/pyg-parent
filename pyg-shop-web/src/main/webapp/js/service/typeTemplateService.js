//定义service服务层
app.service("typeTemplateService", function($http) {
	// 查询所有
	this.findAll = function() {
		return $http.get("../typeTemplate/findAll");
	};

	// 分页查询
	this.findPage = function(page, rows) {
		return $http.get("../typeTemplate/findPage?page=" + page + "&rows=" + rows);
	};
	// 添加
	this.add = function(entity) {
		return $http.post("../typeTemplate/add", entity);
	}
	// 修改
	this.update = function(entity) {
		return $http.post("../typeTemplate/update", entity);
	};

	// 根据id查询品牌数据
	this.findOne = function(id) {
		return $http.get("../typeTemplate/findOne?id=" + id);
	};

	// 删除
	this.dele = function(ids) {
		return $http.get("../typeTemplate/delete?ids=" + ids);
	}

});