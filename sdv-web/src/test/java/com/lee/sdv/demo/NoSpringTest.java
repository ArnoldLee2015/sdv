package com.lee.sdv.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.web.controller.dto.LoginDto;

/**
 * 测试json
 */
public class NoSpringTest {
	@Test
	public void testHttp() {
		String result = "";
		LoginDto dto = new LoginDto();
		dto.setCode("011yYd8q035Biq1oaQ8q0fPj8q0yYd87");
		dto.setEncryptedData("UhLk3hcTI/8LfdAqUEFb8J4pOscqrHdLBjcGhn9nesWNPvwImgl8GtQOPuUHeLY2EJuxNkou/LukF8d/hnPnAxmIwaKLP7twRe/T39V/+nBvCx0a9Q/nuWYNsD75H33tzWO3+AELFAWgziyezartfZFodOGYeDdhMvz38VFDzrzdJM6cBIUWlT9u9hlF6DxIRh/AYI4Nk5fX/NpkVCh6mh6gOdvyEPyG/+1u21RfoxbJFD+w3TLkqD6IUSro/oJ1sG9jkbZCIEFLvA+hwG0uguCZE60D+JSjitZa6DFrMyqKxi8fVA4LuUy1fhceahGXbyz2m+NOjvYALE6Ys070YKyHiew3Vpcx+Vkemn8vLE6cGdUFBvPrRD6iGWxxHBSBZgZpoc6bXx6aXe82IBaruxLU7qQ5uY+/rSQILuYk9Ll+UyM1BWGy/KY1IgVXWT+U9aOhbN6ksOYX0bUowVnyaB7zQrCFRLX/43Zez95dCUOZT9jYrmQtWbZN6N9nweVlu+qxoGUomrAwT7e4xirqtb9dtoNPFrY7a9WKoPgTOrg=");
		dto.setIv("uiwrod4n+GmdbhColgKHxA==");
		result = HttpClientUtil.sendPost(
				"http://woniubi.cn/weixin/login", dto);
		 List<SdvPatientRecord> param = new ArrayList<SdvPatientRecord>();
		 SdvPatientRecord r1 = new SdvPatientRecord();
		 r1.setId(91l);
		 r1.setSdvPatientId(1l);
		 r1.setSdvPatientDataId(69l);
		 r1.setSdvTemplateId(1l);
		 r1.setVisitId(1l);
		 r1.setDataId(1l);
		 r1.setRemark("ces111简单介绍");
		 List<PatientRecordFile> files = new ArrayList<PatientRecordFile>();
		 PatientRecordFile file = new PatientRecordFile();
		 file.setFileName("sadasdas");
		 file.setFileUrl("dfsdfsdf");
		 files.add(file);
		 r1.setFiles(files);
		 param.add(r1);
		 SdvTemplate sdvTemplate = new SdvTemplate();
		 sdvTemplate.setName("治疗转移性肾细胞癌II期临床试验");
		 sdvTemplate.setRemark("尽管目前已有多种VEGF、VEGFR以及mTOR抑制剂获得批准用于转移性肾细胞癌（mRCC）的治疗，但患者的治疗获益已经达到了一个平台期。随着免疫检查点阻断药物mRCC治疗中再度引起关注，新的发展指日可待。抗PD-1通路的单克隆抗体的发展，无论单药或联合，都可能代表着未来的临床突破。");
		 sdvTemplate.setSourceId(1l);
		 result = HttpClientUtil.sendPut(
		 "http://127.0.0.1/api/sdvPatientRecord/save",param);
		// result =
		// HttpClientUtil.sendGet("http://127.0.0.1/api/sdvPatientRecord/patient/1");
		System.out.println(result);
	}
}
