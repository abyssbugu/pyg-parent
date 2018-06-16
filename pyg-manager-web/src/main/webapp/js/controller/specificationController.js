app.controller("specificationController", function ($scope, $controller, specificationService) {
    $controller("baseController", {$scope: $scope});
    //定义函数
    //查询所有
    $scope.findAll = function () {
        //发送请求
        specificationService.findAll().success(function (data) {
            $scope.list = data;
        })
    };

    //定义方法，传递分页查询参数，实现分页
    $scope.findPage = function (page, rows) {
        //发送请求，实现分页查询
        specificationService.findPage(page, rows).success(function (data) {
            //把总记录数赋值分页控件totalItems
            $scope.paginationConf.totalItems = data.total;
            //分页列表展示数据
            $scope.list = data.rows;
        })
    };


    //添加修改品牌数据
    $scope.add = function () {
        var obj = null;
        //判断是否是修改，还是添加操作
        if ($scope.entity.id != null) {
            //修改
            obj = specificationService.update($scope.entity);
        } else {
            //修改
            obj = specificationService.add($scope.entity);
        }

        obj.success(function (data) {
            //判断是否保存成功
            if (data.success) {
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        });
    };

    //根据id查询品牌数据
    $scope.findOne = function (id) {
        //发送请求
        specificationService.findOne(id).success(function (data) {
            $scope.entity = data;
        })
    };


    //订单删除方法
    $scope.dele = function () {
        //发送删除请求
        specificationService.dele($scope.selectIds).success(function (data) {
            //判断是否保存成功
            if (data.success) {
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    }

    $scope.addTableRow = function () {
        $scope.entity.optionList.push({})
    }
    $scope.deleTableRow=function (index) {
        $scope.entity.optionList.splice(index, 1);
    }

});