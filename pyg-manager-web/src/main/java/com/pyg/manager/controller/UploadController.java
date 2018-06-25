package com.pyg.manager.controller;

import com.pyg.utils.FastDFSClient;
import com.pyg.utils.PygResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Abyss on 2018/6/22.
 * description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${FAST_DFS_URL}")
    private String FAST_DFS_URL;

    /**
     * 需求：上传商品图片
     * 参数：MultipartFile file
     * 返回值：PygResult
     */
    @RequestMapping("/pic")
    public PygResult uploadPic(MultipartFile file) {

        try {
            //获取上传文件名称
            String originalFilename = file.getOriginalFilename();
            //截取文件扩展名
            //a.jgp====>substring(originalFilename.lastIndexOf("."))===>.jpg
            //a.jgp====>substring(originalFilename.lastIndexOf(".")+1)===>jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //创建fastdfs工具类对象，使用工具类上传图片
            FastDFSClient fdfs = new FastDFSClient("classpath:config/client.conf");
            //直接上传,已经把文件上传到fastdfs服务器中
            //返回图片相对地址：group1/M00/00/00/wKhCQ1p7wVCAf8iyAAvea_OGt2M536.jpg
            String url = fdfs.uploadFile(file.getBytes(), extName);

            //组装图片绝对地址，返回前台回调函数
            //1,回显图片
            //2,保存图片地址
            url = FAST_DFS_URL+url;

            //上传成功
            return new PygResult(true, url);


        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            return new PygResult(false, "上传失败");
        }

    }
}
