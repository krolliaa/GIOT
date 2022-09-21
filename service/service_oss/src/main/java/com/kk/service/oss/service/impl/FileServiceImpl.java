package com.kk.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.kk.service.oss.service.FileService;
import com.kk.service.oss.util.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFileName) {
        String endPoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();
        String bucketName = ossProperties.getBucketname();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //1.首先得判断 Bucket 存储空间是否存在，如果不存在需要先创建存储空间并设置访问权限
        boolean exists = ossClient.doesBucketExist(bucketName);
        if (!exists) {
            ossClient.createBucket(bucketName);
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        //2.设置上传文件的文件名，为了防止冲突使用日期作为上传的文件夹，使用 UUID 作为上传的文件名，扩展名使用上传文件的扩展名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String folder = simpleDateFormat.format(new Date());
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String objectName = module + "/" + folder + "/" + fileName + fileExtension;
        //3.参考阿里云提供的 OSS Java SDK 文档将文件上传至阿里云
        ossClient.putObject(bucketName, objectName, inputStream);
        ossClient.shutdown();
        //4. 返回上传到阿里云 OSS 的文件 URL 地址
        return "https://" + bucketName + "." + endPoint + "/" + objectName;
    }

    @Override
    public Boolean deleteFile(String avatarUrl) {
        String endPoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();
        String bucketName = ossProperties.getBucketname();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        String baseUrl = "https://" + bucketName + "." + endPoint + "/";
        String objectName = avatarUrl.substring(baseUrl.length());
        // 判断文件是否存在
        boolean fileExist = ossClient.doesObjectExist(bucketName, objectName);
        if (!fileExist) return false;
        // 文件存在，删除文件
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
        return true;
    }

    @Override
    public Boolean deleteFile(List<String> avatarUrlList) {
        String endPoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();
        String bucketName = ossProperties.getBucketname();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //获取各个文件名 ---> 这里其实可以直接调用上面写好的 deleteFile 但是官方已经提供了批量删除的功能就试着按官方的写吧，当作练习
        List<String> keys = new ArrayList<>();
        String baseUrl = "https://" + bucketName + "." + endPoint + "/";
        for (String avatarUrl : avatarUrlList) keys.add(avatarUrl.substring(baseUrl.length()));
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType("url"));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        if(deletedObjects.size() == keys.size()) return true;
        return false;
    }
}