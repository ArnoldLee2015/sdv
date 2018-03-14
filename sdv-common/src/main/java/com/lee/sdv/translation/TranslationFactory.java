package com.lee.sdv.translation;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TranslationFactory {

	private static Map<String, TranslationType> map;
	static {
		map = new HashMap<String, TranslationType>();
		Map<String, TranslationType> temp = SpringContextUtil.getBeansOfType(TranslationType.class);
		if (temp != null) {
			for (TranslationType type : temp.values()) {
				if (type != null) {
					map.put(type.getType(), type);
				}
			}
		}
	}

	public static TranslationType getTranslationType(String type) {
		return map.get(type);
	}

}
