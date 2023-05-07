package com.work.fastdfs.controller;

import com.work.fastdfs.utils.FastDFSUtil;
import com.work.fastdfs.utils.QiNiuYunUtil;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:UPloadController
 * Package:com.work.fastdfs.controller
 * Description: 描述信息
 *
 * @date:2023/5/4 12:57
 * @author:yueyue
 */
@Controller
public class UPloadController {

    @Autowired
    FastDFSUtil fastDFSUtil;

    @Autowired
    QiNiuYunUtil qiNiuYunUtil;

    @RequestMapping("/")
    public String indexView(){
        return "index";
    }

    /**上传文件*/
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile uploadFile) throws IOException, MyException {
        //获取上传文件的字节数组
        byte[] bytes = uploadFile.getBytes();

        //获取文件的名称及后缀名称
        String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1);

        String url = fastDFSUtil.bytesFileUpload(bytes,suffix);
        Map<String,Object> resultMap = new HashMap<>();
        //上传失败
        if(url == null){
            resultMap.put("code",1);
            resultMap.put("message","上传失败！");
            return resultMap;
        }
       //上传成功
        resultMap.put("code",0);
        resultMap.put("message","上传成功！");
        resultMap.put("data",url);
        return resultMap;
    }

    /**下载文件*/
    //当前如果通过get方式进行传递接收参数，尽量不要使用RestFul风格的api操作
    @RequestMapping("/downloadFile2Local")
    @ResponseBody
    //@RequestParam(required = false)  String localFileName：表明 localFileName参数不是必须的
    public Map<String,Object> downloadFile2Local(String groupName,String remoteFileName,@RequestParam(required = false) String localFileName) throws IOException, MyException {

        Map<String,Object> resultMap = new HashMap<>();

        if(localFileName != null){
            fastDFSUtil.localFileDownload(groupName,remoteFileName,localFileName);
            resultMap.put("code",0);
            resultMap.put("msg","下载成功...");
            return resultMap;
        }
        //没有传递本地的路径通过本地下载
        fastDFSUtil.localFileDownload(groupName,remoteFileName);
        //上传成功
        resultMap.put("code",0);
        resultMap.put("msg","下载成功...");
        return resultMap;
    }

    @RequestMapping("/downloadFile2Client")
    public void downloadFile2Client(String groupName,
                                    String remoteFileName,
                                    @RequestParam(required = false) String localFileName,
                                    HttpServletResponse response) throws IOException, MyException {

        if(localFileName != null){
            fastDFSUtil.localFileDownload(groupName,remoteFileName,localFileName);
        }

        //设置响应头相关的内容
        //浏览器识别后，会打开下载框完成下载操作
//        response.setContentType();
        //设置下载完成后的文件名称
        response.setHeader("Content-Disposition","attachment;filename="+remoteFileName);

        //通过本地下载
        byte[] bytes = fastDFSUtil.bytesFileDownload(groupName, remoteFileName);

        //将字节文件响应回浏览器
        response.getOutputStream().write(bytes);

    }


    /**删除文件*/
    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map<String,Object> deleteFile(String groupName,String remoteFileName) throws IOException, MyException {
        fastDFSUtil.deleteFile(groupName,remoteFileName);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("message","删除成功！");
        return resultMap;
    }


    /**七牛云文件上传*/
    @RequestMapping("/qiniuUpload1")
    @ResponseBody
    public Map<String,Object> qiniuUpload(){

        String fileName = qiNiuYunUtil.uploadFile();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("message","七牛云上传文件成功！");
        resultMap.put("data","http://ru4iukum2.hn-bkt.clouddn.com/"+fileName);
        return resultMap;
    }

    @RequestMapping("/qiniuUpload2")
    @ResponseBody
    public Map<String,Object> qiniuUpload(MultipartFile uploadFile) throws IOException {

        String fileName = qiNiuYunUtil.uploadFileByte(uploadFile.getBytes());

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","上传成功啦！");
        resultMap.put("data","http://ru4iukum2.hn-bkt.clouddn.com/"+fileName);
        return resultMap;
    }


}
