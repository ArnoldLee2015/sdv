package com.lee.sdv.service.cos;

import java.io.InputStream;
import java.util.Date;

import com.lee.sdv.service.cos.domain.COSResult;

public interface COSClientService {
	public COSResult uploadLocalFile(String bucketName, String cosPath, String localPath);

	public COSResult uploadFile(String bucketName, String cosPath, byte[] contentBuffer);

	public COSResult deleteFile(String bucketName, String cosPath);

	public COSResult getFileLocal(String bucketName, String cosPath, String localPath);

	public InputStream getFileInputStream(String bucketName, String cosPath) throws Exception;

	public String sign(String cosPath, Date expireTime);
}
