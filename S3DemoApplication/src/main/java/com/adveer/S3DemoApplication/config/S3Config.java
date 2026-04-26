package com.adveer.S3DemoApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

	//@Value("${aws.accessKey}") // we need it only for local profile so we moved it to method parameters of local profile
	//private String accessKey;

	//@Value("${aws.secretKey}") // we need it only for local profile so we moved it to method parameters of local profile
	//private String secretKey;

	@Value("${aws.region}")
	private String region;


	@Bean("s3Client")
	@Profile("local")
	public S3Client s3Client(@Value("${aws.accessKey}") String accessKey,
			                 @Value("${aws.secretKey}") String secretKey) {
		AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
		return S3Client.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
				.build();
}
	@Bean("s3Client") //@Bean("s3Client) is same for both the because only one profile can be active at a time and due to this only one of them dependency will be inject.
	@Profile("dev")
	public S3Client s3ClientDev() {
		//AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey); // no need of it because it will run on AWS directly.
		return S3Client.builder()
				.region(Region.of(region))
				.credentialsProvider(DefaultCredentialsProvider.create())
				.build();
		 
	}
	

}
