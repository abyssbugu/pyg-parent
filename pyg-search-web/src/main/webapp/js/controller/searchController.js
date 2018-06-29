//控制层
app.controller('searchController', function ($scope, $location, searchService) {
    // 定义封装搜索参数对象，封装所有参与搜索参数
    // 1,主查询条件 （关键词搜索条件）
    // 2,分类查询参数
    // 3，品牌参数
    // 4,规格属性参数
    // 5,价格参数
    // 6,排序
    // 7,分页
    $scope.searchMap = {
        "keywords": "",
        "category": "",
        "brand": "",
        "spec": {},
        "price": "",
        "sort": "ASC",
        "sortField": "price",
        "page": 1,
        "pageSize": 30
    };
    // 定义方法，获取门户系统参数，实现搜索
    $scope.loadSearchList = function () {
        // 获取门户系统跨系统传递的参数
        // $location.search():是angularjs提供内置方法
        // 1,即可以接受跨域参数
        // 2,也可以接受本地参数
        // 接受参数语法：
        // 1,$location.search()["参数名称"]
        // 2,$location.search().keywords;
        var keywords = $location.search()["keywords"];

        // 把关键词参数放入参数对象
        $scope.searchMap["keywords"] = keywords;
        // 调用搜索方法
        $scope.searchList();
    }


    // 定义搜索方法
    // 此方法随着页面加载立马执行，实现索引库搜索
    $scope.searchList = function () {
        // 调用service方法，传递参数实现搜索
        searchService.searchList($scope.searchMap).success(function (data) {
            $scope.searchResult = data;
            // 处理分页角标
            // 调用方法，封装分页角标
            bulidPageLable();
        })
    };

    // 定义条件过滤搜索参数封装方法
    $scope.addFilterCondition = function (key, value) {
        // 判断传递是否是规格属性参数
        if (key == "category" || key == "brand" || key == "price") {
            // 分类，品牌，价格参数封装
            $scope.searchMap[key] = value;
        } else {
            // 否则规格属性参数封装
            $scope.searchMap.spec[key] = value;
        }

        // 调用搜索方法
        $scope.searchList();
    };

    // 删除面包屑导航值，重新搜索方法
    $scope.removeSearchItem = function (key) {
        // 判断删除是否是规格属性的值
        // 分类，品牌，价格数据结构是一样的
        if (key == "category" || key == "brand" || key == "price") {
            // searchMap中值置空
            $scope.searchMap[key] = "";
        } else {
            // 规格属性数据结构对象
            delete $scope.searchMap.spec[key];
        }

        // 重新调用搜索方法
        $scope.searchList();

    };

    // 排序函数
    $scope.addSort = function (sort, field) {
        // 封装参数
        // 排序方式
        // asc desc
        $scope.searchMap["sort"] = sort;
        // 排序字段
        // item_price
        // item_updatetime
        $scope.searchMap["sortField"] = field;

        // 调用搜索方法
        $scope.searchList();

    };

    // 分页查询函数
    $scope.queryForPage = function (page) {
        // 判断当前页码是否为负数或者是0，或者是大于总页码数
        if (page <= 0 || page > $scope.searchResult.totalPages) {
            return;
        }

        // 封装分页参数
        $scope.searchMap["page"] = parseInt(page);

        // 调用搜索方法
        $scope.searchList();

    };

    // 定义方法，封装分页角标
    bulidPageLable = function () {

        // 定义数组，封装页码
        $scope.pageLable = [];

        // 获取总页码数
        var maxPage = $scope.searchResult.totalPages;

        // 显示的起始页
        var fisrtPage = 1;
        // 显示结束页
        var lastPage = maxPage;

        //初始化变量，用于判断分页显示的省略号
        $scope.ispreDot = true;
        $scope.isposDot = true;

        // 判断显示页码，进行封装
        if ($scope.searchResult.totalPages > 5) {
            // 判断当前页是否是小于5页，是否是前5页
            if ($scope.searchMap.page <= 5) {
                lastPage = 5;
                //前面没有省略号
                $scope.ispreDot = false;
            } else if ($scope.searchMap.page >= maxPage - 2) {
                // 判断当前页码是否是后面5页
                fisrtPage = maxPage - 4;
                //后面没有省略号
                $scope.isposDot = false;
            } else {
                // 判断当前页码是否是中间页码
                fisrtPage = $scope.searchMap.page - 2;
                lastPage = $scope.searchMap.page + 2;

            }

        }

        // 封装页码
        for (var i = fisrtPage; i <= lastPage; i++) {

            $scope.pageLable.push(i);
        }

    };

    //定义方法，判断 上一页，下一页 样式是否可用
    $scope.isTop = function () {
        //获取当前页码
        if ($scope.searchMap.page == 1) {
            return true;
        }
        return false;
    }

    $scope.isLast = function () {
        //获取当前页码
        if ($scope.searchMap.page == $scope.searchResult.totalPages) {
            return true;
        }
        return false;
    }

});
