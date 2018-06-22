package com.pyg.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by Abyss on 2018/6/22.
 * description:
 */
public class TestImgUpload {

    /**
     * 需求：使用fastDFS提供java客户额实现文件上传
     * @throws Exception
     */
    @Test
    public void uploadPicByFastDFSClient() throws Exception {
        //指定需要上传图片绝对地址
        String picPath = "/Users/abyss/Pics/tree.png";
        //指定上传连接服务器地址
        String clientPath = "/Users/abyss/Dev/Demos/pyg-parent/pyg-shop-web/src/main/resources/config/client.conf";
        //加载客户端配置文件，连接图片服务器
        ClientGlobal.init(clientPath);

        //创建tracker客户端对象
        TrackerClient tc = new TrackerClient();
        //从tracker客户端对象获取tracker_server服务对象
        TrackerServer trackerServer = tc.getConnection();

        StorageServer storageServer=null;
        //创建storageClient客户端对象
        StorageClient sc = new StorageClient(trackerServer, storageServer);


        //使用存储客户端对象上传
        String[] str = sc.upload_file(picPath, "png", null);
        for (String url : str) {
            System.out.println(url);

        }

//        group1
//        M00/00/04/wKhCQ1ssxyOAGzJLAAFHBF_C5-M223.png

    }

}
