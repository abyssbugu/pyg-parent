 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
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
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
            //保存之前，获取富文本编辑器值，把值赋值给需要保存对象字段
            $scope.entity.goodsDesc.introduction = editor.html();
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
                    //重新查询
                    //清空保存之前数据
                    $scope.entity = {};
                    //清空富文本编辑器
                    editor.html('');
				}else{
					alert(response.message);
				}
			}		
		);				
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


    //查询商品分类1级分类菜单节点
    $scope.selectItemCat1List = function(){
        //调用商品分类服务
        itemCatService.findItemCatListByParentId(0).success(function(data){
            $scope.itemCat1List = data;
        })
    };

    //选择第一级菜单中，动态展示出一级菜单所对应二级菜单
    //方法：根据一级菜单id，查询子菜单
    //问题：怎么确定第一级发生了变化？
    //解决方案：使用监控服务：$watch，监控一级，二级节点变化，一旦发现节点发生变化，立马查询下级节点
    //问题：监控谁？ 监控entity.goods.category1Id，分类id
    //newValue：变化后的新的值
    //oldValue: 变化之前的值
    $scope.$watch('entity.goods.category1Id',function(newValue,oldValue){
        //调用服务方法，根据新的分类id查询下级菜单
        itemCatService.findItemCatListByParentId(newValue).success(function(data){
            $scope.itemCat2List = data;
        })
    });

    //监听二级节点分类id
    $scope.$watch('entity.goods.category2Id',function(newValue,oldValue){
        //调用服务方法，根据新的分类id查询下级菜单
        itemCatService.findItemCatListByParentId(newValue).success(function(data){
            $scope.itemCat3List = data;
        })
    });

    //监控三级菜单分类id,获取模板id
    $scope.$watch('entity.goods.category3Id',function(newValue,oldValue){
        //调用服务方法，根据新的分类id查询下级菜单
        itemCatService.findOne(newValue).success(function(data){
            //获取模板id
            $scope.entity.goods.typeTemplateId=data.typeId;
        })
    });

    //监控模板id变化，根据模板id查询模板对象
    $scope.$watch('entity.goods.typeTemplateId',function(newValue,oldValue){
        //调用服务方法，根据新的分类id查询下级菜单
        typeTemplateService.findOne(newValue).success(function(data){
            //获取模板id
            $scope.typeTemplate = data;
            //获取模板中品牌数据
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
        })
    });


});	
