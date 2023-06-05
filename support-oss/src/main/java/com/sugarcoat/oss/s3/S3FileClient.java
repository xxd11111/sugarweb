package com.sugarcoat.oss.s3;

import cn.hutool.core.io.IoUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.sugarcoat.oss.api.FileClient;
import com.sugarcoat.oss.api.UploadResult;
import com.sugarcoat.protocol.exception.FrameworkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */

public class S3FileClient implements FileClient {

    @Value("${biz.s3.service.end.point}")
    public String endPoint;

    @Value("${biz.s3.accessKey}")
    public String accessKey;

    @Value("${biz.s3.secretKey}")
    public String secretKey;

    String bucketName;

    AmazonS3 client;

    @Bean
    public AmazonS3 s3client() {
        AwsClientBuilder.EndpointConfiguration endpointConfig =
                new AwsClientBuilder.EndpointConfiguration(endPoint,  Region.getRegion(Regions.CN_NORTH_1).getName());

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        //http和https指定
        clientConfig.setProtocol(Protocol.HTTP);

        AmazonS3 S3client = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(clientConfig)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .withForceGlobalBucketAccessEnabled(true)
                .build();
        return  S3client;
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
            //client.setBucketPolicy();
        } catch (Exception e) {
            throw new FrameworkException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
        }
    }

    @Override
    public UploadResult upload(String path, InputStream inputStream, String contentType) {
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
        return UploadResult.builder().fileUrl("/" + path).build();
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
