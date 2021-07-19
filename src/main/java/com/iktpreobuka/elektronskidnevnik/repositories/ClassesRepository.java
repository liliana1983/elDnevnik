package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;

public interface ClassesRepository extends CrudRepository<ClassesEntity,Integer> {

	

	boolean existsByClassName(String className);

}
