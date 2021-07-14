package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;





@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
	private EntityManager em;
	@Override
	public boolean exists(String username) {
		String sql= "SELECT u from UserEntity where username=:username";
		Query query= em.createQuery(sql);
		query.setParameter("username", username);
		if(query.getResultList()== null)
			return true; //ako jeste null onda znaci ne postoji user sa tim usernamom
		return false;//ako nije null onda znaci da postoji user sa tim usernejmom
		
	}
}
