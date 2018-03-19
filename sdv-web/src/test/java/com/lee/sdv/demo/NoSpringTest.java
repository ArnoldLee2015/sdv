package com.lee.sdv.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.domain.SdvPatientRecord;

/**
 * 测试json
 */
public class NoSpringTest {
	@Test
	public void testHttp() {
		String result = "";
		// SdvPatient condtion = new SdvPatient();
		// List<Long> ids = new ArrayList<Long>();
		// ids.add(1l);
		// condtion.getExtendMap().put("sdvTemplateIds", ids);
		// result =
		// HttpClientUtil.sendPost("http://39.107.86.242/api/sdvPatientRecord/patient/14",
		// condtion);
//		List<SdvPatientRecord> param = new ArrayList<SdvPatientRecord>();
//		SdvPatientRecord r1 = new SdvPatientRecord();
//		r1.setId(16l);
//		r1.setSdvPatientId(14l);
//		r1.setSdvTemplateId(1l);
//		r1.setVisitId(1l);
//		r1.setDataId(1l);
//		r1.setRemark("ces111");
//		List<PatientRecordFile> files = new ArrayList<PatientRecordFile>();
//		PatientRecordFile file = new PatientRecordFile();
//		file.setFileName("sadasdas");
//		file.setFileUrl("dfsdfsdf");
//		files.add(file);
//		r1.setFiles(files);
//		param.add(r1);
//		result = HttpClientUtil.sendPut(
//				"http://127.0.0.1//api/sdvPatientRecord/save", param);
		 result =
		 HttpClientUtil.sendGet("http://127.0.0.1//api/sdvTemplate/1");
		System.out.println(result);
	}
}
