 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			//给父id赋值
			$scope.entity.parentId = $scope.parentId;
			//保存
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.findItemCatListByParentId($scope.parentId);//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.findItemCatListByParentId($scope.parentId);//重新加载
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	
	//定义方法
	//根据父id查询商品分类
	$scope.findItemCatListByParentId = function(parentId){
		//记录父id，以便于保存
		$scope.parentId = parentId;
		//调用服务层方法
		itemCatService.findItemCatListByParentId(parentId).success(function(data){
			$scope.catList = data;
		})
	};
	
	//定义级别，记录商品分类级别变化
	//默认第一级
	$scope.grade =1;
	
	//定义方法增加级别
	$scope.setGrade = function(grade){
		$scope.grade = grade;
	};
	
	//顶级方法，随着级别的变化，记录每一级对象
	$scope.selectCatList = function(entity){
		//判断级别变化，当前属于哪一级别
		if($scope.grade==1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		};
		
		//如果第二级，记录对象
		if($scope.grade==2){
			$scope.entity_1 = entity;
			$scope.entity_2 = null;
		};
		
		//如果是第三级别
		if($scope.grade==3){
			$scope.entity_2 = entity;
		};
		
		//调用查询下级方法
		$scope.findItemCatListByParentId(entity.id);
		
	};
	
    
});	
