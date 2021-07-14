package com.iktpreobuka.elektronskidnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "teacher")
public class TeacherEntity extends UserEntity {
	@ManyToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<SubjectEntity> subject;
	@ManyToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<ClassEntity> classes;
	@OneToOne(mappedBy = "headMaster")
	private ClassEntity headOfClass;

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<SubjectEntity> getSubject() {
		return subject;
	}

	public void setSubject(List<SubjectEntity> subject) {
		this.subject = subject;
	}

	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}
}
