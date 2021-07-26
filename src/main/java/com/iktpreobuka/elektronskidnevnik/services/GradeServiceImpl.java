package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.SubjectRepository;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	GradeRepository gradeRepository;

	@Override
	public List<GradeEntity> getGradesSubject(Integer subjectId, Integer studentId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		StudentEntity student = studentRepository.findById(studentId).get();
		if (subject.getClasses().contains(student.getEnrolledClass())) {
			List<GradeEntity> grades = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
			return grades;
		}
		return null;
	}

	@Override
	public List<Integer> gradeValuesOneSubject(Integer subjectId, Integer studentId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		StudentEntity student = studentRepository.findById(studentId).get();
		if (subject.getClasses().contains(student.getEnrolledClass())) {
			List<GradeEntity> grades = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
			List<Integer> gradeValues = grades.stream().map(GradeEntity::getGradeValue).collect(Collectors.toList());
			return gradeValues;
		}
		return null;
	}
	
	@Override
	public double calculateAverage(Integer subjectId,Integer studentId) {
	List<Integer> gradeValues=	gradeValuesOneSubject(subjectId, studentId);
	    Double average= gradeValues.stream()
	                .mapToDouble(d -> d)
	                .average()
	                .orElse(0.0);
	    return average;
	}

	@Override
	public Double closeGrade(Double average) {
		Double closingGrade=Precision.round(average, 0);
		return closingGrade;
	}
}
