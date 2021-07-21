package com.iktpreobuka.elektronskidnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.SubjectRepository;

@Service
public class SubjectSeviceImpl implements SubjectService {
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	ClassesRepository classesRepository;

/*	@Override
	public boolean subjectInClass(Integer subjectId, Integer classId) {
		if (classesRepository.existsById(classId)) {
			ClassesEntity classes = classesRepository.findById(classId).get();
			if (subjectRepository.existsById(subjectId)
					&& classes.getListOfSubjects().contains(subjectRepository.findById(subjectId).get())) {
				return true;
			}
		}

		return false;
	}*/
}
