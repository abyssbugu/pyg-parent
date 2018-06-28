//服务层
app.service('searchService',function($http){
	    	
	//根据关键词实现搜索
	this.searchList=function(keywords){
		return $http.get('../search/searchList?keywords='+keywords);		
	}
	
});
