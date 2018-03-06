package com.lee.sdv.service.cos.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class COSClientConfig {

	@Value("${cos.appId}")
	private long appId;
	@Value("${cos.secretId}")
	private String secretId;
	@Value("${cos.secretKey}")
	private String secretKey;
	@Value("${cos.region}")
	private String region;
	@Value("${cos.bucketName}")
	private String bucketName;
	@Value("${cos.fileUrl}")
	private String fileUrl;

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
