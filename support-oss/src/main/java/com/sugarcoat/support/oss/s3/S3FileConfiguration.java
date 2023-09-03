package com.sugarcoat.support.oss.s3;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * awsS3客户端配置
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/6
 */
//@Configuration
public class S3FileConfiguration {

	@Value("${sugarcoat.s3.endpoint}")
	private String endPoint;

	@Value("${sugarcoat.s3.accessKey}")
	private String accessKey;

	@Value("${sugarcoat.s3.secretKey}")
	private String secretKey;

	@Bean
	public AmazonS3 s3client() {
		AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endPoint,
				Region.getRegion(Regions.CN_NORTH_1).getName());

		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
		ClientConfiguration clientConfig = new ClientConfiguration();
		// http和https指定
		clientConfig.setProtocol(Protocol.HTTP);
		AmazonS3 S3client = AmazonS3Client.builder().withEndpointConfiguration(endpointConfig)
				.withClientConfiguration(clientConfig).withCredentials(awsCredentialsProvider).disableChunkedEncoding()
				.withPathStyleAccessEnabled(true).withForceGlobalBucketAccessEnabled(true).build();
		return S3client;
	}

}
