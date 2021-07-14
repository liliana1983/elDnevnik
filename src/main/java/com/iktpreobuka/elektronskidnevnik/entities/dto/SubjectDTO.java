package com.iktpreobuka.elektronskidnevnik.entities.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectDTO {
	@NotNull(message="Subject's hours per week must be provided")
	@Min(value=1, message="Weekly hours of a subject cannot be less than 1")
private Integer hoursPerWeek;
	
	@NotBlank(message="Name must not be blank or null")
	@Size(min=2,max=30, message="First name length must be between {min} and {max}")
private String name;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectDTO(
			@NotNull(message = "Subject's hours per week must be provided") @Min(value = 1, message = "Weekly hours of a subject cannot be less than 1") Integer hoursPerWeek,
			@NotBlank(message = "Name must not be blank or null") @Size(min = 2, max = 30, message = "First name length must be between {min} and {max}") String name) {
		super();
		this.hoursPerWeek = hoursPerWeek;
		this.name = name;
	}

	public Integer getHoursPerWeek() {
		return hoursPerWeek;
	}

	public void setHoursPerWeek(Integer hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
