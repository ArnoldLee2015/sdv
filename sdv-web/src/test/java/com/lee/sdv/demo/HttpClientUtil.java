package com.lee.sdv.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * Http请求工具类
 */
public class HttpClientUtil {

	private static final CloseableHttpClient httpclient = HttpClients.createDefault();

	/**
	 * 发送HttpGet请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {

		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String result = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送HttpPost请求，参数为map
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> map) {
		StringEntity entity = new StringEntity(JSON.toJSONString(map), Consts.UTF_8);
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("ticket", "4acad53abfcf4ab9af0e07feb01f9e95");
		httppost.setHeader("Content-Type", "application/json; charset=utf8");
		httppost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity1 = response.getEntity();
		String result = null;
		try {
			result = EntityUtils.toString(entity1);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送不带参数的HttpPost请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendPost(String url) {
		HttpPost httppost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}