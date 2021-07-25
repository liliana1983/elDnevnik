package com.iktpreobuka.elektronskidnevnik.repositories;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity,Integer> {


	UserEntity findByUsername(String username);




	

}
