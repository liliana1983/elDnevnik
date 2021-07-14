package com.iktpreobuka.elektronskidnevnik.entities.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iktpreobuka.elektronskidnevnik.entities.GradeType;
import com.sun.istack.NotNull;

public class GradeDTO {
	@PastOrPresent(message = "Date when grade is given cannot be in future")
	@JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy")
	private LocalDate dateWhenGiven;

	@Min(value = 1, message = "Please enter grade within range (1,5)")
	@Max(value = 5, message = "Please enter grade within range (1,5)")
	private Integer gradeValue;
	@Enumerated(EnumType.STRING)
	@NotBlank(message = "Grade type cannot be other than :[written,oral,activity, paper_work, other]")
private GradeType gradeType;
	public GradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public GradeDTO(@PastOrPresent(message = "Date when grade is given cannot be in future") LocalDate dateWhenGiven,
			@Min(value = 1, message = "Please enter grade within range (1,5)") @Max(value = 5, message = "Please enter grade within range (1,5)") Integer gradeValue,
			@NotBlank(message = "Grade type cannot be other than :[written,oral,activity, paper_work, other]") GradeType gradeType) {
		super();
		this.dateWhenGiven = dateWhenGiven;
		this.gradeValue = gradeValue;
		this.gradeType = gradeType;
	}



	public LocalDate getDateWhenGiven() {
		return dateWhenGiven;
	}

	public void setDateWhenGiven(LocalDate dateWhenGiven) {
		this.dateWhenGiven = dateWhenGiven;
	}

	public Integer getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(Integer gradeValue) {
		this.gradeValue = gradeValue;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}
	
}
