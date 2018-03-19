package com.lee.sdv.translation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.translation.TranslationType;

@Component
public class OwnerNameTranslation implements TranslationType<Long, String> {

	@Autowired
	private SdvUserService sdvUserService;

	@Override
	public String getType() {
		return "ownerName";
	}

	@Override
	public String translation(Long k) {
		if (k == null) {
			return "";
		}
		String name = "";
		SdvUser s = sdvUserService.selectEntry(k);
		if (s == null) {
			name = k.toString();
		} else {
			name = s.getName();
		}
		return name;
	}

}
