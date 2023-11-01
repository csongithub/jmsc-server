/**
 * 
 */
package com.jmsc.app.service.aws;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anuhr
 *
 */
@Slf4j
@Service
public class AmazonS3Service {
	
	 @Autowired
	 private AmazonS3 amazonS3;
	 
	 

	public PutObjectResult upload(String path, String fileName,Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
	    optionalMetaData.ifPresent(map -> {
	    	if (!map.isEmpty()) {
	    		map.forEach(objectMetadata::addUserMetadata);
	        }
	    });
	    log.debug("Path: " + path + ", FileName:" + fileName);
	    PutObjectResult  putResult = amazonS3.putObject(path, fileName, inputStream, objectMetadata);
	    return putResult;
	}
	
	
	public S3Object download(String path, String fileName) {
		S3Object s3File = amazonS3.getObject(path, fileName);
		return s3File;
    }
}
