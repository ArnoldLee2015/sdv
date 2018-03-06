package com.lee.sdv.service.cos.impl;

import java.io.InputStream;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lee.sdv.service.cos.COSClientService;
import com.lee.sdv.service.cos.domain.COSClientConfig;
import com.lee.sdv.service.cos.domain.COSResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.exception.AbstractCosException;
import com.qcloud.cos.meta.InsertOnly;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.GetFileInputStreamRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import com.qcloud.cos.sign.Sign;

@Service
public class COSClientServiceImpl implements COSClientService {
	protected static final Logger LOG = LoggerFactory.getLogger(COSClientServiceImpl.class);
	@Autowired
	private COSClientConfig cosClientConfig;
	private COSClient cosClient;
	private Credentials cred;

	@PostConstruct
	public void init() {
		// 初始化客户端配置
		ClientConfig clientConfig = new ClientConfig();
		// 设置bucket所在的区域，比如广州(gz), 天津(tj)
		clientConfig.setRegion(cosClientConfig.getRegion());
		// 初始化秘钥信息
		cred = new Credentials(cosClientConfig.getAppId(), cosClientConfig.getSecretId(), cosClientConfig.getSecretKey());
		// 初始化cosClient
		cosClient = new COSClient(clientConfig, cred);
		LOG.info("-------------------cos init--------------");
	}

	@PreDestroy
	public void dostory() {
		cosClient.shutdown();
	}

	@Override
	public COSResult uploadLocalFile(String bucketName, String cosPath, String localPath) {
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosPath, localPath);
		uploadFileRequest.setEnableShaDigest(false);
		String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
		return JSON.parseObject(uploadFileRet, COSResult.class);
	}

	@Override
	public COSResult uploadFile(String bucketName, String cosPath, byte[] contentBuffer) {
		UploadFileRequest overWriteFileRequest = new UploadFileRequest(bucketName, cosPath, contentBuffer);
		// 如果COS上已有文件, 则进行覆盖(默认不覆盖)
		overWriteFileRequest.setInsertOnly(InsertOnly.OVER_WRITE);
		String overWriteFileRet = cosClient.uploadFile(overWriteFileRequest);
		return JSON.parseObject(overWriteFileRet, COSResult.class);
	}

	@Override
	public COSResult deleteFile(String bucketName, String cosPath) {
		DelFileRequest delFileRequest = new DelFileRequest(bucketName, cosPath);
		String delFileRet = cosClient.delFile(delFileRequest);
		return JSON.parseObject(delFileRet, COSResult.class);
	}

	@Override
	public COSResult getFileLocal(String bucketName, String cosPath, String localPath) {
		GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucketName, cosPath, localPath);
		getFileLocalRequest.setUseCDN(false);
		// getFileLocalRequest.setReferer("*.myweb.cn");
		String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
		return JSON.parseObject(getFileResult, COSResult.class);
	}

	@Override
	public InputStream getFileInputStream(String bucketName, String cosPath) throws Exception {
		GetFileInputStreamRequest getFileInputStreamRequest = new GetFileInputStreamRequest(bucketName, cosPath);
		return cosClient.getFileInputStream(getFileInputStreamRequest);
	}

	@Override
	public String sign(String cosPath, Date expireTime) {
		try {
			return Sign.getPeriodEffectiveSign(cosClientConfig.getBucketName(), cosPath, cred, expireTime.getTime());
		} catch (AbstractCosException e) {
			LOG.error("sign error :{}", e.getMessage(), e);
		}
		return null;
	}

}
