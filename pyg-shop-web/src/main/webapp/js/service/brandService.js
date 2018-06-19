//定义service服务层
app.service("brandService", function($http) {
	// 查询所有
	this.findAll = function() {
		return $http.get("../brand/findAll");
	};

	// 分页查询
	this.findPage = function(page, rows) {
		return $http.get("../brand/findPage?page=" + page + "&rows=" + rows);
	};
	// 添加
	this.add = function(entity) {
		return $http.post("../brand/add", entity);
	}
	// 修改
	this.update = function(entity) {
		return $http.post("../brand/update", entity);
	};

	// 根据id查询品牌数据
	this.findOne = function(id) {
		return $http.get("../brand/findOne?id=" + id);
	};

	// 删除
	this.dele = function(ids) {
		return $http.get("../brand/delete?ids=" + ids);
	}
	//查询品牌数据，加载多项选择下拉框
	this.findBrandList = function(){
		return $http.get("../brand/findBrandList");
	}

});