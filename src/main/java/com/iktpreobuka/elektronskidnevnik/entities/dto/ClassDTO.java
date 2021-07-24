package com.iktpreobuka.elektronskidnevnik.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClassDTO {
	@NotBlank(message = "Class name must not be blank or null ")
	@Size(min = 3, max = 5, message = "Classname length must be between {min} and {max}")
	@Pattern(regexp = "^\\d/\\d$")

	private String className;

	public ClassDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassDTO(
			@NotBlank(message = "Class name must not be blank or null ") @Size(min = 2, max = 3, message = "Classname length must be between {min} and {max}") @Pattern(regexp = "[^\\d{1}/?\\d{1}]") String className) {
		super();
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
