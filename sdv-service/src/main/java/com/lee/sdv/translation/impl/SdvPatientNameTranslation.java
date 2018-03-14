package com.lee.sdv.translation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.service.SdvPatientService;
import com.lee.sdv.translation.TranslationType;

@Component
public class SdvPatientNameTranslation implements TranslationType<Long, String> {

	@Autowired
	private SdvPatientService sdvPatientService;

	@Override
	public String getType() {
		return "sdvPatientName";
	}

	@Override
	public String translation(Long k) {
		if (k == null) {
			return "";
		}
		String name = "";
		SdvPatient s = sdvPatientService.selectEntry(k);
		if (s == null) {
			name = k.toString();
		} else {
			name = s.getPatientName();
		}
		return name;
	}
}
