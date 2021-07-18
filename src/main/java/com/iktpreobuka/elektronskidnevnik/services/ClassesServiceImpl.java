package com.iktpreobuka.elektronskidnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;


@Service
public class ClassesServiceImpl implements ClassesService {
	@Autowired
	ClassesRepository classesRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TeacherRepository teacherRepository;

	@Override
	public ClassesEntity addOneStudent(Integer studentId, Integer classId) {
		if (classesRepository.existsById(classId)) {
			if (studentRepository.existsById(studentId)) {
			ClassesEntity oneClass = classesRepository.findById(classId).get();
			StudentEntity student =studentRepository.findById(studentId).get();
				oneClass.setStudents((List<StudentEntity>) student);
				classesRepository.save(oneClass);
				student.setEnrolledClass(oneClass);
				studentRepository.save(student);
				return oneClass;
			}

		}
		return null;
	}

	@Override
	public ClassesEntity addClassWithHeadMAster(ClassesEntity newClass, Integer headMasterId) {
			if(teacherRepository.existsById(headMasterId)) {
				TeacherEntity headMaster= teacherRepository.findById(headMasterId).get();
				ClassesEntity oneClass=new ClassesEntity();
				headMaster.setHeadOfClass(oneClass);
				oneClass.setHeadMaster(headMaster);
				oneClass.setClassName(newClass.getClassName());
				classesRepository.save(oneClass);
				teacherRepository.save(headMaster);
				return oneClass;
			
		}
		return null;
	}


	/*public ClassesEntity addListOfStudents( List<Integer> studentsIds, Integer classId) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
