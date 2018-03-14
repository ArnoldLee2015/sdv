package com.lee.sdv.translation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.service.SdvTemplateVisitService;
import com.lee.sdv.translation.TranslationType;

@Component
public class VisitNameTranslation implements TranslationType<Long, String> {

	@Autowired
	private SdvTemplateVisitService sdvTemplateVisitService;

	@Override
	public String getType() {
		return "visitName";
	}

	@Override
	public String translation(Long k) {
		if (k == null) {
			return "";
		}
		String name = "";
		SdvTemplateVisit s = sdvTemplateVisitService.selectEntry(k);
		if (s == null) {
			name = k.toString();
		} else {
			name = s.getName();
		}
		return name;
	}
}
