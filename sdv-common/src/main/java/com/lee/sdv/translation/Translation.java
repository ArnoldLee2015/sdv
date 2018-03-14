package com.lee.sdv.translation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Translation {

	/**
	 * 映射类型
	 * 
	 * @return
	 */
	String type();

	/**
	 * 映射主键（默认id）
	 * 
	 * @return
	 */
	String source() default "id";
}
