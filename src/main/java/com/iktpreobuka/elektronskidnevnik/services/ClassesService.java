package com.iktpreobuka.elektronskidnevnik.services;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.ClassDTO;

public interface ClassesService {
public ClassesEntity addOneStudent(Integer studentId,Integer classId);
//public ClassesEntity addListOfStudents(List<StudentEntity> students,Integer studentsIds, Integer classId);
public ClassesEntity addClassWithHeadMAster(ClassDTO newClass,Integer headMasterId);
}
