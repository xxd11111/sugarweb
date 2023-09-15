package com.sugarcoat.support.oss;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * oss自动配置
 *
 * @author xxd
 * @since 2023/8/10
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.oss", name = "enable", havingValue = "true")
public class OssAutoConfiguration {

    @Autowired
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

}
