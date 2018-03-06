package com.lee.sdv.common.utils;


import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;

/**
 * User: meichao
 * Date: 15-1-15
 * Time: 上午10:03
 */
public class WeixinUtil {

    public static String HTTP_GET = "GET" ;

    private final static Logger log = LogManager.getLogger(WeixinUtil.class);

    /**
     * sha1加密算法
     * @param key 需要加密的字符串
     * @return 加密后的结果
     */
    public static String sha1(String key) {
        try {

            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(key.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();

        } catch (Exception e) {
            log.error("sha1.error" ,e);
        }

        return key ;
    }


    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param data 提交的数据
     */
    public static String httpRequests(String requestUrl, String requestMethod, String data) {

        log.error(requestUrl);
        HttpsURLConnection httpUrlConn = null ;
        InputStream inputStream  = null ;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            System.setProperty("https.protocols", "TLSv1");
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != data) {
                OutputStream outputStream = null ;
                try{
                    outputStream = httpUrlConn.getOutputStream();
                    outputStream.write(data.getBytes("UTF-8"));
                } finally {
                    if(outputStream != null)
                        outputStream.close();
                }
                outputStream.close();
            }

            inputStream = httpUrlConn.getInputStream();
            return IOUtils.toString(inputStream, "UTF-8");
        } catch (ConnectException ce) {
            log.error("httpRequests.url." + requestUrl);
            log.error("Weixin.server.connection.timedOut." ,ce);
        } catch (Exception e) {
            log.error("httpRequests.url." + requestUrl);
            log.error("Weixin.server.https.request.error:{}", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            if(httpUrlConn != null)
                httpUrlConn.disconnect();
        }
        return null;
    }

}
