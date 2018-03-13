package com.lee.sdv.demo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lee.sdv.service.base.BaseService;

/**
 * 测试spring的业务类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-config-test.xml")
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BusinessServiceTest implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Test
	public void testOrderService() {
		Map<String, BaseService> beans = applicationContext.getBeansOfType(BaseService.class);
		if (!beans.isEmpty()) {
			for (String key : beans.keySet()) {
				BaseService service = beans.get(key);
				System.out.println(service.getClass().getName());
				Type baseType = service.getClass().getGenericSuperclass();
				if (baseType instanceof ParameterizedType) {
					Type[] ts = ((ParameterizedType) baseType).getActualTypeArguments();
					Map<String, String> tsMap = new HashMap<String, String>();
					Class T = (Class) ts[0];
					tsMap.put("T", T.getName());
					Class Key = (Class) ts[1];
					tsMap.put("KEY", Key.getName());
					Method[] methods = service.getClass().getMethods();
					if (methods != null) {
						for (Method method : methods) {
							if ("wait".equals(method.getName()) || "equals".equals(method.getName()) || "toString".equals(method.getName())
									|| "hashCode".equals(method.getName()) || "getClass".equals(method.getName())
									|| "notify".equals(method.getName()) || "notifyAll".equals(method.getName())) {
								continue;
							}
							try {
								Type[] types = method.getGenericParameterTypes();
								if (types.length == 0) {
									method.invoke(service);
								} else {
									Object[] objArray = new Object[types.length];
									for (int i = 0; i < types.length; i++) {
										Type t = types[i];
										if (ParameterizedType.class.isAssignableFrom(t.getClass())) { // 【3】如果是泛型参数的类型
											ParameterizedType aa = (ParameterizedType) t;
											if (aa.getRawType() == List.class) {
												Class genericClazz = (Class) aa.getActualTypeArguments()[0]; // 【4】得到泛型里的class类型对象。
												objArray[i] = JSONObject.parseArray("[{}]", genericClazz);
											}
										} else if (tsMap.get(t.toString()) != null) {
											if (tsMap.get(t.toString()).equals("java.lang.Long")) {
												objArray[i] = 1l;
											} else {
												objArray[i] = Class.forName(tsMap.get(t.toString())).newInstance();
											}
										} else if (t == Date.class) {
											Date date = new Date();
											objArray[i] = date;
										} else if (t == String.class) {
											objArray[i] = "test";
										} else if (t == Long.class) {
											objArray[i] = 1l;
										} else {
											objArray[i] = t.getClass().newInstance();
										}
									}
									method.invoke(service, objArray);
								}
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println(method.getName());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
