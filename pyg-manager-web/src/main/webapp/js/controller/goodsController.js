 //控制层 
app.controller('goodsController' ,function($scope,$controller   ,goodsService,itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承


    //商家商品审核方法
    $scope.updateStatus =function(status){
        //调用service方法
        goodsService.updateStatus($scope.selectIds,status).success(function(data){
            //判断
            if(data.success){
                $scope.reloadList();
                //清空
                $scope.selectIds=[];
            }else{
                alert(data.message);
            }
        })
    }

    $scope.status = [ '未审核', '已审核', '审核未通过', '关闭' ];// 商品状态

    // 查询所有商品分类
    // 1，定义数组
    // 2,查询出所有分类封装到数组
    // 3,把分类id作为数组的角标。分类名称值此此角标对应值。
    $scope.catList = [];
    // 查询所有
    $scope.findAllItemCatList = function() {
        itemCatService.findAll().success(function(data) {
            // 循环回调函数集合
            for (var i = 0; i < data.length; i++) {
                // catList = ["","图书、音像、电子书刊","电子书刊","电子书"....]
                // 获取数组值：catList[1]--->catList[entity.category1Id]
                // catList[0]="张三"
                $scope.catList[data[i].id] = data[i].name;
            }
        })
    };
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}

	// 保存
	$scope.save = function() {
		var serviceObject;// 服务层对象
		if ($scope.entity.goods.id != null) {// 如果有ID
			serviceObject = goodsService.update($scope.entity); // 修改
		} else {
			// 保存之前，获取富文本编辑器值，把值赋值给需要保存对象字段
			$scope.entity.goodsDesc.introduction = editor.html();
			// 保存
			serviceObject = goodsService.add($scope.entity);// 增加
		}
		serviceObject.success(function(response) {
			if (response.success) {
				// 重新查询
				// 清空保存之前数据
				$scope.entity = {};
				// 清空富文本编辑器
				editor.html('');
			} else {
				alert(response.message);
			}
		});
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
