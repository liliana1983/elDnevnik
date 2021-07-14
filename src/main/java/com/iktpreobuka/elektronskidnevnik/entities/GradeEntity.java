package com.iktpreobuka.elektronskidnevnik.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class GradeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grade_id")
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	private SubjectEntity subject;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="student")
	private StudentEntity student;
	
	private LocalDate dateWhenGiven;
	
	private Integer gradeValue;
	
private GradeType gradeType;

	public StudentEntity getStudent() {
	return student;
}
public void setStudent(StudentEntity student) {
	this.student = student;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SubjectEntity getSubject() {
		return subject;
	}
	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}
	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
