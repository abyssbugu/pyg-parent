//模块化设计
var app = angular.module("pyg", []);
// 定义一个angularjs过滤器，处理html字符串问题，让浏览器能解析html
app.filter("trustHtml", [ "$sce", function($sce) {

	return function(data) {

		return $sce.trustAsHtml(data);

	}

} ])