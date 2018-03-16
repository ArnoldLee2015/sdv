package com.lee.sdv.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lee.sdv.domain.SdvPatient;

/**
 * 测试json
 */
public class NoSpringTest {
	@Test
	public void testHttp() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("startIndex", "0");
		param.put("extendMap.sdvTemplateIds", "[1,2,3]");
		String result = "";
		// SdvPatient condtion = new SdvPatient();
		// List<Long> ids = new ArrayList<Long>();
		// ids.add(1l);
		// condtion.getExtendMap().put("sdvTemplateIds", ids);
		// result =
		// HttpClientUtil.sendPost("http://39.107.86.242/api/sdvPatientRecord/patient/14", condtion);
		 param = new HashMap<String, String>();
		 param.put("sourceId", "1");
		 param.put("name", "测试模板xxx");
		 param.put("remark", "简介");
		 result =
		 HttpClientUtil.sendPut("http://127.0.0.1/api/sdvTemplate/save",
		 param);
//		 result =
//		 HttpClientUtil.sendGet("http://39.107.86.242/api/sdvPatientRecord/patient/14");
		System.out.println(result);
	}
}
