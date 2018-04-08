package com.lee.sdv.demo;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * Http请求工具类
 */
public class HttpClientUtil {

	private static final CloseableHttpClient httpclient;
	private static String ticket = "6ba29c975c59488e9930516a5217dd38";
	static {
		// HttpHost proxy = new HttpHost("127.0.0.1", 8888);
		// RequestConfig requestConfig =
		// RequestConfig.custom().setProxy(proxy).build();
		// httpclient =
		// HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		httpclient = HttpClientBuilder.create().build();
	}

	/**
	 * 发送HttpGet请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {

		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("ticket", ticket);
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
	public static String sendPost(String url, Object map) {
		StringEntity entity = new StringEntity(JSON.toJSONString(map), Consts.UTF_8);
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("ticket", ticket);
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
		} catch (Exception e) {
			e.printStackTrace();
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
	public static String sendPut(String url, Object map) {
		StringEntity entity = new StringEntity(JSON.toJSONString(map), Consts.UTF_8);
		HttpPut httpput = new HttpPut(url);
		httpput.setHeader("ticket", ticket);
		httpput.setHeader("Content-Type", "application/json; charset=utf8");
		httpput.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity1 = response.getEntity();
		String result = null;
		try {
			result = EntityUtils.toString(entity1);
		} catch (Exception e) {
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
		httppost.setHeader("ticket", ticket);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}