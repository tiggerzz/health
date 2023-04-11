package com.tigger.health.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.*;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@Component
public class QiniuUtils {

    //配置
    private static String accessKey = "E8YGmreOdIX5wccnzUtIaJ5Eq2Kzfvjhu74y8ysH";
    private static String secretKey = "yzwJFZFbIVbZQhD2FEBFDSP1hsB48po5HJXCpomP";
    private static String bucket = "wyu-health";
    //根据七牛云储存空间位置设置对应Region
    private static Region region = Region.huanan();
    //访问资源的外链域名
    private static String domain = "rrvfy8lfw.hn-bkt.clouddn.com";
    //是否启用https访问
    private static boolean useHttps = false;

    /*
     * 封装七牛云上传文件功能，上传成功返回对应的文件名称
     * */
    public static String upload(MultipartFile file) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(region);
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证upToken，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //原始名
        String usedName = file.getOriginalFilename();
        //根据原始名截取文件后缀
        String suffix = usedName.substring(usedName.lastIndexOf("."));
        //使用UUID生成32位随机名
        String key = UUID.randomUUID().toString() + suffix;
        String imgName = null;
        try {
            //进行文件上传
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果，官方给出的示例用Gson解析了返回的response上传结果，本项目未引入Gson，修改为fastjson解析
            //DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            imgName = putRet.key.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imgName;
    }

    /*
     * 封装七牛云下载文件功能，返回一个可访问的url
     * */
    public static String download(String imgName) throws QiniuException {
        // imgName：下载资源在七牛云存储的名字
        DownloadUrl url = new DownloadUrl(domain, useHttps, imgName);
        //生成凭证
        Auth auth = Auth.create(accessKey, secretKey);
        //设置过期时间
        long l = System.currentTimeMillis();
        //1小时，可以自定义链接过期时间
        long expireInSeconds = 3600;
        long deadline = System.currentTimeMillis() + expireInSeconds;
        //生成url
        String urlString = url.buildURL(auth, deadline);
        return urlString;
    }

    public static Integer delete(String keys) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String key = keys;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            return 20000;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return 0;
        }
    }
}