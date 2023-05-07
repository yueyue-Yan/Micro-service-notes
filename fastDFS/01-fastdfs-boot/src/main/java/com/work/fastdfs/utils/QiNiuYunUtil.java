package com.work.fastdfs.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * ClassName:QiNiuYunUtil
 * Package:com.work.fastdfs.utils
 * Description: 描述信息
 *
 * @date:2023/5/4 15:45
 * @author:yueyue
 */
@Component
public class QiNiuYunUtil {

    public static String uploadFile() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "bEEecQA37hpB44kcMDVA_1kTL71WPAaFZLx9D1Ly";
        String secretKey = "mFdwDfslDcWdnSRdJ6o0njp55zM6rR7lSWl_Xgup";
        String bucket = "yueyue-storage-01";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud,I am yueyue!".getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes,key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return putRet.hash;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }
        return null;
    }

    public static String uploadFileByte(byte[] bytes) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "bEEecQA37hpB44kcMDVA_1kTL71WPAaFZLx9D1Ly";
        String secretKey = "mFdwDfslDcWdnSRdJ6o0njp55zM6rR7lSWl_Xgup";
        String bucket = "yueyue-storage-01";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
                            Response response = uploadManager.put(bytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return putRet.hash;
        } catch (Exception ex) {
        }
        return null;
    }
}
