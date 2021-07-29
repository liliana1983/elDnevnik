package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.TeacherDTO;

public interface TeacherService {

public List<TeacherEntity> findAllTeachers();

TeacherEntity newTeacher(TeacherDTO newTeacher, Integer roleId);
}
