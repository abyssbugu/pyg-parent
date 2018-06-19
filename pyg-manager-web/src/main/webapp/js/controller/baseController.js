app.controller("baseController", function ($scope) {


    //定义分页控件刷新方法
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };


    //定义数组，封装需要匹配删除id
    $scope.selectIds = [];

    //定义函数
    //updateSelection:组装需要向后端提交的参数
    $scope.updateSelection = function ($event, id) {
        //判断是选中事件，还是取消事件
        if ($event.target.checked) {
            //选中事件
            $scope.selectIds.push(id);
        } else {
            //取消事件
            //获取当前取消元素在数组中角标位置
            var pos = $scope.selectIds.indexOf(id);
            //删除数组中id
            $scope.selectIds.splice(pos, 1);
        }
    };


});
