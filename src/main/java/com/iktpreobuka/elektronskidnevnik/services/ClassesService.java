package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;

public interface ClassesService {
public ClassesEntity addOneStudent(Integer studentId,Integer classId);
//public ClassesEntity addListOfStudents(List<StudentEntity> students,Integer studentsIds, Integer classId);
public ClassesEntity addClassWithHeadMAster(ClassesEntity oneClass,Integer headMasterId);
}
