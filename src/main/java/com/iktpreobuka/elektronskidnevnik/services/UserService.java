package com.iktpreobuka.elektronskidnevnik.services;

import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;

public interface UserService {
	public boolean exists(String username);

	UserEntity userLoggedIn();
}
