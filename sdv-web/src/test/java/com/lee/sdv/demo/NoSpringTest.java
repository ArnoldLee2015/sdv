package com.lee.sdv.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.lee.sdv.web.controller.dto.ExportDateRecordDto;

/**
 * 测试json
 */
public class NoSpringTest {
	@Test
	public void testHttp() {
		String result = "";
//		ExportDateRecordDto dto = new ExportDateRecordDto();
//		dto.setEmail("arnold_lee@foxmail.com");
//		dto.setStartDate(new Date("2018/03/19"));
//		dto.setEndDate(new Date());
//		List<Long> ids = new ArrayList<Long>();
//		ids.add(1l);
//		dto.setPatientIds(ids);
//		result = HttpClientUtil.sendPost("http://127.0.0.1/api/sdvPatientRecord/export", dto);
		// List<SdvPatientRecord> param = new ArrayList<SdvPatientRecord>();
		// SdvPatientRecord r1 = new SdvPatientRecord();
		// r1.setId(16l);
		// r1.setSdvPatientId(14l);
		// r1.setSdvTemplateId(1l);
		// r1.setVisitId(1l);
		// r1.setDataId(1l);
		// r1.setRemark("ces111");
		// List<PatientRecordFile> files = new ArrayList<PatientRecordFile>();
		// PatientRecordFile file = new PatientRecordFile();
		// file.setFileName("sadasdas");
		// file.setFileUrl("dfsdfsdf");
		// files.add(file);
		// r1.setFiles(files);
		// param.add(r1);
		// SdvTemplate sdvTemplate = new SdvTemplate();
		// sdvTemplate.setName("治疗转移性肾细胞癌II期临床试验");
		// sdvTemplate.setRemark("尽管目前已有多种VEGF、VEGFR以及mTOR抑制剂获得批准用于转移性肾细胞癌（mRCC）的治疗，但患者的治疗获益已经达到了一个平台期。随着免疫检查点阻断药物mRCC治疗中再度引起关注，新的发展指日可待。抗PD-1通路的单克隆抗体的发展，无论单药或联合，都可能代表着未来的临床突破。");
		// sdvTemplate.setSourceId(1l);
		// result = HttpClientUtil.sendPut(
		// "http://127.0.0.1//api/sdvPatientRecord/status/1/1/1",null);
		 result =
		 HttpClientUtil.sendGet("http://127.0.0.1//weixin/webLoginOut");
		 System.out.println(result);
	}
}
