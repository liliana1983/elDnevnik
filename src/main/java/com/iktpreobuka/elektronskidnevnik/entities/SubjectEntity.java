package com.iktpreobuka.elektronskidnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "subject_id")
	private Integer id;
	
	private String name;
	
	private Integer hoursPerWeek;
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_teaches",
	joinColumns= { @JoinColumn(name="subject_id")},
	inverseJoinColumns= {@JoinColumn(name= "user_id")}) 
	private List<TeacherEntity> teacher;
	@ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.REFRESH )
	@JoinTable (name="subject_in_class",
	joinColumns= {@JoinColumn(name="subject_id")},
	inverseJoinColumns= {@JoinColumn (name="classes_id")})
	private List<ClassesEntity> classes;
	@JsonIgnore
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH })
	private List<GradeEntity> grades;
	
	public List<TeacherEntity> getTeacher() {
		return teacher;
	}

	public void setTeacher(List<TeacherEntity> teacher) {
		this.teacher = teacher;
	}

	public List<ClassesEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassesEntity> classes) {
		this.classes = classes;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}

	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHoursPerWeek() {
		return hoursPerWeek;
	}

	public void setHoursPerWeek(Integer hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}
	
	
}
