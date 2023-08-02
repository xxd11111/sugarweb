package com.sugarcoat.oss.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.sugarcoat.api.exception.ServerException;
import com.sugarcoat.api.oss.FileClient;
import com.sugarcoat.api.exception.FrameworkException;
import com.sugarcoat.api.oss.FileObject;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * awsS3文件客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */
@RequiredArgsConstructor
public class S3FileClient implements FileClient {

    @Value("${sugarcoat.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 client;

    @Override
    public void createBucket() {
        try {
            if (client.doesBucketExistV2(bucketName)) {
                return;
            }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
            client.createBucket(createBucketRequest);
            // client.setBucketPolicy();
        } catch (Exception e) {
            throw new FrameworkException("创建Bucket失败, message:{}", e.getMessage());
        }
    }

    @Override
    public void upload(String path, InputStream inputStream, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new ServerException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    @Override
    public InputStream getContent(String filePath) {
        S3Object object = client.getObject(bucketName, filePath);
        return object.getObjectContent();
    }

    @Override
    public FileObject getFileObject(String filePath) {
        S3Object s3Object = client.getObject(bucketName, filePath);
        FileObject fileObject = new FileObject();
        fileObject.setContentLength(s3Object.getObjectMetadata().getContentLength());
        fileObject.setContentType(s3Object.getObjectMetadata().getContentType());
        fileObject.setContent(s3Object.getObjectContent());
        return fileObject;
    }

    @Override
    public void delete(String filePath) {
        try {
            client.deleteObject(bucketName, filePath);
        } catch (Exception e) {
            throw new ServerException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

}
