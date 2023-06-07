package com.sugarcoat.oss.s3;

import cn.hutool.core.io.IoUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.sugarcoat.oss.api.FileClient;
import com.sugarcoat.protocol.exception.FrameworkException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
            //client.setBucketPolicy();
        } catch (Exception e) {
            throw new FrameworkException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
        }
    }

    @Override
    public void upload(String path, InputStream inputStream, String contentType) {
        if (!(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
        }
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(inputStream.available());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream, metadata);
            // 设置上传对象的 Acl 为公共读
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new FrameworkException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    @Override
    public InputStream getContent(String fileUrl) {
        S3Object object = client.getObject(bucketName, fileUrl);
        return object.getObjectContent();
    }

    @Override
    public void delete(String fileUrl) {
        try {
            client.deleteObject(bucketName, fileUrl);
        } catch (Exception e) {
            throw new FrameworkException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }
}
