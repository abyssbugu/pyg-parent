package com.pyg.shop.controller;
import java.util.List;

import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbSeller;
import com.pyg.manager.service.SellerService;

import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {


    @Reference(timeout=100000)
    private SellerService sellerService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSeller> findAll(){
        return sellerService.findAll();
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult  findPage(int page,int rows){
        return sellerService.findPage(page, rows);
    }

    /**
     * 增加
     * @param seller
     * @return
     */
    @RequestMapping("/add")
    public PygResult add(@RequestBody TbSeller seller){
        try {

            //使用spring security中bc
            //动态加密方式
            //明文秘密：admin
            //每次加密后的秘密都不一样。
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPwd = passwordEncoder.encode(seller.getPassword());
            seller.setPassword(newPwd);

            //商家申请入驻，默认状态0
            seller.setStatus("0");

            sellerService.add(seller);
            return new PygResult(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param seller
     * @return
     */
    @RequestMapping("/update")
    public PygResult update(@RequestBody TbSeller seller){
        try {
            sellerService.update(seller);
            return new PygResult(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbSeller findOne(String id){
        return sellerService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public PygResult delete(String [] ids){
        try {
            sellerService.delete(ids);
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
        return sellerService.findPage(seller, page, rows);
    }

}
