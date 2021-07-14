package com.iktpreobuka.elektronskidnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class GuardianEntity extends UserEntity{
	@Column(name = "email")
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<StudentEntity> getChildren() {
		return children;
	}
	public void setChildren(List<StudentEntity> children) {
		this.children = children;
	}
	@OneToMany(mappedBy = "guardian", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> children;
	public GuardianEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
