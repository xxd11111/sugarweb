package com.sugarweb.oss.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.oss.domain.FileInfo;
import com.sugarweb.oss.OssProperties;

import java.io.InputStream;

/**
 * awsS3文件客户端
 *
 * @author xxd
 * @version 1.0
 */
public class S3FileClient implements FileClient {

    private final String bucketName;

    private final AmazonS3 client;

    public S3FileClient(OssProperties ossProperties, AmazonS3 client) {
        this.bucketName = ossProperties.getBucketName();
        this.client = client;
    }

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
    public FileUploadResult upload(String path, InputStream inputStream, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
            long contentLength = putObjectResult.getMetadata().getContentLength();
            FileUploadResult fileUploadResult = new FileUploadResult();
            fileUploadResult.setFileSize(contentLength);
            return fileUploadResult;
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
    public FileInfo getFileObject(String filePath) {
        S3Object s3Object = client.getObject(bucketName, filePath);
        FileInfo fileObject = new FileInfo();
        ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
        fileObject.setFileSize(objectMetadata.getContentLength());
        fileObject.setContentType(objectMetadata.getContentType());
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
