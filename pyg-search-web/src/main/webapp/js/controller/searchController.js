 //控制层 
app.controller('searchController' ,function($scope,$location,searchService){	
	
	//定义搜索方法
	//此方法随着页面加载立马执行，实现索引库搜索
	$scope.searchList = function(){
		//获取门户系统跨系统传递的参数
		//$location.search():是angularjs提供内置方法
		//1,即可以接受跨域参数
		//2,也可以接受本地参数
		//接受参数语法：
		//1,$location.search()["参数名称"]
		//2,$location.search().keywords;
		var keywords = $location.search()["keywords"];
		
		//调用service方法，传递参数实现搜索
		searchService.searchList(keywords).success(function(data){
			$scope.searchResult = data;
		})
	}
    
});	
