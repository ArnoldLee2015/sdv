package com.lee.sdv.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试json
 */
public class NoSpringTest {
	// @Test
	public void testHttp() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("startIndex", "0");
		String result = HttpClientUtil.sendPost("http://127.0.0.1/api/sdvPatient", param);
		System.out.println(result);
	}
}
