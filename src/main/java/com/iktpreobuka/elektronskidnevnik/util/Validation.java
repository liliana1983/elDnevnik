package com.iktpreobuka.elektronskidnevnik.util;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class Validation {
	
	public static <T> T setIfNotNull (T oldProperty, T newProperty) {
		return newProperty==null? oldProperty:newProperty;
	}
	public static String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
}
