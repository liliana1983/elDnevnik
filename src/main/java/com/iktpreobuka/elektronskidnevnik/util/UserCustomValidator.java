package com.iktpreobuka.elektronskidnevnik.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.UserDTO;


@Component
public class UserCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserEntity user=(UserEntity) target;
		UserDTO newUser=(UserDTO) target;
		if(!user.getPassword().equals(newUser.getConfirmPassword()))
			errors.reject("400","passwords must match");
		
	}


}
