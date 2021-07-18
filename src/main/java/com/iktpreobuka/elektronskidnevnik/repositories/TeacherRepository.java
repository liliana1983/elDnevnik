package com.iktpreobuka.elektronskidnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer>{

	void save(UserEntity teacher);

	


}
