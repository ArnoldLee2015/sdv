package com.lee.sdv.translation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.service.SdvTemplateService;
import com.lee.sdv.translation.TranslationType;

@Component
public class SdvTemplateNameTranslation implements TranslationType<Long, String> {
	@Autowired
	private SdvTemplateService sdvTemplateService;

	@Override
	public String getType() {
		return "sdvTemplateName";
	}

	@Override
	public String translation(Long k) {
		if (k == null) {
			return "";
		}
		String name = "";
		SdvTemplate s = sdvTemplateService.selectEntry(k);
		if (s == null) {
			name = k.toString();
		} else {
			name = s.getName();
		}
		return name;
	}

}
