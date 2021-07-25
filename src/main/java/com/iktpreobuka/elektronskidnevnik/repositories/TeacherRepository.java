package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer>{

	void save(UserEntity teacher);



	

	


}
