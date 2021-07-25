package com.iktpreobuka.elektronskidnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.GradeEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer>{


	List<GradeEntity> findByStudentIdAndSubjectId(Integer studentId, Integer subjectId);

}
