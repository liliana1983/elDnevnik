package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity,Integer>{

}
