package com.work.fastdfs.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * ClassName:FastDFSConfig
 * Package:com.work.fastdfs.utils
 * Description: 与远程建立连接，进行文件的上传下载删除等操作
 *
 * @date:2023/5/4 10:39
 * @author:yueyue
 */
@Component
public class FastDFSUtil {
    public static void main(String[] args) throws IOException, MyException {


        //上传文件（从本地到远端）
        String url = localFileUpload("C:\\Users\\yanyu\\Desktop\\bb.jpg");
        System.out.println(url);             //http://192.168.64.128/group1/M00/00/00/wKhAgGRTLsqAEg28AAPthACz3X4944.jpg

        //下载文件（从远端到本地）
//        localFileDownload("group1","M00/00/00/wKhAgGRTLsqAEg28AAPthACz3X4944.jpg","C:\\Users\\yanyu\\Desktop\\cc");
//        localFileDownload("group1","M00/00/00/wKhAgGRTLsqAEg28AAPthACz3X4944.jpg");

        //删除文件(删除远程文件) 文件在Linux下的/opt/fastDFS/storage/files/data/00/00文件夹中
//        deleteFile("group1","M00/00/00/wKhAgGRTLsqAEg28AAPthACz3X4944.jpg");


    }



    //本地文件上传
    public static String localFileUpload(String localFileName) throws IOException, MyException {
        //获取后缀名
        String suffix = localFileName.substring(localFileName.lastIndexOf(".") + 1);
        //获取StorageClient对象
        StorageClient storageClient = getStorageClient();
        //上传文件  第一个参数：本地文件路径 第二个参数：上传文件的后缀 第三个参数：文件信息
        //上传成功后，会将群组名称和远程文件名称返回
        String[] uploadArray = storageClient.upload_file(localFileName, suffix, null);

        if(uploadArray == null || uploadArray.length == 0){
            //上传失败
            throw new MyException("上传失败...");
        }

        return "http://192.168.64.128/"+uploadArray[0]+"/"+uploadArray[1];
    }


    //流方式文件上传
    public static String bytesFileUpload(byte[] bytes,String suffix) throws IOException, MyException {
        //获取StorageClient对象
        StorageClient storageClient = getStorageClient();

        String[] uploadArray = storageClient.upload_file(bytes, suffix, null);

        if(uploadArray == null || uploadArray.length == 0){
            //上传失败
            throw new MyException("上传失败...");
        }

        return "http://192.168.64.128/"+uploadArray[0]+"/"+uploadArray[1];

    }

    //下载文件
    public static void localFileDownload(String groupName,String remoteFileName,String localFileName) throws IOException, MyException {

        StorageClient storageClient = getStorageClient();
        String suffix = remoteFileName.substring(remoteFileName.lastIndexOf("."));

        //参数1：群组名称
        //参数2：远程文件名称
        //参数3：本地下载绝对路径，C:\Users\Administrator\Desktop\aaa.jpg，当前路径+文件名称，必须要有文件名称

        //可以指定具体路径的下载方式
        storageClient.download_file(groupName,remoteFileName,localFileName+suffix);

    }
    //下载文件
    public static void localFileDownload(String groupName,String remoteFileName) throws IOException, MyException {

        StorageClient storageClient = getStorageClient();
        String suffix = remoteFileName.substring(remoteFileName.lastIndexOf("."));
        //参数1：群组名称
        //参数2：远程文件名称
        //参数3：本地下载绝对路径，C:\Users\Administrator\Desktop\aaa.jpg，当前路径+文件名称，必须要有文件名称
        String newFileName = UUID.randomUUID().toString()+suffix;
        //指定固定位置的下载方式
        storageClient.download_file(groupName,remoteFileName,"C:\\Users\\yanyu\\Desktop\\"+newFileName);

    }
    //下载文件
    public static byte[] bytesFileDownload(String groupName,String remoteFileName) throws IOException, MyException {

        StorageClient storageClient = getStorageClient();
        String suffix = remoteFileName.substring(remoteFileName.lastIndexOf("."));
        //参数1：群组名称
        //参数2：远程文件名称
        return storageClient.download_file(groupName,remoteFileName);

    }


    //删除文件
    public static void deleteFile(String groupName,String remoteFileName) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();

        storageClient.delete_file(groupName,remoteFileName);

    }



    //获取StorageClient对象，这个对象完成对文件的操作
    private static StorageClient getStorageClient(){
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            //1.加载配置文件，默认去classpath下加载
            ClientGlobal.init("fdfs_client.conf");
            //2.创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //3.创建TrackerServer对象
            trackerServer = trackerClient.getConnection();
            //4.创建StorageServler对象
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5.创建StorageClient对象，这个对象完成对文件的操作
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            if(storageClient == null) {return null;}
            return storageClient;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}


