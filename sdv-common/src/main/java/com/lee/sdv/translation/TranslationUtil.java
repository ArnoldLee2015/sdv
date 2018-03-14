package com.lee.sdv.translation;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class TranslationUtil {
	private static final Logger LOG = LoggerFactory.getLogger(TranslationUtil.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T translation(T o) {
		if (o == null) {
			return o;
		}
		Field[] fields = o.getClass().getDeclaredFields();
		if (fields == null) {
			return o;
		}
		for (Field field : fields) {
			try {
				Translation t = field.getAnnotation(Translation.class);
				if (t != null) {
					Field source = null;
					for (Class<?> clazz = o.getClass(); clazz != Object.class && source == null; clazz = clazz.getSuperclass()) {
						try {
							source = clazz.getDeclaredField(t.source());
						} catch (Exception e) {
						}
					}
					if (source != null) {
						source.setAccessible(true);
						TranslationType translationType = TranslationFactory.getTranslationType(t.type());
						if (translationType != null) {
							Object target = translationType.translation(source.get(o));
							field.setAccessible(true);
							field.set(o, target);
						}
					}
				}
			} catch (Exception e) {
				LOG.error("translation error [{}]", e.getMessage(), e);
			}
		}
		return o;
	}

	public static <T> List<T> translations(List<T> collection) {
		if (CollectionUtils.isEmpty(collection)) {
			return collection;
		}
		for (Object o : collection) {
			translation(o);
		}
		return collection;
	}

	public static <K, V> String getCacheKey(TranslationType<K, V> type, K k) {
		StringBuilder sb = new StringBuilder(TranslationType.TRANSLATION_KEY);
		sb.append("_").append(type.getType()).append('_').append(k);
		return sb.toString();
	}
}
