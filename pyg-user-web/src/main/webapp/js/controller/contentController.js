 //控制层 
app.controller('contentController' ,function($scope,contentService){	
	
	//定义广告数据数组，封装首页广告数据
	$scope.adList = [];
	
	//数组类型赋值：[[{}],[{},{}]]
	//adList[1]=[{}]
	//$scope.ad = {};
	//ad[1]=a ----> {"1":"a"}
	
	//查询首页广告数据方法
	$scope.findAdList = function(categoryId){
		//调用广告内容服务方法，查询广告内容数据
		contentService.findAdList(categoryId).success(function(data){
			//$scope.adList = data;
			//adList=[[{},{}],[{},{}],[]]
			$scope.adList[categoryId]=data;
		})
	}
	
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		contentService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		contentService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		contentService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=contentService.update( $scope.entity ); //修改  
		}else{
			serviceObject=contentService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		contentService.dele( $scope.selectIds ).success(
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
		contentService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	
	//定义搜索方法
	//根据关键词进行搜索
	//如何使用静态页面跨系统传参？
	//angualrJS参数路由问题？ 使用静态页面向另一个页面传递参数使用#?拼接参数。
	$scope.loadSearch = function(){
		window.location.href="http://localhost:8083/search.html#?keywords="+$scope.keywords;
	}
    
});	
