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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ClassEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "class_id")
	private Integer id;
	private String className;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private TeacherEntity headMaster;
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_classes", joinColumns = { @JoinColumn(name = "class_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<TeacherEntity> teacher;
	@OneToMany(mappedBy = "enrolledClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> students;

	@ManyToMany(mappedBy = "classes", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<SubjectEntity> listOfSubjects;

	public List<SubjectEntity> getListOfSubjects() {
		return listOfSubjects;
	}

	public void setListOfSubjects(List<SubjectEntity> listOfSubjects) {
		this.listOfSubjects = listOfSubjects;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TeacherEntity> getTeacher() {
		return teacher;
	}

	public void setTeacher(List<TeacherEntity> teacher) {
		this.teacher = teacher;
	}

	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public TeacherEntity getHeadMaster() {
		return headMaster;
	}

	public void setHeadMaster(TeacherEntity headMaster) {
		this.headMaster = headMaster;
	}
}
