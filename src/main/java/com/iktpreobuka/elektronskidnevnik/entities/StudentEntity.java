package com.iktpreobuka.elektronskidnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class StudentEntity extends UserEntity {
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "guardian")
	private GuardianEntity guardian;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="enrolledClass")
	
	private ClassesEntity enrolledClass;

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}

	@OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<GradeEntity> grades;
	private Double gradeAverage;

	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GuardianEntity getGuardian() {
		return guardian;
	}

	public void setGuardian(GuardianEntity guardian) {
		this.guardian = guardian;
	}

	public Double getGradeAverage() {
		return gradeAverage;
	}

	public void setGradeAverage(Double gradeAverage) {
		this.gradeAverage = gradeAverage;
	}

	public ClassesEntity getEnrolledClass() {
		return enrolledClass;
	}

	public void setEnrolledClass(ClassesEntity enrolledClass) {
		this.enrolledClass = enrolledClass;
	}
}
