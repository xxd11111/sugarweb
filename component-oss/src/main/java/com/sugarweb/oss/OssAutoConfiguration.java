package com.sugarweb.oss;

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
import com.sugarweb.oss.application.FileLinkService;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.controller.FileController;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * oss自动配置
 *
 * @author xxd
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(OssProperties.class)
@MapperScan({"com.sugarweb.oss.domain.mapper"})
@ConditionalOnProperty(prefix = "sugarweb.oss", name = "enable", havingValue = "true", matchIfMissing = true)
public class OssAutoConfiguration {

    @Resource
    private OssProperties ossProperties;

    @Bean
    public AmazonS3 s3client() {

        String endPoint = ossProperties.getEndPoint();
        String accessKey = ossProperties.getAccessKey();
        String secretKey = ossProperties.getSecretKey();

        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endPoint,
                Region.getRegion(Regions.CN_NORTH_1).getName());

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        // http和https指定
        clientConfig.setProtocol(Protocol.HTTP);
        AmazonS3 S3client = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(clientConfig)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .withForceGlobalBucketAccessEnabled(true)
                .build();
        return S3client;
    }

    @Bean
    @ConditionalOnMissingBean
    public FileService fileService(AmazonS3 client) {
        return new FileService(ossProperties.getBucketName(), client);
    }

    @Bean
    @ConditionalOnMissingBean
    public FileLinkService fileLinkService() {
        return new FileLinkService();
    }

    @Bean
    @ConditionalOnMissingBean
    public FileController fileController(FileService fileService) {
        return new FileController(fileService);
    }

}
