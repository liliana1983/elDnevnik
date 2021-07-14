package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;

@Service
public class TeacherSeviceImpl  implements TeacherService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@PersistenceContext
	private EntityManager em;
	/*@Override
	public boolean exists(String username) {
		String sql= "SELECT u from UserEntity where username=:username";
		Query query= em.createQuery(sql);
		query.setParameter("username", username);
		if(query.getResultList()== null)
			return true; //ako jeste null onda znaci ne postoji user sa tim usernamom
		return false;//ako nije null onda znaci da postoji user sa tim usernejmom
		SELECT a from AddressEntity a LEFT JOIN fetch a.users u WHERE u.name=:name";
		("SELECT u FROM User u JOIN u.roles r WHERE r.role=:rolename")
		
	}*/
	@Override
	public List<TeacherEntity> findAllTeachers() {
		 String sql="select u from UserEntity u where u.role=2 ";
		 Query query= em.createQuery(sql);
		List<TeacherEntity> retVals=  query.getResultList();
		return retVals;
	
		
	
	}
}
