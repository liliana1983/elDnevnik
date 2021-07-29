package com.iktpreobuka.elektronskidnevnik.entities;

import javax.persistence.Entity;

@Entity
public class FinalGradeEntity extends GradeEntity{

 private Boolean pass;
 private Semester semester;

public Semester getSemester() {
	return semester;
}

public void setSemester(Semester semester) {
	this.semester = semester;
}

public FinalGradeEntity() {
	super();
	// TODO Auto-generated constructor stub
}

public Boolean getPass() {
	return pass;
}

public void setPass(Boolean pass) {
	this.pass = pass;
}
	
}
