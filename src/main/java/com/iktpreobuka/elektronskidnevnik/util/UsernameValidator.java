package com.iktpreobuka.elektronskidnevnik.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsernameValidator implements ConstraintValidator<UniqueUsername, String> {
	@Autowired
	UserRepository userRepository;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
		if (userRepository.findByUsername(username) == null && !username.isEmpty()) {
			if (userRepository.findByUsername(username) != null) {
				constraintValidatorContext.disableDefaultConstraintViolation();
				constraintValidatorContext
						.buildConstraintViolationWithTemplate(
								"Username must be provided")
						.addConstraintViolation();
				return false;
			}
			return true;
		} else {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext.buildConstraintViolationWithTemplate("Username should be unique. '"+ username + "' is already taken")
					.addConstraintViolation();
			return false;
		}
	}

}
