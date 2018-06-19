//定义控制器
//依赖注入service
app.controller("specificationController", function($scope,$controller,specificationService) {
	
	//继承
	//把父控制器域传递给子控制器域
	$controller('baseController',{$scope:$scope});
	
	// 定义函数
	// 查询所有
	$scope.findAll = function() {
		// 发送请求
		specificationService.findAll().success(function(data) {
			$scope.list = data;
		})
	};	

	// 定义方法，传递分页查询参数，实现分页
	$scope.findPage = function(page, rows) {
		// 发送请求，实现分页查询
		specificationService.findPage(page, rows).success(function(data) {
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
		if ($scope.entity.tbSpecification.id != null) {
			// 修改
			obj = specificationService.update($scope.entity);
		} else {
			// 添加
			obj = specificationService.add($scope.entity);
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
		specificationService.findOne(id).success(function(data) {
			$scope.entity = data;
		})
	};

	// 订单删除方法
	$scope.dele = function() {
		// 发送删除请求
		specificationService.dele($scope.selectIds).success(function(data) {
			// 判断是否保存成功
			if (data.success) {
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		})
	};
	
	
	//初始化传递参数结构
	//angularJS对象初始化
	//$scope.entity.optionList=[{},{},{}]
	//新增一行操作
	$scope.addTableRow = function(){
		//向规格选项集合属性中推送空对象
		$scope.entity.optionList.push({});
	}
	
	//删除规格选项行
	$scope.deleTableRow = function(index){
		//参数1：删除集合元素角标
		//参数2：删除的个数
		$scope.entity.optionList.splice(index,1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

})