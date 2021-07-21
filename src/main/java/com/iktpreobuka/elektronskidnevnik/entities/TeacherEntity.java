package com.iktpreobuka.elektronskidnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name = "teacher")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })

public class TeacherEntity extends UserEntity {
	@ManyToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonIgnore
	private List<SubjectEntity> subject;
	@ManyToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JsonIgnore
	private List<ClassesEntity> classes;
	@OneToOne(mappedBy="headMaster",cascade = CascadeType.PERSIST)
	private ClassesEntity headOfClass;
	

	public ClassesEntity getHeadOfClass() {
		return headOfClass;
	}

	public void setHeadOfClass(ClassesEntity headOfClass) {
		this.headOfClass = headOfClass;
	}

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@JsonIgnore
	public List<SubjectEntity> getSubject() {
		return subject;
	}

	public void setSubject(List<SubjectEntity> subject) {
		this.subject = subject;
	}
@JsonIgnore
	public List<ClassesEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassesEntity> classes) {
		this.classes = classes;
	}
}
