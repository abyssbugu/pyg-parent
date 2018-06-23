 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,itemCatService,typeTemplateService,uploadService){
	
	$controller('baseController',{$scope:$scope});//继承



    $scope.status=['未审核','已审核','审核未通过','关闭'];//商品状态

    //查询所有商品分类
    //1，定义数组
    //2,查询出所有分类封装到数组
    //3,把分类id作为数组的角标。分类名称值此此角标对应值。
    $scope.catList = [];
    //查询所有
    $scope.findAllItemCatList = function(){
        itemCatService.findAll().success(function(data){
            //循环回调函数集合
            for(var i=0;i<data.length;i++){
                //catList = ["","图书、音像、电子书刊","电子书刊","电子书"....]
                //获取数组值：catList[1]--->catList[entity.category1Id]
                //catList[0]="张三"
                $scope.catList[data[i].id]=data[i].name;
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


    // 实现扩展属性数据封装，封装商品描述对象customAttributeItems字段
    // goods包装类对象属性一一对应
    // 参数接受格式：通过后台接受参数pojo反向推导。
    $scope.entity = {
        goods : {},
        goodsDesc : {
            itemImages : [],
            customAttributeItems : [],
            specificationItems : []
        }
    };

    //监控模板id变化，根据模板id查询模板对象
    $scope.$watch('entity.goods.typeTemplateId',function(newValue,oldValue){
        //调用服务方法，根据新的分类id查询下级菜单
        typeTemplateService.findOne(newValue).success(function (data) {
            //获取模板id
            $scope.typeTemplate = data;
            //获取模板中品牌数据
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);

            // 获取模板扩展属性数据值赋值货品描述表扩展属性字段
            $scope.entity.goodsDesc.customAttributeItems = JSON
                .parse($scope.typeTemplate.customAttributeItems);
        });

        // 调用service方法，查询规格选项值
        typeTemplateService.findSpecList(newValue).success(function(data) {
            $scope.specList = data;
        });
    });


    // 定义文件上传方法
    $scope.uploadFile = function() {
        // 调用上传文件服务层方法，实现文件上传
        uploadService.uploadFile().success(function(data) {
            // 判断上传文件是否成功
            if (data.success) {
                $scope.image_entity.url = data.message;
            } else {
                alert(data.message);
            }
        })
    };

    // 参数结构
    // entity = {goods:{},goodsDesc:{itemImage:[{},{}]},itemList:[]}

    // 实现图片保存

    // 保存图片地址方法，把图片地址封装到$scope
    $scope.add_image_entity = function() {
        // 把图片地址推送到itemImages
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    // 删除上传的图片
    $scope.dele_image_entity = function(index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };

// 定义方法
    searchSpecAttribute = function(list, key, name) {
        // 循环规格选项集合
        // specificationItems:[{"attributeName":"网络","attributeValue":["电信2G","联通2G"]},{}]
        for (var i = 0; i < list.length; i++) {
            if (list[i][key] == name) {
                return list[i];
            }
        }

        return null;

    }
    // 创建方法，构造规格选项参数
    // 规格选项参数需要保存到：goodsDesc:{specificationItems:[{"attributeName":"网络","attributeValue":["电信2G","联通2G"]},
    // {"attributeName":"机身内存","attributeValue":["32G","16G","64G"]}]}
    // 参数1：选择事件对象
    // 参数2：规格值 --> 网络，机身内存
    // 参数3：规格选项值 ： 电信2G 16G
    $scope.updateSpecAttribute = function($event, name, value) {
        // 判断选择的规格选项属于哪个规格中的值
        var obj = searchSpecAttribute(
            $scope.entity.goodsDesc.specificationItems, "attributeName",
            name);

        // 判断是否为null
        if (obj != null) {
            //判断是否是选中事件，还是取消事件
            if($event.target.checked){
                obj.attributeValue.push(value);
            }else{
                //取消事件
                //删除attributeValue中规格选项
                obj.attributeValue.splice(obj.attributeValue.indexOf(value),1);

                //判断规格选项是否删除完了
                if(obj.attributeValue.length==0){
                    //移除规格
                    $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(obj),1);
                }

            }

        } else {
            // $scope.entity.goodsDesc.specificationItems第一次选择，没有值，，初始化
            // $scope.entity.goodsDesc.specificationItems=[{"attributeName":”网络“,"attributeValue":[“电信2G”]}]
            $scope.entity.goodsDesc.specificationItems.push({
                "attributeName" : name,
                "attributeValue" : [ value ]
            });

        }

    };


    //构造sku保存商品数据
    //1,每一行有哪些字段数据？
    //2,根据规格选项组合参数进行循环组合成sku行
    //[{"attributeName":"网络","attributeValue":["电信2G","联通2G","移动2G"]},{"attributeName":"机身内存","attributeValue":["16G","32G"]}]
    $scope.createSKUTable = function(){
        //需要保存到后端sku数据格式初始化构造
        $scope.itemList = [{spec:{},price:0,num:999999,status:0,isDefault:0}];
        //获取组装规格选项集合对象
        //[{"attributeName":"网络","attributeValue":["电信2G","联通2G","移动2G"]},{"attributeName":"机身内存","attributeValue":["16G","32G"]}]
        var optionList = $scope.entity.goodsDesc.specificationItems;

        //循环调用
        for(var i=0;i<optionList.length;i++){
            //第一次调用：
            //参数1：$scope.itemList = [{spec:{},price:0,num:999999,status:0,isDefault:0}]
            //参数2：网络
            //参数3：["电信2G","联通2G","移动2G"]

            //第二次循环：
            //参数1：$scope.itemList = [{spec:{"网络","电信2G"},price:0,num:999999,status:0,isDefault:0},
            //{spec:{"网络","联通2G"},price:0,num:999999,status:0,isDefault:0},
            //{spec:{"网络","移动2G"},price:0,num:999999,status:0,isDefault:0}]
            //参数2：机身内存
            //参数3：["16G","32G"]

            $scope.itemList =
                createRow($scope.itemList,
                    optionList[i].attributeName,
                    optionList[i].attributeValue);
        };




    };

    //创建sku行
    //规格选项不同组合构造成不同sku行
    //参数1：[{spec:{},price:0,num:999999,status:0,isDefault:0}]
    //参数2：规格值
    //参数3：["电信2G","联通2G","移动2G"]
    createRow = function(list,name,values){
        //第一次调用：
        //参数1：$scope.itemList = [{spec:{},price:0,num:999999,status:0,isDefault:0}]
        //参数2：网络
        //参数3：["电信2G","联通2G","移动2G"]

        //第二次循环：
        //第二次循环：
        //参数1：$scope.itemList = [{spec:{"网络","电信2G"},price:0,num:999999,status:0,isDefault:0},
        //{spec:{"网络","联通2G"},price:0,num:999999,status:0,isDefault:0},
        //{spec:{"网络","移动2G"},price:0,num:999999,status:0,isDefault:0}]
        //参数2：机身内存
        //参数3：["16G","32G"]


        var newList = [];
        //循环list集合
        for(var i=0;i<list.length;i++){
            //获取行数据
            var oldRow = list[i];
            //循环规格选项值
            for(var j=0;j<values.length;j++){
                //新建一行
                //{spec:{},price:0,num:999999,status:0,isDefault:0}
                var newRow = JSON.parse(JSON.stringify(oldRow));
                //向新行中添加数据
                //{spec:{"网络"："2G"}}
                newRow.spec[name]=values[j];

                //把新的行放入newList集合
                newList.push(newRow);

            }

        }


        //第一次循环结束：
        //newList=[{spec:{"网络","电信2G"},price:0,num:999999,status:0,isDefault:0},
        //{spec:{"网络","联通2G"},price:0,num:999999,status:0,isDefault:0},
        //{spec:{"网络","移动2G"},price:0,num:999999,status:0,isDefault:0}]
        return newList;


    }

});	
