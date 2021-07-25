package com.iktpreobuka.elektronskidnevnik.services;

import javax.persistence.EntityManager;
import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;

@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean exists(String username) {
		String sql = "SELECT u from UserEntity where username=:username";
		Query query = em.createQuery(sql);
		query.setParameter("username", username);
		if (query.getResultList() == null)
			return true; // ako jeste null onda znaci ne postoji user sa tim usernamom
		return false;// ako nije null onda znaci da postoji user sa tim usernejmom

	}

	@Override
	public UserEntity userLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		UserEntity user = userRepository.findByUsername(username);
		return user;
		
	}

}
