package com.iktpreobuka.elektronskidnevnik.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity,Integer> {


	UserEntity findByUsername(String username);


	

}
