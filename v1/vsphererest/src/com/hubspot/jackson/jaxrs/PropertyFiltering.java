package com.hubspot.jackson.jaxrs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyFiltering {
	String using() default "property";
	
	/*
	 * Added default option to specify default fields in the annotation
	 * Branden Horiuchi <bhoriuchi@gmail.com>
	 */
	String defaults() default "";
}