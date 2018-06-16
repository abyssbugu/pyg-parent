//定义控制器
//依赖注入service
app.controller("typeTemplateController", function($scope,$controller,typeTemplateService) {
	
	//继承
	//把父控制器域传递给子控制器域
	$controller('baseController',{$scope:$scope});
	
	// 定义函数
	// 查询所有
	$scope.findAll = function() {
		// 发送请求
		typeTemplateService.findAll().success(function(data) {
			$scope.list = data;
		})
	};	

	// 定义方法，传递分页查询参数，实现分页
	$scope.findPage = function(page, rows) {
		// 发送请求，实现分页查询
		typeTemplateService.findPage(page, rows).success(function(data) {
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
			obj = typeTemplateService.update($scope.entity);
		} else {
			// 添加
			obj = typeTemplateService.add($scope.entity);
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
		typeTemplateService.findOne(id).success(function(data) {
			$scope.entity = data;
		})
	};

	// 订单删除方法
	$scope.dele = function() {
		// 发送删除请求
		typeTemplateService.dele($scope.selectIds).success(function(data) {
			// 判断是否保存成功
			if (data.success) {
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		})
	}
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
    };

    //obj = [{"id":"1","text":"TCL"},{"id":"1","text":"TCL"}]
    //第一个对象：obj[0].text (√)
    //情况： var key = "text";  obj[0].key (×) 如果获取json格式的值得key是变量：obj[0][key]

    //定义函数，把显示json数组解析，只需要显示text名称即可，中间使用逗号分隔即可
    //第一个参数： text
    //第二个参数：json数组
    $scope.jsonToStr = function(key,value){
        //把json字符串转换成json对象
        var jsonObj = JSON.parse(value);
        //初始化一个空字符，用来组装数据
        var str = "";
        //循环json对象数组，获取text对应值，组装一个字符串
        //[{"id":"1","text":"TCL"},{}]
        for(var i=0;i<jsonObj.length;i++){
            if(i>0){
                str+=",";
            }
            str +=jsonObj[i][key];
        }

        return str;

    }
})