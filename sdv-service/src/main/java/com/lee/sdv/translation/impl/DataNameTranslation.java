package com.lee.sdv.translation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.sdv.domain.SdvTemplateData;
import com.lee.sdv.service.SdvTemplateDataService;
import com.lee.sdv.translation.TranslationType;

@Component
public class DataNameTranslation implements TranslationType<Long, String> {

	@Autowired
	private SdvTemplateDataService sdvTemplateDataService;

	@Override
	public String getType() {
		return "dataName";
	}

	@Override
	public String translation(Long k) {
		if (k == null) {
			return "";
		}
		String name = "";
		SdvTemplateData s = sdvTemplateDataService.selectEntry(k);
		if (s == null) {
			name = k.toString();
		} else {
			name = s.getName();
		}
		return name;
	}

}
