package com.iktpreobuka.elektronskidnevnik.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Inherited
@Constraint(validatedBy = UsernameValidator.class)// ova anotacija mora da postoji u interfejsu da bi @UniqueUsername radilo iznad atributa
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
	String message() default "";// poruka koja je default koja ce biti vracena kada dodje do greske(prazna je poruka ovde jer se nalazi u UsernameValidator)
	  Class<?>[] groups() default {}; //redosled kojim ce se vrsiti validacija
	  Class<? extends Payload>[] payload() default {}; // za "rucno pozivanje" validacije
}

