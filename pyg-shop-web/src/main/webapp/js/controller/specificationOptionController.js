//定义控制器
//依赖注入service
app.controller("specificationOptionController", function($scope,$controller,specificationOptionService) {
	
	//继承
	//把父控制器域传递给子控制器域
	$controller('baseController',{$scope:$scope});
	
	// 定义函数
	// 查询所有
	$scope.findAll = function() {
		// 发送请求
		specificationOptionService.findAll().success(function(data) {
			$scope.list = data;
		})
	};	

	// 定义方法，传递分页查询参数，实现分页
	$scope.findPage = function(page, rows) {
		// 发送请求，实现分页查询
		specificationOptionService.findPage(page, rows).success(function(data) {
			// 把总记录数赋值分页控件totalItems
			$scope.paginationConf.totalItems = data.total;
			// 分页列表展示数据
			$scope.list = data.rows;
		})
	};

	// 添加修改品牌数据
	$scope.add = function() {

		var obj = null;
		// 判断是否是修改，还是添加操作
		if ($scope.entity.id != null) {
			// 修改
			obj = specificationOptionService.update($scope.entity);
		} else {
			// 添加
			obj = specificationOptionService.add($scope.entity);
		}

		// 发送请求实现保存
		obj.success(function(data) {
			// 判断是否保存成功
			if (data.success) {
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		})
	};

	// 根据id查询品牌数据
	$scope.findOne = function(id) {
		// 发送请求
		specificationOptionService.findOne(id).success(function(data) {
			$scope.entity = data;
		})
	};

	// 订单删除方法
	$scope.dele = function() {
		// 发送删除请求
		specificationOptionService.dele($scope.selectIds).success(function(data) {
			// 判断是否保存成功
			if (data.success) {
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		})
	}

})