package com.iktpreobuka.elektronskidnevnik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskidnevnik.entities.dto.ClassDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
@Component
public class ClassesValidator implements Validator {
@Autowired
ClassesRepository classesRepository;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return ClassDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
ClassDTO classDto = (ClassDTO) target;
		
		if(classesRepository.existsByClassName(classDto.getClassName())) {
			errors.reject("400", "Class name already exists.");
	}
	}
}
