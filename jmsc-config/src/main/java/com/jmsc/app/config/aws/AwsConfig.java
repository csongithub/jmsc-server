package com.jmsc.app.config.aws;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "aws.properties")
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ToString
@Slf4j
public class AwsConfig {
	
	private String endpointUrl;
	
	private String accessKey;
	
	private String secretKey;
	
	private String bucketName;
	
	private Regions region;
	
	private String prefixUrl;
	
	
	

	@PostConstruct
	private void init() {
		log.debug("Activated: {}",toString());
	}
	
	
	@Bean(name = "AWS_CREDENTIALS_BEAN")
	public AWSCredentials awsCredentials() {
		 AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		 return credentials;
	}
	
	
	
	@Bean(name = "AMAZON_S3_CLIENT")
	public AmazonS3 amazonS3Client() {
		AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
				 									   .withRegion(region)
													   .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
													   .build();
		//Deprecated Method
		//AmazonS3Client  amazonS3Client = new AmazonS3Client(awsCredentials());
		log.debug("Amazon S3 Client: {}", amazonS3Client);
		return amazonS3Client;
	}
}
